package com.rs.keepcart.login;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.rs.keepcart.R;
import com.rs.keepcart.comingSoon.VendorProccessActivity;
import com.rs.keepcart.dashboard.HomeActivity;
import com.rs.keepcart.databinding.ActivitySplashBinding;
import com.rs.keepcart.utills.MySharedData;

import java.util.List;
import java.util.Locale;

import static com.facebook.FacebookSdk.getApplicationContext;

public class Splash extends AppCompatActivity implements LocationListener{
    public String TAG = Splash.class.getSimpleName();
    ImageView splash;
    // private SharedPreference sharedPreference;
    Activity context = this;
    String email_, password_;
    private Location location;
    private LocationManager locationManager;
    private ActivitySplashBinding viewBinding;
    private String latitude;
    private String longitude;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        splash = findViewById(R.id.splashimage);
        //sharedPreference = new SharedPreference();
        getGeoLocation();
       /* email_ =sharedPreference.getValue(context);
         password_ = sharedPreference.getValue(context);*/
        String stats = MySharedData.getGeneralSaveSession("status0");
        String activ_stats = MySharedData.getGeneralSaveSession("Act_status_1");
        String newsPerSelected = MySharedData.getGeneralSaveSession("newsPerSelected");
        Thread th = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                    if (MySharedData.getGeneralSaveSession("userId").equals("") && MySharedData.getGeneralSaveSession("pass").equals("")) {
                        Intent in = new Intent(getApplicationContext(), LoginActivity.class);
                        startActivity(in);
                        Log.e(TAG, "new user--0 ....................");
                        finish();
                    }

                    else if (!MySharedData.getGeneralSaveSession("userId").equals("")) {
                        if (stats.equals("200")) {
                             if (activ_stats.equals("0")) {
                                Intent inV0 = new Intent(getApplicationContext(), VendorProccessActivity.class);
                                //Intent inV = new Intent(getApplicationContext(), SelectNewsPaperActivity.class);
                                startActivity(inV0);
                                // finish();
                            }else if( activ_stats.equals("1"))
                             {
                                 Intent inV0 = new Intent(getApplicationContext(), LoginActivity.class);
                                 //Intent inV = new Intent(getApplicationContext(), SelectNewsPaperActivity.class);
                                 startActivity(inV0);
                             }
                        }
                        else if(!MySharedData.getGeneralSaveSession("userId").equals("") && !MySharedData.getGeneralSaveSession("pass").equals("")) {

                            Intent iin = new Intent(getApplicationContext(), HomeActivity.class);
                            startActivity(iin);
                            finish();
                            Log.e(TAG, "already signup user--0 ......................");
                        }
                        else{
                            Intent iin = new Intent(getApplicationContext(), HomeActivity.class);
                            startActivity(iin);
                            finish();
                            Log.e(TAG, "already signup user--0 ......................");
                        }
                    }
                    else{
                        Intent iin = new Intent(getApplicationContext(), HomeActivity.class);
                        startActivity(iin);
                        finish();
                        Log.e(TAG, "already signup user--0 ......................");
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        });
        th.start();


    }
    public void getGeoLocation()
    {
        if (ContextCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(context, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION}, 101);

        }
        try {
            locationManager = (LocationManager)context.getSystemService(Context.LOCATION_SERVICE);
            assert locationManager != null;
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 5000, 5, (LocationListener) this);
        }
        catch(SecurityException e) {
            e.printStackTrace();
        }

    }
    @Override
    public void onLocationChanged(Location location) {
        /*("Latitude: " + location.getLatitude() + "\n Longitude: " + location.getLongitude());*/

        //viewBinding.location.setText( location.getLatitude() + "\n" + location.getLongitude());
        MySharedData.setGeneralSaveSession("locate",location.getLatitude()+ "\n"+ location.getLongitude());

        latitude = String.valueOf(location.getLatitude());
        longitude = String.valueOf(location.getLongitude());


        Log.e(TAG, "Latitude...................." + location.getLatitude());
        Log.e(TAG, "Longitude...................." + location.getLongitude());
        try {
            Geocoder geocoder = new Geocoder(context, Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
//            viewBinding.location.setText(viewBinding.location.getText() + "\n"+addresses.get(0).getAddressLine(0)+", "+
//                    addresses.get(0).getAddressLine(1)+", "+addresses.get(0).getAddressLine(2));

            Log.e(TAG, "address...................." + addresses.get(0).getAddressLine(0));
            Log.e(TAG, "address2...................." + addresses.get(0).getAddressLine(1));
            Log.e(TAG, "address3...................." + addresses.get(0).getAddressLine(2));
            Log.e(TAG, "address4...................." + addresses.get(0).getAddressLine(0)+", "+
                    addresses.get(0).getAddressLine(1)+", "+addresses.get(0).getAddressLine(2));


        }catch(Exception e)
        {

        }

    }

    @Override
    public void onProviderDisabled(String provider) {
        Toast.makeText(Splash.this, "Please Enable GPS and Internet", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

}
