package com.ppp_smh.initlibrary.ui.apptoolbar;

import android.app.Dialog;
import android.content.Context;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.ppp_smh.initlibrary.R;
import com.ppp_smh.initlibrary.databinding.AppToolbarActivityBinding;
import com.ppp_smh.initlibrary.helper.Config;
import com.ppp_smh.initlibrary.ui.base.BaseViewModel;

import java.util.Stack;

import dagger.android.support.DaggerAppCompatActivity;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;


public class AppToolbarActivity extends DaggerAppCompatActivity {

    //region Property
    public AppToolbarActivityBinding appToolbarActivityBinding;

    private Dialog baseDialog;
    Stack<Integer> loadingProgressStack = new Stack<>();
    private BaseViewModel baseViewModel;



    private boolean fullLoading,isTitle;

    //endregion

    //region Override
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }


    public void setContentView(View view) {

        appToolbarActivityBinding = DataBindingUtil.setContentView(this, R.layout.app_toolbar_activity);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT
        );


        appToolbarActivityBinding.baseContentFrame.addView(view, lp);
        appToolbarActivityBinding.getRoot().setLayoutDirection(Config.direction);

    }


    @Override
    public void onBackPressed() {
        if (!baseViewModel.getIsLoading().get())
            super.onBackPressed();
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        init(savedInstanceState);
    }

    @Override
    public void onPostCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onPostCreate(savedInstanceState, persistentState);
        init(savedInstanceState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_toolbar, menu);
        MenuItem shareItem = menu.findItem(R.id.action_search);
        if (shareItem != null)
            shareItem.setVisible(false);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        updateActionMenu(menu);
        return super.onPrepareOptionsMenu(menu);
    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//
//        return super.onOptionsItemSelected(item);
//    }


    //endregion

    //region Init
    private void init(Bundle savedInstanceState) {
        initUI(savedInstanceState);
//        initExtra(getIntent().getExtras());
//        initData(isConnectToNetwork());
        setupUI();
    }

    public void initUI(Bundle savedInstanceState) {
        baseDialog = new Dialog(this);
    }



    public void setupUI() {
        // Set toolbar
        setSupportActionBar(appToolbarActivityBinding.baseToolbar);

        // check Login true show user Icon
//        if (FreightApp.getInstance().login)
//            appToolbarActivityBinding.imgUser.setVisibility(View.VISIBLE);
//        else
//            appToolbarActivityBinding.imgUser.setVisibility(View.GONE);


        getSupportActionBar().setDisplayShowTitleEnabled(true);

/*        getSupportActionBar().setDisplayShowHomeEnabled(true); // show or hide the default home button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowCustomEnabled(true); // enable overriding the default toolbar layout*/
//        baseToolbar.setLogo(R.drawable.logo);

        // Set dialog
        baseDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        // Set loading progress
        appToolbarActivityBinding.baseProgress.getIndeterminateDrawable().setColorFilter(getResources().getColor(R.color.colorAccent), PorterDuff.Mode.SRC_IN);
    }

    //endregion


    //region Method

    public void setBaseVM(BaseViewModel baseViewModel) {
        this.baseViewModel = baseViewModel;
        appToolbarActivityBinding.setViewModel(baseViewModel);

    }

    public void updateActionMenu(Menu menu){
        if (baseViewModel.getShowActionHome().get()) {
            getSupportActionBar().setDisplayShowHomeEnabled(true); // show or hide the default home button
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        setMenuVisibility(R.id.action_search,baseViewModel.getShowActionSearch().get(),menu);
        setMenuVisibility(R.id.action_filter,baseViewModel.getShowActonFilter().get(),menu);
    }

    private void setMenuVisibility(int id, boolean visible, Menu menu) {
        try {
//            invalidateOptionsMenu();
            if (menu == null)
                menu = appToolbarActivityBinding.baseToolbar.getMenu();
            MenuItem menuItem = menu.findItem(id);
            if (menuItem != null)
                menuItem.setVisible(visible);
        } catch (Exception ex) {
            Log.e("", ex.getMessage());
        }
    }

    public void showRequestFailureDialog(@Nullable Throwable throwable, @Nullable View.OnClickListener onRetryClick, @Nullable View.OnClickListener onCancelClick) {

        // Set baseDialog
        baseDialog.setContentView(R.layout.request_failure_dialog);
        //baseDialog.setCanceledOnTouchOutside(false);
        baseDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        baseDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL);

        // Set retry button
        ImageButton btnRetry = (ImageButton) baseDialog.findViewById(R.id.btnRetry);
        if (onRetryClick == null) btnRetry.setVisibility(View.INVISIBLE);
        else btnRetry.setOnClickListener(onRetryClick);

        // Set cancel button
        ImageButton btnCancel = (ImageButton) baseDialog.findViewById(R.id.btnCancel);
        if (onCancelClick == null) onCancelClick = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                baseDialog.dismiss();
            }
        };
        btnCancel.setOnClickListener(onCancelClick);

        baseDialog.show();

    }

    //endregion

}
