package com.ecoparque.activites;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.ecoparque.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


public class MapaDominio extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_mapa_dominio);
        Intent intent = getIntent();
        String latitude = intent.getStringExtra("lat");
        String longitude = intent.getStringExtra("lon");
        GoogleMap mMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map))
                .getMap();

        mMap.setMyLocationEnabled(true);

        LatLng usjLatLong = new LatLng(Double.parseDouble(latitude), Double.parseDouble(longitude));
        mMap.addMarker(new MarkerOptions()
                .position(usjLatLong)
                .title("USJ")
                .snippet("La Universidad San Jorge"));

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(usjLatLong, 13.0f));

        if (savedInstanceState == null) {
        }
    }

}
