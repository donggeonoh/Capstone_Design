package com.donggeon.honmaker.ui.image;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.donggeon.honmaker.R;
import com.donggeon.honmaker.databinding.ActivityImageBinding;
import com.donggeon.honmaker.extension.databinding.ImageViewAdapters;
import com.donggeon.honmaker.extension.mlkit.VisionImageGetter;
import com.donggeon.honmaker.ui.BaseActivity;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.text.FirebaseVisionText;

public class ImageActivity extends BaseActivity<ActivityImageBinding> {
    private static final String EXTRA_FILE_PATH = "EXTRA_FILE_PATH";

    private String filePath;

    public static Intent getLaunchIntent(@NonNull final Context context,
                                         @NonNull final String path) {
        Intent intent = new Intent(context, ImageActivity.class);
        intent.putExtra(EXTRA_FILE_PATH, path);
        return intent;
    }

    private void getExtras(@NonNull final Intent intent) {
        this.filePath = intent.getStringExtra(EXTRA_FILE_PATH);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_image;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getExtras(getIntent());
        initViews();
    }

    private void initViews() {
        ImageViewAdapters.loadImageFromFile(binding.ivImage, filePath);
        binding.tvStart.setOnClickListener(__ -> startAnalyze());
    }

    private void startAnalyze() {
        analyze(VisionImageGetter.getImagefromBitmap(BitmapFactory.decodeFile(filePath)));
    }

    private void analyze(@NonNull final FirebaseVisionImage image) {
        Log.d("VisionImage", "analyze with image : " + image.toString());

        FirebaseVision.getInstance()
                .getCloudTextRecognizer()
                .processImage(image)
                .addOnSuccessListener(result -> {
                    Log.d("VisionImage", "text: " + result.getText());
                    for (FirebaseVisionText.TextBlock textBlock : result.getTextBlocks()) {
                        Log.d("VisionImage", "text: " + textBlock.getText());
                    }
                })
                .addOnFailureListener(error -> {
                    Log.d("VisionImage", "error: " + error.getLocalizedMessage());
                    error.printStackTrace();
                });
    }
}
