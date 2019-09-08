package com.ppp_smh.initlibrary.ui.base;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.ppp_smh.initlibrary.entity.ErrorStatus.ErrorModel;

import dagger.android.support.AndroidSupportInjection;
import dagger.android.support.DaggerFragment;

/**
 * Created by m.hejazi on 5/7/18.
 */

public abstract class BaseFragment<V extends BaseViewModel> extends DaggerFragment implements BaseView<V> {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        AndroidSupportInjection.inject(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        getViewModel().onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        getViewModel().onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getViewModel().onClear();
        getViewModel().clearUseCaseDisposables();
    }

    @Override
    public void showError(ErrorModel error) {
        Toast.makeText(getContext(), error.getStatus() + " : " + error.getMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    /*@Override
    public void requestPermission(String... permission) {
    }*/
}
