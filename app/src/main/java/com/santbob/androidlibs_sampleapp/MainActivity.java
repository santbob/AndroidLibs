package com.santbob.androidlibs_sampleapp;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.santbob.locationhelper.LocationConfig;
import com.santbob.locationhelper.LocationHelper;
import com.santbob.permissionhelper.PermissionHelper;

public class MainActivity extends AppCompatActivity implements ActivityCompat.OnRequestPermissionsResultCallback, PermissionHelper.PermissionHelperListener {

    private TextView locationInfoText;
    private LocationHelper locationHelper;
    private PermissionHelper permissionHelper;

    private Button requestLocationBtn, stopLocationUpdatesBtn;

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        locationHelper = new LocationHelper(this, new LocationConfig(1000, 500, LocationConfig.Priority.PRIORITY_HIGH_ACCURACY, true));
        permissionHelper = new PermissionHelper(this, this);

        requestLocationBtn = findViewById(R.id.request_current_location_button);
        requestLocationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                locationInfoText.setText(R.string.location_placeholder);
                permissionHelper.requestPermission(Manifest.permission.ACCESS_FINE_LOCATION, LOCATION_PERMISSION_REQUEST_CODE);
            }
        });

        stopLocationUpdatesBtn = findViewById(R.id.stop_location_updates_button);
        stopLocationUpdatesBtn.setVisibility(View.GONE);
        stopLocationUpdatesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                locationHelper.stopLocationUpdates();
                locationInfoText.setText(R.string.location_placeholder);
                requestLocationBtn.setVisibility(View.VISIBLE);
                stopLocationUpdatesBtn.setVisibility(View.GONE);
            }
        });
        locationInfoText = findViewById(R.id.location_info);
    }

    @Override
    public void OnPermissionGranted(String permission, int requestCode) {
        requestLocationBtn.setVisibility(View.GONE);
        stopLocationUpdatesBtn.setVisibility(View.VISIBLE);
        locationHelper.requestLocationUpdate(new LocationHelper.LocationHelperListener() {
            @Override
            public void onLocationIdentified(Location location) {
                String builder = "Latitude : " + location.getLatitude() +
                        "\nLongitude : " + location.getLongitude();
                locationInfoText.setText(builder);
            }
        });
    }

    @Override
    public void OnPermissionDenied(String permission, int requestCode) {
        AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
        alertDialog.setTitle(R.string.permission_needed);
        alertDialog.setMessage(getString(R.string.location_permission_denied));
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }

    @Override
    public void OnShowRationale(String permission, int requestCode) {
        new AlertDialog.Builder(this)
                .setTitle(R.string.permission_needed)
                .setMessage(R.string.location_permission_rationale)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        permissionHelper.requestPermissionWithoutRationalCheck(Manifest.permission.ACCESS_FINE_LOCATION, LOCATION_PERMISSION_REQUEST_CODE);
                    }
                })
                .setNegativeButton(android.R.string.cancel, null).show();
    }

    @Override
    public void OnNeverAskAgain(String permission, int requestCode) {
        AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
        alertDialog.setTitle(R.string.permission_needed);
        alertDialog.setMessage(getString(R.string.location_permission_denied_never_ask));
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        permissionHelper.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
