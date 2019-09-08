package com.ppp_smh.initlibrary.util.providers;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.StringRes;

/**
 * Concrete implementation of the {@link BaseResourceProvider} interface.
 */
public class ResourceProvider implements BaseResourceProvider {

    @NonNull
    private final Context mContext;

    public ResourceProvider(@NonNull Context context) {
        mContext = context;
    }

    @NonNull
    @Override
    public String getString(@StringRes final int id) {
        return mContext.getString(id);
    }

    @NonNull
    @Override
    public String getString(@StringRes final int id, final Object... formatArgs) {
        return mContext.getString(id, formatArgs);
    }

    @NonNull
    public Context getmContext() {
        return mContext;
    }
}
