package com.donggeon.honmaker.ui.ingredient;

import android.annotation.SuppressLint;
import android.util.Log;

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

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

@SuppressLint("CheckResult")
public class IngredientViewModel extends BaseViewModel {
    public final MutableLiveData<Boolean> isLoading = new MutableLiveData<>(false);
    public final MutableLiveData<String> filePath = new MutableLiveData<>();
    public final MutableLiveData<List<Ingredient>> ingredientList = new MutableLiveData<>(new ArrayList<>());

    private static final List<Ingredient> INGREDIENT_DATA =
            new ArrayList<>(Arrays.asList(
                    Ingredient.HAM,
                    Ingredient.CHEEZE,
                    Ingredient.TUNA));

    public void analyze() {
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
        Observable.create(
                emitter -> {
                    FirebaseVision.getInstance()
                            .getCloudTextRecognizer(options)
                            .processImage(image)
                            .addOnSuccessListener(result -> {
                                for (FirebaseVisionText.TextBlock textBlock : result.getTextBlocks()) {
                                    for (Ingredient item : INGREDIENT_DATA) {
                                        if (textBlock.getText().contains(item.getName())) {
                                            Log.d("ImageVision", "item : " + item.getName());
                                            emitter.onNext(item);
                                        }
                                    }
                                }
                            })
                            .addOnFailureListener(error -> {
                                emitter.onError(error);
                                error.printStackTrace();
                            });
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(item -> {
                    this.isLoading.setValue(false);
                    List<Ingredient> list = ingredientList.getValue();
                    if (list == null) {
                        list = new ArrayList<>();
                    }
                    list.add((Ingredient) item);
                    this.ingredientList.setValue(ingredientList.getValue());
                }, Throwable::printStackTrace);
    }

    public void init(String filePath) {
        this.filePath.setValue(filePath);
    }
}
