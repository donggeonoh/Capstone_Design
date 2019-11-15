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

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;

@SuppressLint("CheckResult")
public class IngredientViewModel extends BaseViewModel {
    public final MutableLiveData<String> filePath = new MutableLiveData<>();
    public final MutableLiveData<List<Ingredient>> ingredientList = new MutableLiveData<>(new ArrayList<>());

    private PublishSubject<List<Ingredient>> resultList = PublishSubject.create();
    private PublishSubject<List<String>> resultList2 = PublishSubject.create();

    public IngredientViewModel() {
        resultList.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(list -> {
                    this.loading.setValue(false);
                    this.ingredientList.setValue(list);
                }, error -> {
                    this.loading.setValue(false);
                    this.error.setValue(error);
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

        this.loading.setValue(true);
        FirebaseVision.getInstance()
                .getCloudTextRecognizer(options)
                .processImage(image)
                .addOnSuccessListener(result -> {
                    List<String> list = new ArrayList<>();
    
                    for (FirebaseVisionText.TextBlock textBlock : result.getTextBlocks()) {
                        // TODO: 2019-11-15 textBlock을 list에 넣고 서버에 요청함
                        //  이후 응답을 받으면 재료가 local에 저장된다.
                        Log.e("List : ", textBlock.getText());
                    }
                    /*
                    List<Ingredient> list = new ArrayList<>();
                    for (Ingredient item : Ingredient.values()) {
                        for (FirebaseVisionText.TextBlock textBlock : result.getTextBlocks()) {
                            if (textBlock.getText().contains(item.getName())) {
                                list.add(item);
                                break;
                            }
                        }
                    }
                    resultList.onNext(list);*/
                })
                .addOnFailureListener(error -> {
                    resultList.onError(error);
                    error.printStackTrace();
                });
    }

}
