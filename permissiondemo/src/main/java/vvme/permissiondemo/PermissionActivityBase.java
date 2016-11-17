package vvme.permissiondemo;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

import java.util.List;

import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * Created by VV on 2016/11/17.
 */

public class PermissionActivityBase extends AppCompatActivity implements EasyPermissions.PermissionCallbacks {
    protected static final int RC_CAMERA_PERM = 123;
    protected static final int RC_LOCATION_CONTACTS_PERM = 124;
    protected static final int RC_SETTINGS_SCREEN = 125;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // EasyPermissions handles the request result.
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            new AppSettingsDialog.Builder(this, getPermissionStr(requestCode))
                    .setTitle(getString(R.string.title_settings_dialog))
                    .setPositiveButton(getString(R.string.setting))
                    .setNegativeButton(getString(R.string.cancel), null /* click listener */)
                    .setRequestCode(RC_SETTINGS_SCREEN)
                    .build()
                    .show();
        }
    }

    private String getPermissionStr(int requestCode){
        String result = "";
        switch (requestCode){
            case RC_CAMERA_PERM:
                result ="获取相机权限时被拒绝.";
                break;
        }
        return result;
    }
}
