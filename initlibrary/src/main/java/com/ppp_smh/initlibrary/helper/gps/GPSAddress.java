package com.ppp_smh.initlibrary.helper.gps;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.AsyncTask;
import android.util.Log;

import java.util.List;
import java.util.Locale;

public class GPSAddress extends AsyncTask<Location, Void, GPSAddress> {
    private final Context mContext;
    private Double latitude = null;
    private Double longitude = null;
    private String addressText = "";

    private GPSAddress(Context context) {
        super();
        mContext = context;
    }

    private setOnLocationFoundListener onLocationFoundListener;

    interface setOnLocationFoundListener {
        void onLocationFound(GPSAddress gps);
    }

    private void setOnLocationFoundListener(setOnLocationFoundListener event) {
        if (event != null) {
            this.onLocationFoundListener = event;
        }
    }

    /**
     * Get a GeoCoder instance, get the latitude and longitude
     * look up the address, and return it
     */
    @Override
    protected GPSAddress doInBackground(Location... params) {

        try {
            Geocoder geocoder = new Geocoder(mContext, Locale.getDefault());

            // Get the current location from the input parameter list
            Location loc = params[0];
            latitude = loc.getLatitude();
            longitude = loc.getLongitude();

            // Create a list to contain the result address
            List<Address> addresses = null;

            //Return 1 address.
            addresses = geocoder.getFromLocation(latitude, longitude, 1);

            // If the reverse geocode returned an address
            if (addresses != null && addresses.size() > 0) {
                // Get the first address
                Address address = addresses.get(0);

                if (address.getFeatureName() != null)
                    addressText = address.getFeatureName();
                else if (address.getSubLocality() != null)
                    addressText = address.getSubLocality();
                else {
                    for (int i = 0; i <= address.getMaxAddressLineIndex(); i++) {
                        if (address.getAddressLine(i) != null)
                            addressText += address.getAddressLine(i) + ", ";
                    }

                    //remove comma from end of address
                    if (addressText.length() > 2)
                        addressText = addressText.substring(0, addressText.length() - 2);
                }
                
                Log.d("",addressText);
            } else Log.w("","Can't get address");

        } catch (Exception ex) {
            Log.e("",ex.getMessage());
        }
        return this;
    }

    @Override
    protected void onPostExecute(GPSAddress gps) {
        try {
            super.onPostExecute(this);
            if (this.onLocationFoundListener != null) {
                this.onLocationFoundListener.onLocationFound(gps);
            } else {
                Log.e("","onLocationFoundListener event is null");
            }
        } catch (Exception ex) {
            Log.e("",ex.getMessage());
        }
    }

    public static void TryToGet(final Context context, boolean showSettingAlert, final setOnLocationFoundListener listener) {

        GPSTracker gps = new GPSTracker(context, new GPSTracker.FoundLocationListener() {
            @Override
            public void onGetLastLocation(Location lastLocation) {
                try {
                    GPSAddress gpsAddress = new GPSAddress(context);
                    gpsAddress.setOnLocationFoundListener(listener);
                    gpsAddress.execute(lastLocation);
                } catch (Exception ex) {
                    Log.e("",ex.getMessage());
                }
            }

            @Override
            public void onGetCurrentLocation(Location lastLocation) {
                try {
                    GPSAddress gpsAddress = new GPSAddress(context);
                    gpsAddress.setOnLocationFoundListener(listener);
                    gpsAddress.execute(lastLocation);
                } catch (Exception ex) {
                    Log.e("",ex.getMessage());
                }
            }
        });
        gps.start(true);
    }
}
