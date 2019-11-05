package com.donggeon.honmaker.ui.ingredient;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.ObservableBoolean;

import com.donggeon.honmaker.R;
import com.donggeon.honmaker.databinding.ActivityIngredientBinding;
import com.donggeon.honmaker.extension.databinding.ImageViewAdapters;
import com.donggeon.honmaker.extension.mlkit.VisionImageGetter;
import com.donggeon.honmaker.ui.BaseActivity;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.text.FirebaseVisionCloudTextRecognizerOptions;
import com.google.firebase.ml.vision.text.FirebaseVisionText;

import java.util.Arrays;

public class IngredientActivity extends BaseActivity<ActivityIngredientBinding> {
    private static final String EXTRA_FILE_PATH = "EXTRA_FILE_PATH";

    public ObservableBoolean isLoading = new ObservableBoolean(false);
    public ObservableString filePath = new ObservableString();

    public static Intent getLaunchIntent(@NonNull final Context context,
                                         @NonNull final String path) {
        Intent intent = new Intent(context, IngredientActivity.class);
        intent.putExtra(EXTRA_FILE_PATH, path);
        return intent;
    }

    private void getExtras(@NonNull final Intent intent) {
        this.filePath.set(intent.getStringExtra(EXTRA_FILE_PATH));
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_ingredient;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getExtras(getIntent());
        initViews();
    }

    private void initViews() {
        startAnalyze();
    }

    private void startAnalyze() {
        isLoading.set(true);
        analyze(VisionImageGetter.getImageFromFilePath(this, filePath));
    }

    private void analyze(@NonNull final FirebaseVisionImage image) {
        FirebaseVisionCloudTextRecognizerOptions options =
                new FirebaseVisionCloudTextRecognizerOptions.Builder()
                        .setLanguageHints(Arrays.asList("en", "ko")).build();

        FirebaseVision.getInstance()
                .getCloudTextRecognizer(options)
                .processImage(image)
                .addOnSuccessListener(result -> {
                    isLoading.set(false);
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
