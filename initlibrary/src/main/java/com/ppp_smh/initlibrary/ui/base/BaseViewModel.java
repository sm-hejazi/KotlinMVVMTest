package com.ppp_smh.initlibrary.ui.base;

import androidx.databinding.BaseObservable;
import androidx.databinding.ObservableBoolean;
import android.util.Log;

import com.ppp_smh.initlibrary.util.connectivity.BaseConnectionManager;
import com.ppp_smh.initlibrary.util.navigator.BaseNavigator;

import io.reactivex.disposables.CompositeDisposable;


/**
 * Created by m.hejazi on 5/7/18.
 */
public abstract class BaseViewModel extends BaseObservable {

    private final static String TAG = BaseViewModel.class.getSimpleName();
    private final CompositeDisposable compositeDisposable;
    private final BaseConnectionManager connectionManager;
    private final BaseNavigator navigator;

    protected final ObservableBoolean isLoading = new ObservableBoolean(false);
    protected final ObservableBoolean isLoadingBg = new ObservableBoolean(false);
    private final ObservableBoolean showActionHome = new ObservableBoolean(false);
    private final ObservableBoolean showActionSearch = new ObservableBoolean(false);
    private final ObservableBoolean showActonFilter = new ObservableBoolean(false);


    protected BaseViewModel(BaseConnectionManager connectionManager, BaseNavigator navigator) {
        this.connectionManager = connectionManager;
        this.navigator = navigator;
        this.compositeDisposable = new CompositeDisposable();
    }

    public CompositeDisposable getCompositeDisposable() {
        return compositeDisposable;
    }

    public void onClear() {
        compositeDisposable.clear();
    }

    public abstract void clearUseCaseDisposables();

    public void checkConnection() {
        if (connectionManager.isVpnConnected()) {
            Log.d(TAG, "checkConnection() : VPN connected");
        }
    }

    public void onTokenExpired(Class cls) {
        navigator.onTokenExpired(cls);
    }

    /**
     * We can use lifeCycle in inherited classes if we need
     */
    public void onStart() {
    }

    /**
     * We can use lifeCycle in inherited classes if we need
     */
    public void onStop() {
        onClear();
    }

    public ObservableBoolean getIsLoadingBg() {
        return isLoadingBg;
    }

    public ObservableBoolean getIsLoading() {
        return isLoading;
    }

    public ObservableBoolean getShowActionHome() {
        return showActionHome;
    }

    public ObservableBoolean getShowActionSearch() {
        return showActionSearch;
    }

    public ObservableBoolean getShowActonFilter() {
        return showActonFilter;
    }

    public void setShowActionHome(boolean state) {
        this.showActionHome.set(state);
    }

    public void setShowActionSearch(boolean state) {
        this.showActionSearch.set(state);
    }

    public void setShowActonFilter(boolean state) {
        this.showActonFilter.set(state);
    }

}
