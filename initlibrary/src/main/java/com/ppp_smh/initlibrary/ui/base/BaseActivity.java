package com.ppp_smh.initlibrary.ui.base;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.ppp_smh.initlibrary.entity.ErrorStatus.ErrorModel;
import com.ppp_smh.initlibrary.ui.apptoolbar.AppToolbarActivity;

import dagger.android.AndroidInjection;

/**
 * Created by m.hejazi on 5/7/18.
 */

public abstract class BaseActivity<V extends BaseViewModel> extends AppToolbarActivity implements BaseView<V> {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        AndroidSupportInjection.inject(this);
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        // TODO: 3/5/18 do some Actions after checking Connection
        getViewModel().checkConnection();
    }

    @Override
    protected void onStart() {
        super.onStart();
        getViewModel().onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        getViewModel().onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        getViewModel().onClear();
        getViewModel().clearUseCaseDisposables();
    }

/*    @Override
    public void setBaseVM(BaseViewModel baseViewModel) {
        super.setBaseVM(baseViewModel);
    }*/

    @Override
    public void showError(ErrorModel error) {
        Toast.makeText(this, error.getStatus() + " : " + error.getMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    /*@Override
    public void onVisibilityChanged(boolean isOpen) {}*/

}
