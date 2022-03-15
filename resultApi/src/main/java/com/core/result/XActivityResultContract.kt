package com.core.result

import android.util.Log
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
            Log.d("测试","resultLauncher=${this}")
            activityResultCallback?.onActivityResult(it)
        }


    /**
     *
     */
    @JvmOverloads
    fun launch( input: I, activityResultCallback: ActivityResultCallback<O>?) {
        this.activityResultCallback = activityResultCallback
        launcher?.launch(input)
    }

}