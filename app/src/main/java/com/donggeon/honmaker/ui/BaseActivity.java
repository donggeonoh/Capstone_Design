package com.donggeon.honmaker.ui;

import android.os.Build;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

 /**
 * 모든 액티비티는 BaseActivity 를 상속
 * 이거 쓰는 이유 : 데이터바인딩, 라이프사이클 등록을 한번에 처리하기 위함
 * <p>
 * <B extends ViewDataBinding>
 * 레이아웃 이름이 예를들어 activity_main.xml 이면,
 * 데이터바인딩 클래스 이름은 "ActivityMainBinding" 으로 정해짐
 */
public abstract class BaseActivity<B extends ViewDataBinding> extends AppCompatActivity {

    protected B binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 데이터 바인딩 등록
        binding = DataBindingUtil.setContentView(this, getLayoutId());
        // 라이프사이클 등록
        binding.setLifecycleOwner(this);
    }
    
    @Override
    protected void onResume() {
        super.onResume();
        // 상태바 글자색 검정색으로 변경
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
    }

    // 자식클래스에서 액티비티와 연결된 layout id 를 넘겨주도록 강제
    protected abstract int getLayoutId();
}
