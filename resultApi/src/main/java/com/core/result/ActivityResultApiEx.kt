package com.core.result

import android.content.Intent
import android.text.TextUtils
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.alibaba.android.arouter.core.LogisticsCenter
import com.alibaba.android.arouter.exception.NoRouteFoundException
import com.alibaba.android.arouter.facade.Postcard
import com.alibaba.android.arouter.facade.callback.InterceptorCallback
import com.alibaba.android.arouter.facade.callback.NavigationCallback
import com.alibaba.android.arouter.facade.enums.RouteType
import com.alibaba.android.arouter.facade.service.DegradeService
import com.alibaba.android.arouter.facade.service.InterceptorService
import com.alibaba.android.arouter.facade.service.PretreatmentService
import com.alibaba.android.arouter.launcher.ARouter

/**
 * 获取activityResultLauncher
 */
fun FragmentActivity.activityResultLauncher(): XActivityResultContract<Intent, ActivityResult>? {
    val activityKey = intent.getStringExtra(ActivityLifecycleCallbacks.KEY_ACTIVITY_RESULT_API)
    return if (TextUtils.isEmpty(activityKey)) null else ActivityLifecycleCallbacks.resultLauncherMap[activityKey]
}

///**
// * 获取fragmentResultLauncher
// */
//fun Fragment.fragmentResultLauncher(): XActivityResultContract<Intent, ActivityResult>? {
//    val fragmentKey =
//        requireActivity().intent.getStringExtra(ActivityLifecycleCallbacks.KEY_FRAGMENT_LIFECYCLE_CALLBACKS)
//    return if (TextUtils.isEmpty(fragmentKey)) null else ActivityLifecycleCallbacks.resultLauncherMap[fragmentKey]
//}

/**
 * 在Activity使用registerForActivityResult
 */
inline fun FragmentActivity.registerForActivityResult(
    intent: Intent,
    activityResultCallback: ActivityResultCallback<ActivityResult>
) {
    activityResultLauncher()?.launch(intent, activityResultCallback)
}

/**
 * 在Fragment使用registerForActivityResult
 */
inline fun Fragment.registerForActivityResult(
    intent: Intent,
    activityResultCallback: ActivityResultCallback<ActivityResult>
) {
    requireActivity().activityResultLauncher()?.launch(intent, activityResultCallback)
}

/**
 * Activity中ARouter导航
 * @param [activity] activity
 * @param [activityResultCallback] 返回数据回调
 */
fun Postcard.navigation(
    activity: FragmentActivity,
    activityResultCallback: ActivityResultCallback<ActivityResult>
): Any? {
    return navigation(activity, null, activityResultCallback)
}

/**
 * Fragment中ARouter导航
 * @param [fragment] Fragment
 * @param [activityResultCallback] 返回数据回调
 */
fun Postcard.navigation(
    fragment: Fragment,
    activityResultCallback: ActivityResultCallback<ActivityResult>
): Any? {
    return navigation(fragment.requireActivity(), null, activityResultCallback)
}


/**
 * Fragment中ARouter导航
 * @param [fragment] Fragment
 * @param [callback] 回调
 * @param [activityResultCallback] 返回数据回调
 */
fun Postcard.navigation(
    fragment: Fragment,
    callback: NavigationCallback?,
    activityResultCallback: ActivityResultCallback<ActivityResult>
): Any? {
    val _postcard = this
    val activity = fragment.requireActivity()
    val pretreatmentService = ARouter.getInstance().navigation(PretreatmentService::class.java)
    if (null != pretreatmentService && !pretreatmentService.onPretreatment(activity, this)) {
        return null
    }

    try {
        LogisticsCenter.completion(_postcard)
    } catch (ex: NoRouteFoundException) {
        debugLog(activity, path, group)
        if (null != callback) {
            callback.onLost(_postcard)
        } else {
            val degradeService = ARouter.getInstance().navigation(DegradeService::class.java)
            degradeService?.onLost(activity, _postcard)
        }

        return null
    }
    callback?.onFound(_postcard)
    val interceptorService = ARouter.getInstance().navigation(InterceptorService::class.java)
    if (!isGreenChannel && interceptorService != null) {
        interceptorService.doInterceptions(_postcard, object : InterceptorCallback {

            override fun onContinue(postcard: Postcard?) {
                _navigation(activity, _postcard, activityResultCallback,fragment)
            }

            override fun onInterrupt(exception: Throwable?) {
                callback?.onInterrupt(_postcard)
            }

        })
    } else {
        return _navigation(activity, this, activityResultCallback, fragment)
    }
    return null
}

