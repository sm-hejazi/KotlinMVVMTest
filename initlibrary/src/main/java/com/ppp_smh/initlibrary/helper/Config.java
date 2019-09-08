package com.ppp_smh.initlibrary.helper;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;
import android.view.View;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.Properties;

public class Config {

    //region Method
    private static boolean mIsPropertyFileLoaded = false;
    //Web Response Error
    public static String error_desc_null_empty = "NULL_OR_EMPTY";

    /**
     * Whether property file is loaded or default config is loaded.
     *
     * @return If property file is loaded return true else false.
     */
    public static boolean isPropertyFileLoaded() {
        return mIsPropertyFileLoaded;
    }

    /**
     * Load configuration from a properties file in assets directory
     *
     * @param context            Application context.
     * @param propertiesFileName Full name with extension of property file.
     * @return True if config file loaded successfully else return false.
     */
    public static boolean loadFromPropertyFile(Context context, String propertiesFileName) {
        try {
            AssetManager assetManager = context.getAssets();
            InputStream inputStream = assetManager.open(propertiesFileName);
            Properties properties = new Properties();
            properties.load(inputStream);

            for (Field field : Config.class.getDeclaredFields()) {
                if (properties.containsKey(field.getName())) {
                    try {
                        Object value = Utility.getPropertiesValue(properties, field.getName(), field.getType());
                        field.setAccessible(true);
                        field.set(null, value);
                    } catch (Exception ex) {
                        Log.e("",
                                "Can't set " + field.getName() + " from config property file." +
                                        "Check data type in property file is the same with config field.");
                    }
                }
            }
            mIsPropertyFileLoaded = true;
            return true;

        } catch (Exception ex) {
            Log.e("",ex.getMessage());
            return false;
        }
    }

    //endregion

    //region Default Configuration

    //Web Server Path
    private static final String web_server_base_url = "http://www.mysite.com/";


    //Web Connection
    public static int connection_timeout = 45000;
    public static int connection_read_timeout = 60000;
    public static int connection_write_timeout = 60000;
    public static int response_timeout = 60000;

    public static final int direction = View.LAYOUT_DIRECTION_RTL;

    public static boolean imeiRandom = false;


    public static String default_json_preference_key = "json_preference";



    public static String getWebServerDomainName() {
        Log.e("","--- Config getWebServerDomainName web_server_base_url --- " + web_server_base_url);
        try {
            URL url = new URL(web_server_base_url);
            return url.getHost();
        } catch (Exception ex) {
            Log.e("",ex.getMessage());
            return null;
        }
    }

    public static String getWebServerRootUrl() {
        try {
            URL url = new URL(web_server_base_url);
            return url.getProtocol() + "://" + url.getHost();
        } catch (Exception ex) {
            Log.e("",ex.getMessage());
            return null;
        }
    }

    public static String getAbsoluteUrl(String url) {
        if (url != null && !url.startsWith(Config.web_server_base_url))
            return Config.web_server_base_url + url;
        else return url;
    }

    //endregion

}
