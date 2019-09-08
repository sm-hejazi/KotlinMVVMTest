package com.ppp_smh.initlibrary.helper.gps;


import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;

class GISUtility {
    public static void addCircle(GoogleMap googleMap, double lat, double lang, double radius) {
        radius *= 2;
        if (lat == 0 || lang == 0 || radius == 0) return;

        // Instantiates a new CircleOptions object and defines the center and radius
        CircleOptions circleOptions = new CircleOptions()
                .center(new LatLng(lat, lang))
                .radius(radius); // In meters

        // Get back the mutable Circle
        googleMap.addCircle(circleOptions);
    }
}
