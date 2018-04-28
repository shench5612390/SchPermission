package org.sch.permission;

import org.sch.permission.library.SchPermission;
import org.sch.permission.library.callback.IPermissionCallback;

import android.Manifest;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private SchPermission mSchPermission;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mSchPermission = new SchPermission(this);
        findViewById(R.id.btn_request_permission).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSchPermission
                        .request(new String[] {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                new
                                        IPermissionCallback() {
                                            @Override
                                            public void onPermissionResult(@NonNull String[] permissions,
                                                                           @NonNull int[] grantResults,
                                                                           boolean[]
                                                                                   shouldShowRequestPermissionRationale) {
                                                StringBuffer sb = new StringBuffer();
                                                for (String str : permissions) {
                                                    sb.append(" ");
                                                    sb.append(str);
                                                }
                                                StringBuffer sb2 = new StringBuffer();
                                                for (int i : grantResults) {
                                                    sb2.append(" ");
                                                    sb2.append(i);
                                                }
                                                StringBuffer sb3 = new StringBuffer();
                                                for (boolean b : shouldShowRequestPermissionRationale) {
                                                    sb3.append(" ");
                                                    sb3.append(b);
                                                }
                                                Log.i(TAG, "permissions=" + sb.toString() + ",grantResults=" + sb2
                                                        .toString() + ",shouldShowRequestPermissionRationale=" + sb3
                                                        .toString());

                                            }
                                        });
            }
        });
    }
}
