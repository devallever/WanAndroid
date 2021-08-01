package com.everdeng.android.app.wanandroid.ui.main.model

import android.Manifest
import androidx.fragment.app.FragmentActivity
import com.xm.lib.base.inters.IBaseView
import com.xm.lib.base.model.BaseViewModelKt
import com.xm.lib.datastroe.DataStore
import com.xm.lib.permission.PermissionCompat
import com.xm.lib.util.log

class MainViewModel : BaseViewModelKt<IBaseView>() {
    override fun onCreated() {
        DataStore.putString("KEY", "TEST MMKE")
        log("text mmkv value = " + DataStore.getString("KEY"))

        PermissionCompat.with(mCxt as FragmentActivity)
            .permission(Manifest.permission.CALL_PHONE, Manifest.permission.CAMERA, Manifest.permission.READ_CONTACTS)
            .onExplain("部分功能需要用到这些权限")
            .onSetting("需要手动授权")
            .request { allGranted, grantedList, deniedList ->
                if (allGranted) {
                    log("全部授权")
                    toast("全部授权")
                }
            }

//        PermissionX.init(mCxt as AppCompatActivity)
//            .permissions(Manifest.permission.CALL_PHONE, Manifest.permission.CAMERA, Manifest.permission.READ_CONTACTS)
//            .onExplainRequestReason { scope, deniedList ->
//                scope.showRequestReasonDialog(deniedList, "部分功能需要用到这些权限", "授权", "取消")
//            }
//            .onForwardToSettings { scope, deniedList ->
//                scope.showForwardToSettingsDialog(deniedList, "需要手动授权", "👌好的", "取消")
//            }
//            .request { allGranted, grantedList, deniedList ->
//                if (allGranted) {
//                    toast("全部授权")
//                }
//            }
    }
}