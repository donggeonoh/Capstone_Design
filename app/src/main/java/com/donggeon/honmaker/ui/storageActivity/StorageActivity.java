package com.donggeon.honmaker.ui.storageActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;

import com.donggeon.honmaker.R;
import com.donggeon.honmaker.data.Ingredient;
import com.donggeon.honmaker.databinding.ActivityStorageBinding;
import com.donggeon.honmaker.extension.Retrofit.RetrofitAPI;
import com.donggeon.honmaker.extension.Retrofit.RetrofitClient;
import com.donggeon.honmaker.ui.BaseActivity;
import com.donggeon.honmaker.ui.ingredient.IngredientAdapter;
import com.donggeon.honmaker.ui.ingredient.Place;
import com.google.firebase.auth.FirebaseAuth;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StorageActivity extends BaseActivity<ActivityStorageBinding> {

    private static final String EXTRA_PLACE = "EXTRA_PLACE";
    private Place place;

    public static Intent getLaunchIntent(@NonNull Context context,
                                         @NonNull Place place) {
        Intent intent = new Intent(context, StorageActivity.class);
        intent.putExtra(EXTRA_PLACE, place);
        return intent;
    }

    private void getIntentExtras(@NonNull Intent intent) {
        Bundle args = intent.getExtras();
        if (args != null) {
            place = (Place) args.getSerializable(EXTRA_PLACE);
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_storage;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getIntentExtras(getIntent());
        initViewModels();
        initViews();
    }

    private void initViewModels() {
        binding.setVm(ViewModelProviders.of(this).get(StorageViewModel.class));
        binding.getVm().setPlace(place);
    }
    
    private void initViews() {
        binding.rvIngredient.setLayoutManager(new GridLayoutManager(this, 4));
        binding.rvIngredient.setAdapter(new IngredientAdapter(this::startIngredientActivity));
    }
    
    public void startIngredientActivity(Ingredient ingredient) {
        
        AlertDialog ad = new AlertDialog.Builder(this)
                .setTitle("삭제")
                .setMessage("삭제하시겠습니까?")
                .setPositiveButton("확인", (dialog, which) -> {
                    
                    RetrofitAPI api = RetrofitClient.retrofit.create(RetrofitAPI.class);
                    Call<String> call = api.deleteIngredient(FirebaseAuth.getInstance().getUid(), ingredient.getName());
                    
                    call.enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(Call<String> call, Response<String> response) {
                            String item = response.body();
                            Log.d("Storagddddd", item);
                            if(item == null) {
                                Log.d("delete ingredient","값이 비어있습니다.");
                                return;
                            }
                            
                            Toast.makeText(getApplicationContext(), "재료가 삭제되었습니다", Toast.LENGTH_SHORT).show();
                            Log.d("delete ingredient", "재료가 삭제되었습니다.");
                            
                            binding.getVm().loadIngredientList();
                        }
    
                        @Override
                        public void onFailure(Call<String> call, Throwable t) {
                            t.printStackTrace();
                        }
                    });
                    
                }).setNegativeButton("취소", (dialog, which) -> {
            
                }).create();
        
        ad.show();
    }
}