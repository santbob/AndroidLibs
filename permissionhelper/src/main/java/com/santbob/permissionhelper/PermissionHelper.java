package com.santbob.permissionhelper;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

/**
 * Created by santhosh on 15/11/2017.
 * A light weight Single permission requester, which calls back approriate listener based on user actions on the permission window.
 */

public class PermissionHelper implements ActivityCompat.OnRequestPermissionsResultCallback{

    public interface PermissionHelperListener {
        void OnPermissionGranted(String permission, int requestCode);

        void OnPermissionDenied(String permission, int requestCode);

        void OnShowRationale(String permission, int requestCode);

        void OnNeverAskAgain(String permission, int requestCode);
    }

    private Activity activity;
    private PermissionHelperListener permissionHelperListener;


    public PermissionHelper(Activity activity, PermissionHelperListener listener) {
        this.activity = activity;
        this.permissionHelperListener = listener;
    }


    public void requestPermission(final String permission, int requestCode) {
        this.requestPermission(permission, requestCode, true);
    }

    public void requestPermissionWithoutRationalCheck(final String permission, int requestCode) {
        this.requestPermission(permission, requestCode, false);
    }

    private void requestPermission(final String permission, int requestCode, boolean checkForRationale) {
        if (ContextCompat.checkSelfPermission(activity, permission) != PackageManager.PERMISSION_GRANTED) {
            // Should we show an explanation?
            if (checkForRationale && ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)) {
                if (permissionHelperListener != null) {
                    permissionHelperListener.OnShowRationale(permission, requestCode);
                }
            } else {
                ActivityCompat.requestPermissions(activity, new String[]{permission}, requestCode);
            }
        } else {
            if (permissionHelperListener != null) {
                permissionHelperListener.OnPermissionGranted(permission, requestCode);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        String currentPermission = (permissions.length > 0) ? permissions[0] : null;
        // If request is cancelled, the result arrays are empty.
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            if (permissionHelperListener != null) {
                permissionHelperListener.OnPermissionGranted(currentPermission, requestCode);
            }
        } else {
            boolean neverShowAgain = currentPermission == null || !ActivityCompat.shouldShowRequestPermissionRationale(activity, currentPermission);
            if (permissionHelperListener != null) {
                if (neverShowAgain) {
                    permissionHelperListener.OnNeverAskAgain(currentPermission, requestCode);
                } else {
                    permissionHelperListener.OnPermissionDenied(currentPermission, requestCode);
                }
            }
        }
    }

}