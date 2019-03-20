package com.example.dell.myapplication;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements LocationListener {
    private TextView longitude;
    private TextView latitude;
    LocationManager locationManager;
    String provider;
    Location location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        longitude = findViewById(R.id.longitude);
        latitude = findViewById(R.id.latitude);

        if (ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    1);

        } else {

            if (location != null) {
                System.out.println("Provider" + provider + "has been selected");
                onLocalChanged(location);

            } else {
                longitude.setText("Location not avaiable");
                latitude.setText("Location not avaiable");
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1: {

                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {


                } else {
                    if (ContextCompat.checkSelfPermission(MainActivity.this,
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            != PackageManager.PERMISSION_GRANTED) {

                        ActivityCompat.requestPermissions(this,
                                new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                                1);
                        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                        Criteria criteria = new Criteria();
                        provider = locationManager.getBestProvider(criteria, false);

                    }
                }
                return;
            }
            case 2: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this,"hay cho phep truy cap vi tri",Toast.LENGTH_SHORT).show();
                } else {
                    if (ContextCompat.checkSelfPermission(MainActivity.this,
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            != PackageManager.PERMISSION_GRANTED) {
                        location = locationManager.getLastKnownLocation(provider);
                    }
                }
                return;


            }

        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        try {
            if (ContextCompat.checkSelfPermission(MainActivity.this,
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {
                // Permission is not granted
                // Should we show an explanation?
                if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,
                        Manifest.permission.ACCESS_FINE_LOCATION)) {
                } else {
                    // No explanation needed; request the permission
                    ActivityCompat.requestPermissions(this,
                            new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                            1);
                }
            } else {
                locationManager.requestLocationUpdates(provider, 400, 1, this);
            }
        } catch (Exception e) {
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        locationManager.removeUpdates(this);

    
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

    private void onLocalChanged(Location location) {

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onLocationChanged(Location location) {
        int lat = (int) location.getLatitude();
        int lon = (int) location.getLongitude();
        longitude.setText("longitude" + String.valueOf(lon));
        latitude.setText("latitude" + String.valueOf(lat));

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {
        Toast.makeText(this, "Enabled", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onProviderDisabled(String provider) {
        Toast.makeText(this, "Disabled", Toast.LENGTH_SHORT).show();
    }
}
