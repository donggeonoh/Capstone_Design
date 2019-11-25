package com.donggeon.honmaker.ui.ingredient;

import android.annotation.SuppressLint;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.donggeon.honmaker.App;
import com.donggeon.honmaker.data.Ingredient;
import com.donggeon.honmaker.data.Text;
import com.donggeon.honmaker.extension.Retrofit.RetrofitAPI;
import com.donggeon.honmaker.extension.Retrofit.RetrofitClient;
import com.donggeon.honmaker.extension.mlkit.VisionImageGetter;
import com.donggeon.honmaker.ui.BaseViewModel;
import com.google.firebase.auth.FirebaseAuth;
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
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@SuppressLint("CheckResult")
public class IngredientViewModel extends BaseViewModel {
    
    public final MutableLiveData<String> filePath = new MutableLiveData<>();
    private static final MutableLiveData<List<Ingredient>> ingredientList = new MutableLiveData<>(new ArrayList<>());
    private PublishSubject<List<Ingredient>> resultList = PublishSubject.create();
    
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
                    StringBuilder builder = new StringBuilder();
                    for (FirebaseVisionText.TextBlock textBlock : result.getTextBlocks()) {
                        builder.append(textBlock.getText());
                    }
                    Log.d("builder", builder.toString());
                    loading.setValue(false);
                    requestIngredients(builder.toString());
                })
                .addOnFailureListener(error -> {
                    resultList.onError(error);
                    error.printStackTrace();
                });
    }
    
    private void requestIngredients(String toString) {
    
        Log.d("Login", "Sign in anonymously : " + FirebaseAuth.getInstance().getUid());
        
        RetrofitAPI api = RetrofitClient.retrofit.create(RetrofitAPI.class);
        Call<List<Ingredient>> call = api.ingredient(new Text(FirebaseAuth.getInstance().getUid(), toString));
        
        call.enqueue(new Callback<List<Ingredient>>() {
            @Override
            public void onResponse(Call<List<Ingredient>> call, Response<List<Ingredient>> response) {
                List<Ingredient> result = response.body();
                
                if(result == null) {
                
                } else if(result.isEmpty()) {
                
                }
                
                for (Ingredient data : result) {
                    Log.d("ingredient", "response : " + data.getName() + " " + data.getImageUri() + " " + data.getPlace());
                }
    
                loading.setValue(false);
                ingredientList.setValue(result);
            }
            
            @Override
            public void onFailure(Call<List<Ingredient>> call, Throwable t) {
            
            }
        });
    }
    
    public static void setValue(List<Ingredient> list) {
        
        if (list == null) {
            return;
        }
        
        for (Ingredient item : list) {
            if (!ingredientList.getValue().contains(item)) {
                ingredientList.getValue().add(item);
            }
        }
    }
    
    public static MutableLiveData<List<Ingredient>> getIngredientList() {
        return ingredientList;
    }
}
