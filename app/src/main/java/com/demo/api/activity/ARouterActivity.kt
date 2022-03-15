package com.demo.api.activity

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.alibaba.android.arouter.facade.annotation.Route
import com.demo.api.R
import com.demo.api.Router

@Route(path = Router.A_ROUTER_ACTIVITY,name = "Arouter")
class ARouterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_a_router)
        findViewById<TextView>(R.id.content).text = intent.getStringExtra("params")
        findViewById<Button>(R.id.back).setOnClickListener {
            setResult(Activity.RESULT_OK, Intent().apply {
                putExtra("params","ARouter返回")
            })
            finish()
        }
    }
}