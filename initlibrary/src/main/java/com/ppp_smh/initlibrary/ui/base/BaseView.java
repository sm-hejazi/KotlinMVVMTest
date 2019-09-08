package com.ppp_smh.initlibrary.ui.base;


import com.ppp_smh.initlibrary.entity.ErrorStatus.ErrorModel;

/**
 * Created by m.hejazi on 5/7/18.
 */

public interface BaseView<V extends BaseViewModel> {
    V getViewModel();

    void showError(ErrorModel error);

    void showMessage(String message);

//    void requestPermission(String...permission);

}
