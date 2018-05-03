package org.sch.permission.library.ui;

import org.sch.permission.library.SchPermission;

import android.annotation.TargetApi;
import android.app.Fragment;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;

/**
 * Created by shenchanghui on 2018/4/27.
 */

public class SchPermissionFragment extends Fragment {

    private int mPermissionRequestCode = 10203040;

    public int getPermissionRequestCode() {
        return mPermissionRequestCode;
    }

    public void setPermissionRequestCodePlus() {
        this.mPermissionRequestCode++;
    }

    public void setSchPermission(SchPermission mSchPermission) {
        this.mSchPermission = mSchPermission;
    }

    private SchPermission mSchPermission;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

    }

    @TargetApi(Build.VERSION_CODES.M)
    public boolean isRevoked(String permission) {
        return getActivity().getPackageManager()
                .isPermissionRevokedByPolicy(permission, getActivity().getPackageName());
    }

    /**
     * 判断是否拥有该权限
     *
     * @param permission
     *
     * @return
     */
    public boolean isGranted(String permission) {
        //判断是否已经赋予权限
        return ContextCompat.checkSelfPermission(getActivity(), permission) == PackageManager.PERMISSION_GRANTED;
    }

    @TargetApi(Build.VERSION_CODES.M)
    public void requestUnquestedPermissions(@NonNull String[] permissions, int requestCode) {
        requestPermissions(permissions, requestCode);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (mSchPermission != null) {
            mSchPermission.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

}
