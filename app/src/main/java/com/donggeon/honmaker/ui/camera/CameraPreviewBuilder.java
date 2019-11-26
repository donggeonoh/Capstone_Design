package com.donggeon.honmaker.ui.camera;

import android.content.Context;
import android.graphics.Matrix;
import android.hardware.display.DisplayManager;
import android.util.Log;
import android.util.Size;
import android.view.Display;
import android.view.Surface;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.camera.core.Preview;
import androidx.camera.core.PreviewConfig;

import java.lang.ref.WeakReference;

public class CameraPreviewBuilder {
    @NonNull
    private final PreviewConfig config;
    @NonNull
    private final WeakReference<CameraView> cameraViewRef;

    private Preview preview;

    /**
     * Internal variable used to keep track of the use case's output rotation
     */
    private int bufferRotation = 0;

    /**
     * Internal variable used to keep track of the view's rotation
     */
    private int viewFinderRotation = 0;

    /**
     * Internal variable used to keep track of the use-case's output dimension
     */
    private Size bufferDimens = new Size(0, 0);

    /**
     * Internal variable used to keep track of the view's dimension
     */
    private Size viewFinderDimens = new Size(0, 0);

    /**
     * Internal variable used to keep track of the view's display
     */
    private int viewFinderDisplay = -1;

    /**
     * Internal reference of the [DisplayManager]
     */
    private DisplayManager displayManager;

    private DisplayManager.DisplayListener displayListener = new DisplayManager.DisplayListener() {
        @Override
        public void onDisplayAdded(int displayId) {

        }

        @Override
        public void onDisplayRemoved(int displayId) {

        }

        @Override
        public void onDisplayChanged(int displayId) {
            CameraView viewFinder = cameraViewRef.get();
            if (viewFinder == null) {
                return;
            }
            if (displayId == viewFinderDisplay) {
                Display display = displayManager.getDisplay(displayId);
                int rotation = getDisplaySurfaceRotation(display);
                updateTransform(viewFinder, rotation, bufferDimens, viewFinderDimens);
            }
        }
    };

    private CameraPreviewBuilder(@NonNull final PreviewConfig config,
                                 @NonNull final CameraView cameraView) {
        this.config = config;
        this.cameraViewRef = new WeakReference<>(cameraView);
        init();
    }

