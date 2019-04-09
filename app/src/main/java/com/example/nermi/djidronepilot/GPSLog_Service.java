package com.example.nermi.djidronepilot;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class GPSLog_Service extends Service{

    private String GPS_LOG_FILE = "gps_logs.txt";
    private OutputStreamWriter gpsLogWriter = null;

    private LocationManager locationManager;
    private LocationListener locationListener;
    private Criteria criteria;



    @SuppressLint("MissingPermission")
    @Override
    public void onCreate() {
        super.onCreate();
        try {
            gpsLogWriter = openFile(GPS_LOG_FILE, MODE_PRIVATE);
        } catch (FileNotFoundException e) {
            Toast.makeText(this, "Something went wrong when opening the file", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }

        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                Intent intent = new Intent(GPSLocationReceiver.GPS_RECEIVED);
                intent.putExtra("coordinates", location.getLatitude() + ", " + location.getLongitude());
                sendBroadcast(intent);

                try {
                    writeToFile(gpsLogWriter, location.getLatitude() + ", " + location.getLongitude() + "\n");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        };

        locationManager = (LocationManager) getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
        criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);

        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 0, locationListener);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if(locationManager != null)
            locationManager.removeUpdates(locationListener);

        if(gpsLogWriter != null) {
            try {
                closeFile(gpsLogWriter);
                Toast.makeText(this, "Succesfully Closed File", Toast.LENGTH_LONG).show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private OutputStreamWriter openFile(String filename, int MODE) throws FileNotFoundException {
        FileOutputStream fileOutputStream = openFileOutput(filename, MODE);
        return new OutputStreamWriter(fileOutputStream);
    }

    private void writeToFile(OutputStreamWriter outputStreamWriter, String sourceText) throws IOException{
        outputStreamWriter.write(sourceText);
        outputStreamWriter.flush();
    }

    private void closeFile(OutputStreamWriter outputStreamWriter) throws IOException {
        outputStreamWriter.close();
    }
}
