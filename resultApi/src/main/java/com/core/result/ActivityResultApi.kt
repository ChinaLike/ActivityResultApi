package com.core.result

import android.app.Application

/**
 * 初始化功能
 * @author like
 * @date 3/15/22 10:24 AM
 */
object ActivityResultApi {

    /**
     * 初始化
     */
    fun init(application: Application) {
        application.registerActivityLifecycleCallbacks(ActivityLifecycleCallbacks())
    }

}