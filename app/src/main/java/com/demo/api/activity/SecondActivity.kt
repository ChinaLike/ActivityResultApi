package com.demo.api.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.core.widget.toolbar.OnToolbarListener
import com.demo.api.Key
import com.demo.api.Router
import com.demo.api.databinding.ActivitySecondBinding

@Route(path = Router.SECOND_ACTIVITY)
class SecondActivity : BaseActivity<ActivitySecondBinding>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.content.text = "从【${intent.getStringExtra(Key.SOURCE)}】跳转而来，如果为【null】，有可能是无参跳转"

        binding.toolbar.onToolbarListener = object :OnToolbarListener{
            /**
             * 返回键监听事件
             * @return true 点击事件生效，允许返回 ， false 点击事件无效，不允许返回
             */
            override fun onCallback(): Boolean {
                setResult(Activity.RESULT_OK, Intent().apply {
                    putExtra(Key.RESULT_DATE,"成功返回")
                })
                finish()
                return false
            }
        }
    }
}