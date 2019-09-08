package com.ppp_smh.initlibrary.helper;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import androidx.core.content.ContextCompat;
import android.util.Log;

import java.util.Properties;

/**
 * Created by BP-SHARIFI on 1/16/2018.
 */

public class Utility {

    public static Typeface getPersianFont(Context context) {
        return Typeface.createFromAsset(context.getAssets(), Constants.FONT_PATH);
    }

    public static String commaSeparate(String reshteh) {
        StringBuilder ss = new StringBuilder();
        int count = 0;
        for (int i = reshteh.length() - 1; i >= 0; i--) {
            count++;
            if (count == 3 & i != 0) {
                count = 0;
                ss.append(reshteh.charAt(i));
                ss.append(",");
            } else
                ss.append(reshteh.charAt(i));
        }
        return ss.reverse().toString();
    }

    public static int getDynamicDrawable(String name, Context context) {
        int nameResourceID = context.getResources().getIdentifier(name, "drawable", context.getApplicationInfo().packageName);
        if (nameResourceID == 0) {
            throw new IllegalArgumentException(
                    "No resource drawable found with name " + name);
        } else {
            return nameResourceID;
        }
    }

    private static String cleanCardNumber(String num){
        num = num.replace("-", "");
        num =  num.trim();
        if (num.length() > 6)
            num = num.substring(0,6);
        return num;
    }



   /* public static String getBankName(String num) {
        num = cleanCardNumber(num);
        if (KianApp.cardBankNum.containsKey(num)) {
            return KianApp.cardBankNum.get(num);
        } else  return "";
    }

    public static boolean isValidCardBank(String num){
        num = cleanCardNumber(num);
        return  KianApp.cardBankNum.containsKey(num);
    }

    public static boolean isValidBankFrom(String num) {
        num = cleanCardNumber(num);
        if (KianApp.validCardBankNum.containsKey(num)) {
                return true;
        } else  return false;
    }

    public static String validBankFromName(){
        String validate = "";
        for (Object o : KianApp.validCardBankNum.keySet()) {
            validate = KianApp.validCardBankNum.get(o) + ", ";
        }
        validate = validate.substring(0,validate.length()-2);

        return validate;
    }*/


    public static Object getPropertiesValue(Properties properties, String propertyName, Class<?> type) throws Exception {
        Object value;
        try {
            if (!properties.containsKey(propertyName)) {
                return null;
            }
            String strValue = properties.getProperty(propertyName);

            if (type.equals(Integer.class) ||
                    type.equals(int.class)) {
                value = Integer.parseInt(strValue);
            } else if (type.equals(long.class) ||
                    type.equals(Long.class)) {
                value = Long.parseLong(strValue);
            } else if (type.equals(char.class) ||
                    type.equals(String.class) ||
                    type.equals(CharSequence.class) ||
                    type.equals(Character.class)) {
                value = strValue;
            } else if (type.equals(Boolean.class) ||
                    type.equals(boolean.class) ||
                    type.equals(short.class) ||
                    type.equals(Short.class)) {
                value = Boolean.parseBoolean(strValue);
            } else if (type.equals(Double.class) ||
                    type.equals(double.class)) {
                value = Double.parseDouble(strValue);
            } else {
                throw new Exception("Can't find proper casting method data type for " + type.getName());
            }

            return value;
        } catch (Exception ex) {
            Log.e("",ex.getMessage());
            throw ex;
        }
    }

    public static boolean isConnectToNetwork(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }

    public static Drawable changeIconColor(Context context, int icon, int newColor) {
        Drawable mDrawable = ContextCompat.getDrawable(context, icon).mutate();
//        Drawable mDrawable = context.getResources().getDrawable(icon);
        mDrawable.setColorFilter(new PorterDuffColorFilter(context.getResources().getColor(newColor), PorterDuff.Mode.SRC_IN));
        return mDrawable;
    }

    /*public static String getImei() {
        TelephonyManager tm = (TelephonyManager) MbankApplication.getInstance().getApplicationContext()
                .getSystemService(MbankApplication.getInstance().getApplicationContext().TELEPHONY_SERVICE);
        if (ActivityCompat.checkSelfPermission(MbankApplication.getInstance().getApplicationContext(), Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            return "";
        }
        return tm.getDeviceId();
    }*/

}
