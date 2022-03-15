package com.demo.api.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.demo.api.R

class SecondActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        val text = findViewById<TextView>(R.id.content)
        text.text = intent.getStringExtra("params")
        findViewById<Button>(R.id.backBtn).setOnClickListener {
            setResult(RESULT_OK, Intent().apply {
                putExtra("params","第二个界面回传")
            })
            finish()
        }
    }
}