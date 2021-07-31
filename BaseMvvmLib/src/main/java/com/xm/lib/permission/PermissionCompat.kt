package com.xm.lib.permission

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.permissionx.guolindev.PermissionMediator
import com.permissionx.guolindev.PermissionX
import com.permissionx.guolindev.request.PermissionBuilder
import com.xm.lib.manager.IntentManager

object PermissionCompat {
    private lateinit var permissionMediator: PermissionMediator
    private lateinit var permissionBuilder: PermissionBuilder

    fun with(activity: FragmentActivity): PermissionCompat {
        permissionMediator = PermissionX.init(activity)
        return this
    }

    fun with(fragment: Fragment): PermissionCompat {
        permissionMediator = PermissionX.init(fragment)
        return this
    }

    fun permission(vararg permission: String): PermissionCompat {
        permissionBuilder = permissionMediator.permissions(*permission)
        return this
    }

    fun onExplain(
        msg: String,
        positiveText: String = "授权",
        cancelText: String = "取消"
    ): PermissionCompat {
        permissionBuilder = permissionBuilder.onExplainRequestReason { scope, deniedList ->
            scope.showRequestReasonDialog(deniedList, msg, positiveText, cancelText)
        }
        return this
    }

    fun onSetting(
        msg: String,
        positiveText: String = "👌好的",
        cancelText: String = "取消"
    ): PermissionCompat {
        permissionBuilder = permissionBuilder.onForwardToSettings { scope, deniedList ->
            scope.showForwardToSettingsDialog(deniedList, msg, positiveText, cancelText)
        }
        return this
    }

    fun request(blocK: (allGranted: Boolean, grantedList: List<String>, deniedList: List<String>) -> Unit) {
        permissionBuilder.request { allGranted, grantedList, deniedList ->
            blocK(allGranted, grantedList, deniedList)
        }
    }

}