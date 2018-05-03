package org.sch.permission.library;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.sch.permission.library.callback.IPermissionCallback;
import org.sch.permission.library.ui.SchPermissionFragment;

import android.app.Activity;
import android.app.FragmentManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.util.Log;

/**
 * Created by v_shenchanghui on 2018/4/27.
 */

public class SchPermission {
    private static final String TAG = SchPermission.class.getSimpleName();

    private SchPermissionFragment mSchPermissionFragment;
    private HashMap<Integer, IPermissionCallback> mCallbackMap = new HashMap<>();

    public SchPermission(@NonNull Activity activity) {
        mSchPermissionFragment = getSchPermissionsFragment(activity);
    }

    /**
     * 是否需要申请权限
     *
     * @return
     */
    private boolean isNeedRequestPermission() {
        // 版本判断。当手机系统大于 23 时，才有必要去判断权限是否获取
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
    }

    /**
     * 申请权限
     */
    public void request(String[] permissionArr, IPermissionCallback callback) {
        if (!isNeedRequestPermission()) {
            Log.i(TAG, "unnecessary to request permission!");
            return;
        }
        List<String> unrequestedPermissions = new ArrayList<>();
        for (String permission : permissionArr) {
            if (mSchPermissionFragment.isGranted(permission)) {
                continue;
            }
            if (mSchPermissionFragment.isRevoked(permission)) {
                continue;
            }
            unrequestedPermissions.add(permission);
        }
        if (unrequestedPermissions.isEmpty()) {
            Log.i(TAG, "requested permissions are granted!");
            return;
        }
        mSchPermissionFragment.setPermissionRequestCodePlus();
        mCallbackMap.put(mSchPermissionFragment.getPermissionRequestCode(), callback);
        mSchPermissionFragment
                .requestUnquestedPermissions(permissionArr, mSchPermissionFragment.getPermissionRequestCode());

    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults,
                                           boolean[] shouldShowRequestPermissionRationale) {
        IPermissionCallback callback = mCallbackMap.get(requestCode);
        if (callback != null) {
            callback.onPermissionResult(permissions, grantResults, shouldShowRequestPermissionRationale);
            mCallbackMap.remove(requestCode);
        }
    }

    private SchPermissionFragment getSchPermissionsFragment(Activity activity) {
        SchPermissionFragment schPermissionFragment = findSchPermissionsFragment(activity);
        boolean isNewInstance = schPermissionFragment == null;
        if (isNewInstance) {
            schPermissionFragment = new SchPermissionFragment();
            schPermissionFragment.setSchPermission(this);
            FragmentManager fragmentManager = activity.getFragmentManager();
            fragmentManager.beginTransaction()
                    .add(schPermissionFragment, TAG)
                    .commitAllowingStateLoss();
            fragmentManager.executePendingTransactions();
        }
        return schPermissionFragment;
    }

    private SchPermissionFragment findSchPermissionsFragment(Activity activity) {
        return (SchPermissionFragment) activity.getFragmentManager().findFragmentByTag(TAG);
    }

}
