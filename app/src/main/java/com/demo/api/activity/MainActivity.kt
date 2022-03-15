package com.demo.api.activity

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.alibaba.android.arouter.launcher.ARouter
import com.core.result.navigation
import com.core.result.registerForActivityResult
import com.demo.api.R
import com.demo.api.Router
import com.demo.api.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //普通跳转
        binding.btn.setOnClickListener {
            val intent = Intent(this, SecondActivity::class.java)
            intent.putExtra("params", "第一个界面")
            registerForActivityResult(intent) {
                val text = findViewById<TextView>(R.id.content)
                text.text = "普通界面返回：${it.data?.getStringExtra("params")}"
            }
        }
        //ARouter跳转
        binding.aRouterBtn.setOnClickListener {
            ARouter.getInstance().build(Router.A_ROUTER_ACTIVITY)
                .withString("params","第一个界面")
                .navigation(this) {
                    val text = findViewById<TextView>(R.id.content)
                    text.text = "ARouter界面返回：${it.data?.getStringExtra("params")}"
                }
        }

        //跳转Fragment
        binding.fragmentBtn.setOnClickListener {
            val intent = Intent(this,FragmentActivity::class.java)
            intent.putExtra("params","第一个界面")
            registerForActivityResult(intent) {
                val text = findViewById<TextView>(R.id.content)
                text.text = "Fragment界面返回：${it.data?.getStringExtra("params")}"
            }
        }
    }
}