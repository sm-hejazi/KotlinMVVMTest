package com.ppp_smh.initlibrary.ui;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.ppp_smh.initlibrary.R;


class ConnectionFailurePopup extends PopupWindow {
    public Throwable throwable;
    private View.OnClickListener onRetryClick;
    private View.OnClickListener onCancelClick;
    private final Context context;
    private PopupWindow popupWindow;
    private TextView txtFailureDesc;

    public ConnectionFailurePopup(Context context) {
        this.context = context;
    }

    public void show() {
        View parent = ((Activity) context).getWindow().getDecorView().getRootView();
        show(parent);
    }

    private void show(final View parent) {
        try {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            @SuppressLint("InflateParams") final View popupView = layoutInflater.inflate(R.layout.request_failure_dialog, null);

            txtFailureDesc = popupView.findViewById(R.id.txtFailureDesc);
            popupWindow = new PopupWindow(
                    popupView,
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            );

            // Set retry button
            ImageButton btnRetry = (ImageButton) popupView.findViewById(R.id.btnRetry);
            if (onRetryClick == null) btnRetry.setVisibility(View.INVISIBLE);
            else btnRetry.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    popupWindow.dismiss();
                    onRetryClick.onClick(v);
                }
            });

            // Set cancel button
            ImageButton btnCancel = (ImageButton) popupView.findViewById(R.id.btnCancel);
            if (onCancelClick == null) onCancelClick = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    popupWindow.dismiss();
                }
            };
            btnCancel.setOnClickListener(onCancelClick);

            parent.post(new Runnable() {
                public void run() {
                    try {
                        popupWindow.showAtLocation(parent, Gravity.BOTTOM, 0, 0);
                    } catch (Exception ex) {
                        Log.e("",ex.getMessage());
                    }
                }
            });
        } catch (Exception ex) {
            Log.e("",ex.getMessage());
        }
    }

    public void setDescription(String msg){
        txtFailureDesc.setVisibility(View.VISIBLE);
        txtFailureDesc.setText(msg);
    }
}
