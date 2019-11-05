package com.donggeon.honmaker.ui.ingredient;

import android.annotation.SuppressLint;

import androidx.lifecycle.MutableLiveData;

import com.donggeon.honmaker.App;
import com.donggeon.honmaker.extension.mlkit.VisionImageGetter;
import com.donggeon.honmaker.ui.BaseViewModel;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.text.FirebaseVisionCloudTextRecognizerOptions;
import com.google.firebase.ml.vision.text.FirebaseVisionText;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;

@SuppressLint("CheckResult")
public class IngredientViewModel extends BaseViewModel {
    public final MutableLiveData<Boolean> isLoading = new MutableLiveData<>(false);
    public final MutableLiveData<String> filePath = new MutableLiveData<>();
    public final MutableLiveData<List<Ingredient>> ingredientList = new MutableLiveData<>(new ArrayList<>());

    private PublishSubject<List<Ingredient>> resultList = PublishSubject.create();

    public IngredientViewModel() {
        resultList.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(list -> {
                    this.isLoading.setValue(false);
                    this.ingredientList.setValue(list);
                }, error -> {
                    this.isLoading.setValue(false);
                    error.printStackTrace();
                });
    }

    void init(String filePath) {
        this.filePath.setValue(filePath);
        startAnalyze();
    }

    public void startAnalyze() {
        String filePath = this.filePath.getValue();
        if (filePath == null) {
            return;
        }

        FirebaseVisionImage image = VisionImageGetter
                .getImageFromFilePath(App.instance().getContext(), filePath);

        FirebaseVisionCloudTextRecognizerOptions options =
                new FirebaseVisionCloudTextRecognizerOptions.Builder()
                        .setLanguageHints(Arrays.asList("en", "ko")).build();

        this.isLoading.setValue(true);
        FirebaseVision.getInstance()
                .getCloudTextRecognizer(options)
                .processImage(image)
                .addOnSuccessListener(result -> {
                    List<Ingredient> list = new ArrayList<>();
                    for (Ingredient item : Ingredient.values()) {
                        for (FirebaseVisionText.TextBlock textBlock : result.getTextBlocks()) {
                            if (textBlock.getText().contains(item.getName())) {
                                list.add(item);
                                break;
                            }
                        }
                    }
                    resultList.onNext(list);
                })
                .addOnFailureListener(error -> {
                    resultList.onError(error);
                    error.printStackTrace();
                });
    }

}
