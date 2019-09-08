package com.ppp_smh.initlibrary.helper;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.SpannableStringBuilder;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.io.FileInputStream;
import java.io.InputStream;
import java.security.MessageDigest;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


class    Tools {
    public static final String ANDROID_SCHEMA = "http://schemas.android.com/apk/res/android";

    public static boolean isNullOrEmpty(View view) {
        try {
            if (view instanceof TextView) {
                return ((TextView) view).getText().toString().trim().length() == 0;
            } else throw new Exception("Undefined view type");
        } catch (Exception ex) {
            Log.e("",ex.getMessage());
            return false;
        }
    }

    public static boolean isNullOrEmpty(String text) {
        return (text == null || text.length() == 0 || text.trim().length() == 0);
    }

    public static String getMd5OfFile(String filePath) {
        String returnVal = "";
        try {
            InputStream input = new FileInputStream(filePath);
            byte[] buffer = new byte[1024];
            MessageDigest md5Hash = MessageDigest.getInstance("MD5");
            int numRead = 0;
            while (numRead != -1) {
                numRead = input.read(buffer);
                if (numRead > 0) {
                    md5Hash.update(buffer, 0, numRead);
                }
            }
            input.close();

            byte[] md5Bytes = md5Hash.digest();
            for (int i = 0; i < md5Bytes.length; i++) {
                returnVal += Integer.toString((md5Bytes[i] & 0xff) + 0x100, 16).substring(1);
            }
        } catch (Throwable t) {
            t.printStackTrace();
        }
        return returnVal.toUpperCase();
    }

    public static String getMd5OfFile(byte[] data) {
        String returnVal = "";
        try {
            MessageDigest md5Hash = MessageDigest.getInstance("MD5");

            byte[] md5Bytes = md5Hash.digest(data);
            for (int i = 0; i < md5Bytes.length; i++) {
                returnVal += Integer.toString((md5Bytes[i] & 0xff) + 0x100, 16).substring(1);
            }
        } catch (Throwable t) {
            t.printStackTrace();
        }
        return returnVal.toUpperCase();
    }

    public static boolean isConnectToNetwork(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }

    private static Integer tryParse(String value, Integer defaultValue) {
        Integer retVal;
        try {
            retVal = Integer.parseInt(value);
        } catch (Exception ex) {
            retVal = defaultValue;
        }
        return retVal;
    }

    public static Integer tryParse(String value) { return tryParse(value, null); }

    public static String randomString(int len) {
        final String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        Random rnd = new Random();
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++)
            sb.append(AB.charAt(rnd.nextInt(AB.length())));
        return sb.toString();
    }

    /*public static String[] getYear() {
        List<String> years = new ArrayList<>();
        PersianCalendarTool pct = new PersianCalendarTool();
        for (int i = pct.getIranianYear()-100; i < pct.getIranianYear()-10 ; i++){
            years.add(i+"");
        }
        return Convert.listToArray(String.class,years);
    }*/


    private static boolean startWithRtl(String text) {
        if (text == null || text.length() == 0 || text.trim().length() == 0) return false;
        return isRtlText(text.substring(0, 1));
    }

    private static boolean isRtlText(String text) {
        char[] chars = text.toCharArray();
        for (char c : chars) {
            if (c >= 0x5D0 && c <= 0x6ff) {
                return true;
            }
        }
        return false;
    }

    public static String fixRtl(String text) {
        if (startWithRtl(text))
            return "\u200e" + text;
        else return text;
    }

    public static CharSequence fixPersianNumber(CharSequence text) {
        // SpannableStringBuilder will loose its styles, so we skip it.
        if (text instanceof SpannableStringBuilder) return text;

        if (text != null) {
            StringBuilder sb = new StringBuilder();

            for (int i = 0; i < text.length(); ++i) {
                char c = text.charAt(i);
                if (c >= 48 && c <= 57) {
                    c = (char) (c + 1728);
                }

                sb.append(c);
            }

            text = sb;
        }

        return text;
    }

    public static boolean isValidIranMobile(String mobile) {
        Pattern pattern = Pattern.compile("09\\d{9}");
        Matcher matcher = pattern.matcher(mobile);
        return matcher.matches();
    }

    public static boolean isValidIranianNationalCode(String input) {
        // check if input has 10 digits that all of them are not equal
        if (!input.matches("^\\d{10}$")
                || input.equals("0000000000")
                || input.equals("1111111111")
                || input.equals("2222222222")
                || input.equals("3333333333")
                || input.equals("4444444444")
                || input.equals("5555555555")
                || input.equals("6666666666")
                || input.equals("7777777777")
                || input.equals("8888888888")
                || input.equals("9999999999"))
            return false;

        int check = Integer.parseInt(input.substring(9, 10));
        int sum = 0;
        char[] inputArray = input.toCharArray();
        for (int i = 0;i < 9; i++){
                sum += Character.getNumericValue(inputArray[i]) * (10 - i);
        }
        sum %= 11;

        return (sum < 2 && check == sum) || (sum >= 2 && check + sum == 11);
    }

    public static boolean isValidRangeDate(String year,int toYear){
        PersianCalendarTool pct = new PersianCalendarTool();
        int yearNow = pct.getIranianYear();
        String yearPrefix = String.valueOf(yearNow).substring(0,2);
        if (year.startsWith("0")){
            yearPrefix = String.valueOf(Integer.valueOf(yearPrefix)+1);
        }
        String inputYearStr = yearPrefix + year;
        int inputYearInt = Integer.valueOf(inputYearStr);
        return !(inputYearInt < yearNow || inputYearInt > yearNow+toYear);
    }

}
