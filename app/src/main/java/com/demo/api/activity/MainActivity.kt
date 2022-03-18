package com.demo.api.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import com.alibaba.android.arouter.facade.Postcard
import com.alibaba.android.arouter.facade.callback.NavigationCallback
import com.alibaba.android.arouter.launcher.ARouter
import com.core.result.navigation
import com.core.result.registerForActivityResult
import com.core.widget.toolbar.OnToolbarListener
import com.demo.api.Key
import com.demo.api.R
import com.demo.api.Router
import com.demo.api.databinding.ActivityMainBinding

class MainActivity : BaseActivity<ActivityMainBinding>() {

    private val source = "Activity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.toolbar.onToolbarListener = object : OnToolbarListener {
            override fun onMenuClick(imageView: AppCompatImageView?, textView: AppCompatTextView?) {
                registerForActivityResult<JavaActivity>()
            }
        }

        //普通Intent跳转
        binding.normalStyle1Callback.setOnClickListener {
            //方式1（有回调）
            binding.normalContent.text = resources.getString(R.string.result_data, "")
            val intent = Intent(this, SecondActivity::class.java)
            intent.putExtra(Key.SOURCE, source)
            registerForActivityResult(intent) {
                binding.normalContent.text = resources.getString(
                    R.string.result_data,
                    it.data?.getStringExtra(Key.RESULT_DATE)
                )
            }
        }
        binding.normalStyle1NoCallback.setOnClickListener {
            //方式1（没有回调），当然此用法和startActivity（）一样
            binding.normalContent.text = resources.getString(R.string.result_data, "")
            val intent = Intent(this, SecondActivity::class.java)
            intent.putExtra(Key.SOURCE, source)
            registerForActivityResult(intent)
//            startActivity(intent)
        }
        binding.normalStyle2.setOnClickListener {
            //方式2(不带参数)
            binding.normalContent.text = resources.getString(R.string.result_data, "")
            registerForActivityResult<SecondActivity> {
                binding.normalContent.text = resources.getString(
                    R.string.result_data,
                    it.data?.getStringExtra(Key.RESULT_DATE)
                )
            }
        }
        binding.normalStyle3.setOnClickListener {
            //方式2（带参数）
            binding.normalContent.text = resources.getString(R.string.result_data, "")
            registerForActivityResult<SecondActivity>({
                it.putExtra(Key.SOURCE, source)
            }) {
                binding.normalContent.text = resources.getString(
                    R.string.result_data,
                    it.data?.getStringExtra(Key.RESULT_DATE)
                )
            }
        }
        binding.normalStyle4.setOnClickListener {
            //方式2（有参数，没有回调）
            binding.normalContent.text = resources.getString(R.string.result_data, "")
            registerForActivityResult<SecondActivity>({
                it.putExtra(Key.SOURCE, source)
            })
        }
        binding.normalStyle5.setOnClickListener {
            //方式2（没有参数，没有回调）
            binding.normalContent.text = resources.getString(R.string.result_data, "")
            registerForActivityResult<SecondActivity>()
        }

        //ARouter跳转
        binding.aRouterStyle1.setOnClickListener {
            binding.aRouterContent.text = resources.getString(R.string.result_data, "")
            //带监听和回调
            ARouter.getInstance()
                .build(Router.SECOND_ACTIVITY)
                .withString(Key.SOURCE, source)
                .navigation(this, object : NavigationCallback {

                    override fun onFound(postcard: Postcard?) {
                        Toast.makeText(this@MainActivity, "成功打开界面", Toast.LENGTH_SHORT).show()
                    }

                    override fun onLost(postcard: Postcard?) {

                    }

                    override fun onArrival(postcard: Postcard?) {

                    }

                    override fun onInterrupt(postcard: Postcard?) {

                    }

                }) {
                    binding.aRouterContent.text = resources.getString(
                        R.string.result_data,
                        it.data?.getStringExtra(Key.RESULT_DATE)
                    )
                }
        }
        binding.aRouterStyle2.setOnClickListener {
            binding.aRouterContent.text = resources.getString(R.string.result_data, "")
            //不带监听，有回调
            ARouter.getInstance()
                .build(Router.SECOND_ACTIVITY)
                .withString(Key.SOURCE, source)
                .navigation(this) {
                    binding.aRouterContent.text = resources.getString(
                        R.string.result_data,
                        it.data?.getStringExtra(Key.RESULT_DATE)
                    )
                }
        }
    }

}