/**
 * Activity中ARouter导航
 * @param [activity] Fragment
 * @param [callback] 回调
 * @param [activityResultCallback] 返回数据回调
 */
private fun Postcard.navigation(
    activity: FragmentActivity,
    callback: NavigationCallback?,
    activityResultCallback: ActivityResultCallback<ActivityResult>
): Any? {
    val _postcard = this
    val pretreatmentService = ARouter.getInstance().navigation(PretreatmentService::class.java)
    if (null != pretreatmentService && !pretreatmentService.onPretreatment(activity, this)) {
        return null
    }

    try {
        LogisticsCenter.completion(_postcard)
    } catch (ex: NoRouteFoundException) {
        debugLog(activity, path, group)
        if (null != callback) {
            callback.onLost(_postcard)
        } else {
            val degradeService = ARouter.getInstance().navigation(DegradeService::class.java)
            degradeService?.onLost(activity, _postcard)
        }

        return null
    }
    callback?.onFound(_postcard)
    val interceptorService = ARouter.getInstance().navigation(InterceptorService::class.java)
    if (!isGreenChannel && interceptorService != null) {
        interceptorService.doInterceptions(_postcard, object : InterceptorCallback {

            override fun onContinue(postcard: Postcard?) {
                _navigation(activity, _postcard, activityResultCallback)
            }

            override fun onInterrupt(exception: Throwable?) {
                callback?.onInterrupt(_postcard)
            }

        })
    } else {
        return _navigation(activity, this, activityResultCallback)
    }
    return null
}

/**
 * Debug模式下日志打印
 */
private fun debugLog(activity: FragmentActivity, path: String?, group: String?) {
    if (ARouter.debuggable()) {
        // Show friendly tips for user.
        activity.runOnUiThread {
            Toast.makeText(
                activity,
                "There's no route matched!Path = [${path}]Group = [${group}]",
                Toast.LENGTH_LONG
            ).show()
        }
    }
}

private fun _navigation(
    activity: FragmentActivity,
    postcard: Postcard,
    activityResultCallback: ActivityResultCallback<ActivityResult>,
    fragment: Fragment? = null
): Any? {

    return when (postcard.type) {
        RouteType.ACTIVITY -> {

            val intent = Intent(activity, postcard.destination)
            postcard.extras?.let {
                intent.putExtras(it)
            }
            if (postcard.flags != -1) {
                intent.flags = postcard.flags
            }

            postcard.action?.let {
                intent.action = postcard.action
            }
            activity.runOnUiThread {
                //适配动画
                if ((postcard.enterAnim != -1 && postcard.exitAnim != -1)) {
                    activity.overridePendingTransition(postcard.enterAnim, postcard.exitAnim)
                }
                if (fragment != null) {
                    fragment.registerForActivityResult(intent, activityResultCallback)
                } else {
                    activity.registerForActivityResult(intent, activityResultCallback)
                }
            }
            null
        }
        RouteType.PROVIDER -> {
            postcard.provider
        }
        RouteType.FRAGMENT -> {
            val fragmentMeta = postcard.destination
            try {
                val instance = fragmentMeta.getConstructor().newInstance()
                if (instance is Fragment) {
                    instance.arguments = postcard.extras
                }
                instance
            } catch (ex: Exception) {
                ex.printStackTrace()
            }
        }
        else -> {
            null
        }
    }
}