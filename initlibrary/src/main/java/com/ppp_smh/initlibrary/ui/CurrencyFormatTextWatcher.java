package com.ppp_smh.initlibrary.ui;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.TextView;

import com.ppp_smh.initlibrary.helper.Convert;

public class CurrencyFormatTextWatcher implements TextWatcher {
    private final TextView editText;
    private boolean b = false;

    public CurrencyFormatTextWatcher(TextView editText) {
        this.editText = editText;
    }

    public void afterTextChanged(Editable s) {
        this.editText.removeTextChangedListener(this);
        if (!this.b) {
            this.b = true;
            s.replace(0, s.length(), Convert.currencySeparate(s.toString()));
        }

        this.b = false;
        this.editText.addTextChangedListener(this);
    }

    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    public void onTextChanged(CharSequence s, int start, int before, int count) {
    }
}
