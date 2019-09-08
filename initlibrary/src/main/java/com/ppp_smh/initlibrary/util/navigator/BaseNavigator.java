package com.ppp_smh.initlibrary.util.navigator;

import android.content.Intent;
import android.os.Bundle;

import java.lang.ref.WeakReference;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

/**
 * Handles navigation between Activities in the app.
 */
public interface BaseNavigator<T extends AppCompatActivity> {

    /**
     * get navigator activity
     *
     * @return
     */
    WeakReference<T> getActivity();

    /**
     * Start an Activity
     *
     * @param cls the Activity class to be opened.
     */
    default void startActivity(Class cls) {
        if (getActivity().get() != null) {
            Intent intent = new Intent(getActivity().get(), cls);
            getActivity().get().startActivity(intent);
        }
    }

    /**
     * Start an Activity
     *
     * @param cls    the Activity class to be opened.
     * @param bundle which provides from getCallingBundle()
     */
    default void startActivity(Class cls, Bundle bundle) {
        if (getActivity().get() != null) {
            Intent intent = new Intent(getActivity().get(), cls);
            intent.putExtras(bundle);
            getActivity().get().startActivity(intent);
        }
    }

    /**
     * Finish an Activity
     */
    default void finishActivity() {
        if (getActivity().get() != null) {
            getActivity().get().finish();
        }
    }

    /**
     * Finish an Activity with a result.
     *
     * @param resultCode the result code to be set when finishing the Activity.
     */
    default void finishActivityWithResult(int resultCode, Bundle bundle) {
        if (getActivity().get() != null) {
            Intent intent = new Intent();
            intent.putExtras(bundle);
            getActivity().get().setResult(resultCode, intent);
            getActivity().get().finish();
        }
    }

    /**
     * Start a new Activity for a result.
     *
     * @param cls         the Activity class to be opened.
     * @param requestCode the request code that will be passed to the opened Activity.
     */
    default void startActivityForResult(Class cls, int requestCode) {
        if (getActivity().get() != null) {
            Intent intent = new Intent(getActivity().get(), cls);
            getActivity().get().startActivityForResult(intent, requestCode);
        }
    }

    /**
     * Start a new Activity for a result.
     *
     * @param cls         the Activity class to be opened.
     * @param requestCode the request code that will be passed to the opened Activity.
     * @param bundle      bundle for send value to Activity.
     */
    default void startActivityForResult(Class cls, int requestCode, Bundle bundle) {
        if (getActivity().get() != null) {
            Intent intent = new Intent(getActivity().get(), cls);
            intent.putExtras(bundle);
            getActivity().get().startActivityForResult(intent, requestCode);
        }
    }

    /**
     * Start a new Activity for a result with an extra
     *
     * @param cls        the Activity class to be opened.
     * @param requestCode the request code that will be passed to the opened Activity.
     * @param extraKey   the key for the extra that is passed in the Intent.
     * @param extraValue the value for the extra the is passed in the Intent.
     */
//    void startActivityForResultWithExtra(Class cls, int requestCode, String extraKey, String extraValue, Bundle bundle);

    /**
     * @return SupportFragmentTransaction from activity which provided in constructor
     */
    default FragmentTransaction getSFragmentTransaction(int... animMove) {
        if (getActivity().get() != null) {
            FragmentTransaction fragmentTransaction = getActivity().get().getSupportFragmentManager().beginTransaction();
            if (animMove == null || animMove.length == 0)
                return fragmentTransaction;
            else if (animMove.length == 2)
                return fragmentTransaction.setCustomAnimations(animMove[0], animMove[1]);
            else if (animMove.length == 4)
                return fragmentTransaction.setCustomAnimations(animMove[0], animMove[1], animMove[2], animMove[3]);
            else return fragmentTransaction;
        } else {
            throw new NullPointerException();
        }
    }

    default android.app.FragmentManager getFragManager(){
        if (getActivity().get() != null) {
            return getActivity().get().getFragmentManager();
        } else {
            throw new NullPointerException();
        }
    }

    /**
     * @return FragmentManager from activity which provided in constructor
     */
    default FragmentManager getFragmentManager() {
        if (getActivity().get() != null) {
            return getActivity().get().getSupportFragmentManager();
        } else {
            throw new NullPointerException();
        }
    }

    default void onTokenExpired(Class cls) {
        if (getActivity().get() != null) {
            Intent intent = new Intent(getActivity().get(), cls);// Todo login again
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            getActivity().get().startActivity(intent);
        }
    }
}
