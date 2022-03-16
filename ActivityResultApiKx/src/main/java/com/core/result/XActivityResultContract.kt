package com.core.result

import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultCaller
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContract

/**
 *
 * @author like
 * @date 3/14/22 4:03 PM
 */
class XActivityResultContract<I, O>(
    activityResultCaller: ActivityResultCaller,
    activityResultContract: ActivityResultContract<I, O>
) {

    private var activityResultCallback: ActivityResultCallback<O>? = null


    private val launcher: ActivityResultLauncher<I>? =
        activityResultCaller.registerForActivityResult(activityResultContract) {
            activityResultCallback?.onActivityResult(it)
        }


    /**
     * 启动
     */
    @JvmOverloads
    fun launch(input: I, activityResultCallback: ActivityResultCallback<O>?) {
        this.activityResultCallback = activityResultCallback
        launcher?.launch(input)
    }

    /**
     * 注销
     */
    fun unregister() {
        launcher?.unregister()
    }

}