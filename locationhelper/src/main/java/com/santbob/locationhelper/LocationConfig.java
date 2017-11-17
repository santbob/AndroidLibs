package com.santbob.locationhelper;

import com.google.android.gms.location.LocationRequest;

/**
 * Created by santhosh on 16/11/2017.
 * Location updates Configuration
 */

public class LocationConfig {

    /**
     * The desired interval for location updates. Inexact. Updates may be more or less frequent.
     */
    private static final long UPDATE_INTERVAL_IN_MILLISECONDS = 10000;
    /**
     * The fastest rate for active location updates. Exact. Updates will never be more frequent
     * than this value.
     */
    private static final long FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS =
            UPDATE_INTERVAL_IN_MILLISECONDS / 2;

    public enum Priority {
        PRIORITY_HIGH_ACCURACY, PRIORITY_BALANCED_POWER_ACCURACY, PRIORITY_LOW_POWER, PRIORITY_NO_POWER
    }

    private long updateInterval = UPDATE_INTERVAL_IN_MILLISECONDS; //ms
    private long fastestUpdateInterval = FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS;
    private Priority requestPriority = Priority.PRIORITY_BALANCED_POWER_ACCURACY;
    private boolean shouldRequestRegularUpdates = false;

    public LocationConfig() {
    }

    public LocationConfig(long updateInterval, long fastestUpdateInterval, Priority requestPriority, boolean shouldRequestRegularUpdates) {
        this.updateInterval = updateInterval;
        this.fastestUpdateInterval = fastestUpdateInterval;
        this.requestPriority = requestPriority;
        this.shouldRequestRegularUpdates = shouldRequestRegularUpdates;
    }

    public long getUpdateInterval() {
        return updateInterval;
    }

    public void setUpdateInterval(long updateInterval) {
        this.updateInterval = updateInterval;
    }

    public long getFastestUpdateInterval() {
        return fastestUpdateInterval;
    }

    public void setFastestUpdateInterval(long fastestUpdateInterval) {
        this.fastestUpdateInterval = fastestUpdateInterval;
    }

    public int getRequestPriority() {
        switch (requestPriority) {
            case PRIORITY_HIGH_ACCURACY:
                return LocationRequest.PRIORITY_HIGH_ACCURACY;
            case PRIORITY_BALANCED_POWER_ACCURACY:
                return LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY;
            case PRIORITY_LOW_POWER:
                return LocationRequest.PRIORITY_LOW_POWER;
            case PRIORITY_NO_POWER:
                return LocationRequest.PRIORITY_NO_POWER;
            default:
                return LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY;
        }

    }

    public void setRequestPriority(Priority requestPriority) {
        this.requestPriority = requestPriority;
    }

    public boolean isShouldRequestRegularUpdates() {
        return shouldRequestRegularUpdates;
    }

    public void setShouldRequestRegularUpdates(boolean shouldRequestRegularUpdates) {
        this.shouldRequestRegularUpdates = shouldRequestRegularUpdates;
    }
}
