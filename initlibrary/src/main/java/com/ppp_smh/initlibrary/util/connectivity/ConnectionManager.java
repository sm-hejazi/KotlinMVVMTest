package com.ppp_smh.initlibrary.util.connectivity;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;

import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by m.hejazi on 5/7/18.
 */

public class ConnectionManager implements BaseConnectionManager {
    private static final int CONNECTION_TIMEOUT = 500;
    private Context mContext;
    private final ConnectivityManager mConnectivityManager;

    public ConnectionManager(Context context) {
        this.mContext = context;
        this.mConnectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
    }

    @Override
    public boolean isNetworkConnected() {
        NetworkInfo info = mConnectivityManager.getActiveNetworkInfo();

        try {
            return info != null && info.isConnectedOrConnecting() && InetAddress.getByName("www.google.com").isReachable(CONNECTION_TIMEOUT);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean isVpnConnected() {
        NetworkInfo info = mConnectivityManager.getActiveNetworkInfo();
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP
                && info != null
                && info.getType() == ConnectivityManager.TYPE_VPN;
    }

    @Override
    public String getIPV4() {
        try {
            ArrayList<NetworkInterface> interfaces = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface nInterface : interfaces) {
                ArrayList<InetAddress> inetAddresses = Collections.list(nInterface.getInetAddresses());
                for (InetAddress address : inetAddresses) {
                    if (!address.isLinkLocalAddress()) {
                        String hostAddress = address.getHostAddress();
                        if (!hostAddress.contains(":"))
                            return hostAddress;
                    }
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }
        return "";
    }

    @Override
    public boolean isWifi() {
        NetworkInfo info = mConnectivityManager.getActiveNetworkInfo();
        return info != null && info.getType() == ConnectivityManager.TYPE_WIFI;
    }

    @Override
    public boolean isMobileData() {
        NetworkInfo info = mConnectivityManager.getActiveNetworkInfo();
        return info != null && info.getType() == ConnectivityManager.TYPE_MOBILE;
    }

}