    private void init() {
        CameraView cameraView = this.cameraViewRef.get();
        if (cameraView == null) {
            throw new IllegalArgumentException("Invalid reference to view finder used");
        }
        viewFinderDisplay = cameraView.getDisplay().getDisplayId();
        viewFinderRotation = getDisplaySurfaceRotation(cameraView.getDisplay());

        preview = new Preview(config);

        preview.setOnPreviewOutputUpdateListener(output -> {
            CameraView viewFinder = this.cameraViewRef.get();
            if (viewFinder == null) {
                return;
            }

            Log.d("CameraPreviewBuilder", "Preview output changed. " +
                    "Size: " + output.getTextureSize() + ", Rotation: " + output.getRotationDegrees());

            // To update the SurfaceTexture, we have to remove it and re-add it
            ViewGroup parent = (ViewGroup) viewFinder.getParent();
            parent.removeView(viewFinder);
            parent.addView(viewFinder, 1);

            // Update internal texture
            viewFinder.setSurfaceTexture(output.getSurfaceTexture());

            // Apply relevant transformations
            bufferRotation = output.getRotationDegrees();
            int rotation = getDisplaySurfaceRotation(cameraView.getDisplay());
            updateTransform(viewFinder, rotation, output.getTextureSize(), viewFinderDimens);
        });

        // Every time the provided texture view changes, recompute layout
        cameraView.addOnLayoutChangeListener((view, left, top, right, bottom, a, b, c, d) -> {
            CameraView viewFinder = (CameraView) view;
            Size newViewFinderDimens = new Size(right - left, bottom - top);
            Log.d("CameraPreviewBuilder", "View finder layout changed. Size: " + newViewFinderDimens);
            int rotation = getDisplaySurfaceRotation(viewFinder.getDisplay());
            updateTransform(viewFinder, rotation, bufferDimens, newViewFinderDimens);
        });

        displayManager = (DisplayManager) cameraView.getContext()
                .getSystemService(Context.DISPLAY_SERVICE);
        displayManager.registerDisplayListener(displayListener, null);

        cameraView.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() {
            @Override
            public void onViewAttachedToWindow(View v) {
                displayManager.registerDisplayListener(displayListener, null);
            }

            @Override
            public void onViewDetachedFromWindow(View v) {
                displayManager.unregisterDisplayListener(displayListener);
            }
        });
    }

    /**
     * Helper function that fits a camera preview into the given [TextureView]
     */
    private void updateTransform(CameraView textureView, int rotation, Size newBufferDimens, Size newViewFinderDimens) {
        // This should not happen anyway, but now the linter knows
        if (textureView == null) {
            return;
        }

        if (rotation == viewFinderRotation &&
                newBufferDimens == this.bufferDimens &&
                newViewFinderDimens == viewFinderDimens) {
            // Nothing has changed, no need to transform output again
            return;
        }

        // Update internal field with new inputs
        viewFinderRotation = rotation;

        if (newBufferDimens.getWidth() == 0 || newBufferDimens.getHeight() == 0) {
            // Invalid buffer dimens - wait for valid inputs before setting matrix
            return;
        } else {
            // Update internal field with new inputs
            this.bufferDimens = newBufferDimens;
        }

        if (newViewFinderDimens.getWidth() == 0 || newViewFinderDimens.getHeight() == 0) {
            // Invalid view finder dimens - wait for valid inputs before setting matrix
            return;
        } else {
            // Update internal field with new inputs
            this.viewFinderDimens = newViewFinderDimens;
        }

        Matrix matrix = new Matrix();
        Log.d("CameraPreviewBuilder", "Applying output transformation.\n" +
                "View finder size: " + viewFinderDimens + "\n" +
                "Preview output size: " + bufferDimens + "\n" +
                "View finder rotation: " + viewFinderRotation + "\n" +
                "Preview output rotation: " + bufferRotation);

        // Compute the center of the view finder
        float centerX = viewFinderDimens.getWidth() / 2f;
        float centerY = viewFinderDimens.getHeight() / 2f;

        // Correct preview output to account for display rotation
        matrix.postRotate(-viewFinderRotation, centerX, centerY);

        // Buffers are rotated relative to the device's 'natural' orientation: swap width and height
        float bufferRatio = (float) this.bufferDimens.getHeight() / (float) this.bufferDimens.getWidth();

        int scaledWidth;
        int scaledHeight;
        // Match longest sides together -- i.e. apply center-crop transformation
        if (viewFinderDimens.getWidth() > viewFinderDimens.getHeight()) {
            scaledHeight = viewFinderDimens.getWidth();
            scaledWidth = (int) (viewFinderDimens.getWidth() * bufferRatio);
        } else {
            scaledHeight = viewFinderDimens.getHeight();
            scaledWidth = (int) (viewFinderDimens.getHeight() * bufferRatio);
        }

        // Compute the relative scale value
        float xScale = (float) scaledWidth / (float) viewFinderDimens.getWidth();
        float yScale = (float) scaledHeight / (float) viewFinderDimens.getHeight();

        // Scale input buffers to fill the view finder
        matrix.preScale(xScale, yScale, centerX, centerY);

        // Finally, apply transformations to our TextureView
        textureView.setTransform(matrix);
    }

    private static int getDisplaySurfaceRotation(Display display) {
        switch (display.getRotation()) {
            case Surface.ROTATION_0:
                return 0;
            case Surface.ROTATION_90:
                return 90;
            case Surface.ROTATION_180:
                return 180;
            case Surface.ROTATION_270:
                return 270;
        }
        return 0;
    }

    public static Preview build(@NonNull final PreviewConfig config,
                                @NonNull final CameraView cameraView) {
        return new CameraPreviewBuilder(config, cameraView).preview;
    }
}
