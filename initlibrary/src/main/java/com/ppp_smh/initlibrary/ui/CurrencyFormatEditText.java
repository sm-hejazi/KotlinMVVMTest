package com.ppp_smh.initlibrary.ui;

import android.content.Context;
import androidx.appcompat.widget.AppCompatEditText;
import android.util.AttributeSet;

import java.text.NumberFormat;
import java.util.Locale;

public class CurrencyFormatEditText extends AppCompatEditText {
    public CurrencyFormatEditText(Context context) {
        super(context);
    }

    public CurrencyFormatEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CurrencyFormatEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    BufferType type;

    @Override
    public void setText(CharSequence text, BufferType type) {
             super.setText(comaSeprate(text), type);
    }

    @Override
    protected void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter);
    }


    private CharSequence comaSeprate(CharSequence text){
        if (((CharSequence)text).length() > 0) {
            text = NumberFormat.getNumberInstance(Locale.US).format(Double.parseDouble(((CharSequence)text).toString()));
        }
        return text;
    }
}
