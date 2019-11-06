package com.donggeon.honmaker.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;

/**
 * 모든 프래그먼트는 BaseFragment 를 상속
 * 이거 쓰는 이유 : 데이터바인딩, 라이프사이클 등록을 한번에 처리하기 위함
 * <p>
 * <B extends ViewDataBinding>
 * 레이아웃 이름이 예를들어 fragment_camera.xml 이면,
 * 데이터바인딩 클래스 이름은 "FragmentCameraBinding" 으로 정해짐
 */
public abstract class BaseFragment<B extends ViewDataBinding> extends Fragment {

    protected B binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        // 데이터 바인딩 등록
        binding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false);
        // 라이프사이클 등록
        binding.setLifecycleOwner(this);

        // 액티비티는 메니페스트에 등록되지만 프레그먼트는 뷰를 직접 inflating 해서 리턴해주어야 함
        return binding.getRoot();
    }

    // 자식클래스에서 액티비티와 연결된 layout id 를 넘겨주도록 강제
    protected abstract int getLayoutId();
}