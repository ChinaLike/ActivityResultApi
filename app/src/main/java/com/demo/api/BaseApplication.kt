package com.demo.api

import android.app.Application
import com.alibaba.android.arouter.launcher.ARouter
import com.core.result.ActivityResultApi

/**
 *
 * @author like
 * @date 3/15/22 10:24 AM
 */
class BaseApplication:Application() {

    override fun onCreate() {
        super.onCreate()
        ARouter.openLog()     // 打印日志
        ARouter.openDebug()
        ARouter.init(this)
        ActivityResultApi.init(this)
    }
}