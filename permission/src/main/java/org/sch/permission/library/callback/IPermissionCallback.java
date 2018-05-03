package org.sch.permission.library.callback;

import java.io.Serializable;

import android.support.annotation.NonNull;

/**
 * Created by shenchanghui on 2018/4/27.
 */

public interface IPermissionCallback extends Serializable {
    /**
     * 权限申请回调
     *
     * @param permissions  申请的权限
     * @param grantResults 权限申请结果
     */
    void onPermissionResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults);
}
