package com.ppp_smh.initlibrary.util.connectivity;

/**
 * Created by m.hejazi on 5/7/18.
 */

public interface BaseConnectionManager {
    boolean isNetworkConnected();

    boolean isVpnConnected();

    String getIPV4();

    boolean isWifi();

    boolean isMobileData();
}
