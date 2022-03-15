package com.core.result

import android.content.Context
import android.text.TextUtils
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

/**
 * Fragment生命周期监听
 * @author like
 * @date 3/14/22 5:50 PM
 */
class FragmentLifecycleCallbacks : FragmentManager.FragmentLifecycleCallbacks() {

    override fun onFragmentAttached(fm: FragmentManager, f: Fragment, context: Context) {
        super.onFragmentAttached(fm, f, context)
        val resultLauncher =
            XActivityResultContract(f, ActivityResultContracts.StartActivityForResult())
        val fragmentKey = f.javaClass.simpleName + System.currentTimeMillis()
        f.requireActivity().intent.putExtra(
            ActivityLifecycleCallbacks.KEY_FRAGMENT_LIFECYCLE_CALLBACKS,
            fragmentKey
        )
        ActivityLifecycleCallbacks.resultLauncherMap[fragmentKey] = resultLauncher

        Log.d("测试","fragmentKey=${fragmentKey},Fragment=${f},resultLauncher=${resultLauncher}")
    }

    override fun onFragmentDetached(fm: FragmentManager, f: Fragment) {
        super.onFragmentDetached(fm, f)
        val fragmentKey =
            f.requireActivity().intent.getStringExtra(ActivityLifecycleCallbacks.KEY_FRAGMENT_LIFECYCLE_CALLBACKS)
        if (!TextUtils.isEmpty(fragmentKey)) {
            ActivityLifecycleCallbacks.resultLauncherMap.remove(fragmentKey)
        }
    }

}