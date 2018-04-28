package org.sch.permission.library.callback;

import java.io.Serializable;

import android.support.annotation.NonNull;

/**
 * Created by v_shenchanghui on 2018/4/27.
 */

public interface IPermissionCallback extends Serializable {
    void onPermissionResult(@NonNull String[] permissions, @NonNull int[] grantResults,boolean[] shouldShowRequestPermissionRationale);
}
