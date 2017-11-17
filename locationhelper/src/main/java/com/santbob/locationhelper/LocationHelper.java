package com.santbob.locationhelper;

import android.app.Activity;
import android.content.IntentSender;
import android.location.Location;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

/**
 * Created by santhosh on 15/11/2017.
 * Activities should create an instance of this class and call requestLocationUpdate when they have to fetch the new location.
 * Every single time requestLocationUpdate is called, it will trigger the GPS update and wait for its first locationChanged and once it recieves it, it will stop recieving any more updates, unless triggered again.
 */
@SuppressWarnings("MissingPermission")
public class LocationHelper {

    public interface LocationHelperListener {
        void onLocationIdentified(Location location);
    }

    private static final String TAG = LocationHelper.class.getSimpleName();
    /**
     * Constant used in the location settings dialog.
     */
    private static final int REQUEST_CHECK_SETTINGS = 0x1;

    private final FusedLocationProviderClient fusedLocationClient;
    private final SettingsClient settingsClient;
    private LocationHelperListener locationHelperListener;
    private final Activity activity;
    /**
     * Stores parameters for requests to the FusedLocationProviderApi.
     */
    private LocationRequest mLocationRequest;
    /**
     * Stores the types of location services the client is interested in using. Used for checking
     * settings to determine if the device has optimal location settings.
     */
    private LocationSettingsRequest mLocationSettingsRequest;
    /**
     * Callback for Location events.
     */
    private LocationCallback mLocationCallback;

    private LocationConfig locationConfig;

    /**
     * Time when the location was updated represented as a String.
     */
    private int updateCount;

    public LocationHelper(Activity activity) {
        this(activity, new LocationConfig());
    }

    public LocationHelper(Activity activity, LocationConfig locationConfig) {
        this.activity = activity;
        this.locationConfig = locationConfig;
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(activity);
        settingsClient = LocationServices.getSettingsClient(activity);
        // Kick off the process of building the LocationCallback, LocationRequest, and
        // LocationSettingsRequest objects.
        createLocationCallback();
        createLocationRequest();
        buildLocationSettingsRequest();
    }

    /**
     * Sets up the location request. Android has two location request settings:
     * {@code ACCESS_COARSE_LOCATION} and {@code ACCESS_FINE_LOCATION}. These settings control
     * the accuracy of the current location. This sample uses ACCESS_FINE_LOCATION, as defined in
     * the AndroidManifest.xml.
     * <p/>
     * When the ACCESS_FINE_LOCATION setting is specified, combined with a fast update
     * interval (5 seconds), the Fused Location Provider API returns location updates that are
     * accurate to within a few feet.
     * <p/>
     * These settings are appropriate for mapping applications that show real-time location
     * updates.
     */
    private void createLocationRequest() {
        mLocationRequest = new LocationRequest();

        // Sets the desired interval for active location updates. This interval is
        // inexact. You may not receive updates at all if no location sources are available, or
        // you may receive them slower than requested. You may also receive updates faster than
        // requested if other applications are requesting location at a faster interval.
        mLocationRequest.setInterval(locationConfig.getUpdateInterval());

        // Sets the fastest rate for active location updates. This interval is exact, and your
        // application will never receive updates faster than this value.
        mLocationRequest.setFastestInterval(locationConfig.getFastestUpdateInterval());

        mLocationRequest.setPriority(locationConfig.getRequestPriority());
    }

    /**
     * Creates a callback for receiving location events.
     */
    private void createLocationCallback() {
        mLocationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);
                updateCount++;
                if (locationHelperListener != null) {
                    Location identifiedLocation = locationResult.getLastLocation();
                    //if just one location update is good enough, check if the location is identified or wait for max 3 updates.
                    if (!locationConfig.isShouldRequestRegularUpdates() && (identifiedLocation != null || updateCount > 3)) {
                        stopLocationUpdates();
                    }
                    locationHelperListener.onLocationIdentified(identifiedLocation);
                } else {
                    //when there no listener, stop location updates.
                    stopLocationUpdates();
                }
            }
        };
    }

    /**
     * Uses a {@link com.google.android.gms.location.LocationSettingsRequest.Builder} to build
     * a {@link com.google.android.gms.location.LocationSettingsRequest} that is used for checking
     * if a device has the needed location settings.
     */
    private void buildLocationSettingsRequest() {
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
        builder.addLocationRequest(mLocationRequest);
        mLocationSettingsRequest = builder.build();
    }

    public void requestLocationUpdate(LocationHelperListener listener) {
        this.locationHelperListener = listener;
        // Begin by checking if the device has the necessary location settings.
        settingsClient.checkLocationSettings(mLocationSettingsRequest)
                .addOnSuccessListener(new OnSuccessListener<LocationSettingsResponse>() {
                    @Override
                    public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
                        if (BuildConfig.DEBUG) {
                            Log.d(TAG, "All location settings are satisfied.");
                        }
                        //noinspection MissingPermission
                        fusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        int statusCode = ((ApiException) e).getStatusCode();
                        switch (statusCode) {
                            case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                                if (BuildConfig.DEBUG) {
                                    Log.d(TAG, "Location settings are not satisfied. Attempting to upgrade location settings ");
                                }
                                try {
                                    // Show the dialog by calling startResolutionForResult(), and check the
                                    // result in onActivityResult().
                                    ResolvableApiException rae = (ResolvableApiException) e;
                                    rae.startResolutionForResult(activity, REQUEST_CHECK_SETTINGS);
                                } catch (IntentSender.SendIntentException sie) {
                                    if (BuildConfig.DEBUG) {
                                        Log.d(TAG, "PendingIntent unable to execute request.");
                                    }

                                }
                                break;
                            case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                                String errorMessage = "Location settings are inadequate, and cannot be " +
                                        "fixed here. Fix in Settings.";
                                if (BuildConfig.DEBUG) {
                                    Log.d(TAG, errorMessage);
                                }
                                Toast.makeText(activity, errorMessage, Toast.LENGTH_LONG).show();
                                updateCount = 0;
                        }
                    }
                });
    }

    /**
     * Removes location updates from the FusedLocationApi.
     */
    public void stopLocationUpdates() {
        // It is a good practice to remove location requests when the activity is in a paused or
        // stopped state. Doing so helps battery performance and is especially
        // recommended in applications that request frequent location updates.
        fusedLocationClient.removeLocationUpdates(mLocationCallback)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        updateCount = 0;
                    }
                });
    }
}