package com.proyecto.mtg.practicas_uao;

import android.app.Activity;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;

public class MyLocationListener implements LocationListener {

    PracticasActivity practicasActivity;

    public PracticasActivity getPracticasActivity() {
        return practicasActivity;
    }

    public void setPracticasActivity(PracticasActivity practicasActivity) {
        this.practicasActivity = practicasActivity;
    }

    @Override
    public void onLocationChanged(Location location) {
        location.getLatitude();
        location.getLongitude();
        this.practicasActivity.setLocation(location);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
