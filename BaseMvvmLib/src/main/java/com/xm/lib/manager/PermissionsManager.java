package com.xm.lib.manager;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

/**
 * 权限工具类
 */

public class PermissionsManager {

    private final String TAG = "PermissionsManager";

    private final int mRequestCode = 100;//权限请求码
    private boolean showSystemSetting = false;

    private PermissionsManager() {

    }

    private static PermissionsManager mPermissionsManager;
    private IPermissionsResult mPermissionsResult;

    public static PermissionsManager getInstance() {
        if (mPermissionsManager == null) {
            mPermissionsManager = new PermissionsManager();
        }
        return mPermissionsManager;
    }

    /**
     * 是否显示setting页面
     * */
    public PermissionsManager setShowSystemSetting(boolean showSystemSetting) {
        this.showSystemSetting = showSystemSetting;
        return this;
    }

    public void checkPermissions(Activity context
            , String[] permissions, @NonNull IPermissionsResult permissionsResult) {
        mPermissionsResult = permissionsResult;

        if (isApplyPermissions()) {//6.0才用动态权限
            permissionsResult.passPermissions();
            return;
        }

        //创建一个mPermissionList，逐个判断哪些权限未授予，未授予的权限存储到mPerrrmissionList中
        List<String> mPermissionList = new ArrayList<>();
        //逐个判断你要的权限是否已经通过
        for (int i = 0; i < permissions.length; i++) {
            if (ContextCompat.checkSelfPermission(context, permissions[i])
                    != PackageManager.PERMISSION_GRANTED) {
                mPermissionList.add(permissions[i]);//添加还未授予的权限
            }
        }

        Log.e(TAG, "requestPermissions:" + mPermissionList);

        //申请权限
        if (mPermissionList.size() > 0) {//有权限没有通过，需要申请
            ActivityCompat.requestPermissions(context, permissions, mRequestCode);
        } else {
            //说明权限都已经通过，可以做你想做的事情去
            permissionsResult.passPermissions();
            return;
        }
    }

    /**
     * 判断是否需要申请权限
     */
    private boolean isApplyPermissions() {
        return Build.VERSION.SDK_INT < Build.VERSION_CODES.M;
    }

    //请求权限后回调的方法
    //参数： requestCode  是我们自己定义的权限请求码
    //参数： permissions  是我们请求的权限名称数组
    //参数： grantResults 是我们在弹出页面后是否允许权限的标识数组，数组的长度对应的是权限名称数组的长度，数组的数据0表示允许权限，-1表示我们点击了禁止权限

    public void onRequestPermissionsResult(Activity context, int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        boolean hasPermissionDismiss = false;//有权限没有通过
        if (mRequestCode == requestCode) {
            for (int i = 0; i < grantResults.length; i++) {
                if (grantResults[i] == -1) {
                    hasPermissionDismiss = true;
                }
            }
            //如果有权限没有被允许
            if (hasPermissionDismiss) {
                if (showSystemSetting) {
                    showSystemPermissionsSettingDialog(context);//跳转到系统设置权限页面，或者直接关闭页面，不让他继续访问
                } else {
                    mPermissionsResult.rejectPermissions();
                }
            } else {
                //全部权限通过，可以进行下一步操作。。。
                mPermissionsResult.passPermissions();
            }
            showSystemSetting = false;
        }

    }


    /**
     * 不再提示权限时的展示对话框
     */
    AlertDialog mPermissionDialog;

    private void showSystemPermissionsSettingDialog(final Activity context) {
        final String mPackName = context.getPackageName();
        if (mPermissionDialog == null) {
            mPermissionDialog = new AlertDialog.Builder(context)
                    .setMessage("已禁用权限，请手动授予")
                    .setPositiveButton("设置", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            PermissionsManager.this.cancelPermissionDialog();

                            Uri packageURI = Uri.parse("package:" + mPackName);
                            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, packageURI);
                            context.startActivity(intent);
                            context.finish();
                        }
                    })
                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //关闭页面或者做其他操作
                            PermissionsManager.this.cancelPermissionDialog();
                            //mContext.finish();
                            mPermissionsResult.rejectPermissions();
                        }
                    })
                    .create();
        }
        mPermissionDialog.show();
    }

    //关闭对话框
    private void cancelPermissionDialog() {
        if (mPermissionDialog != null) {
            mPermissionDialog.cancel();
            mPermissionDialog = null;
        }
    }


    public interface IPermissionsResult {
        void passPermissions();

        void rejectPermissions();
    }


}
