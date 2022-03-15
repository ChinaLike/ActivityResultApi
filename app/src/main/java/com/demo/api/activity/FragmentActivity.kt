package com.demo.api.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.demo.api.R

class FragmentActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fragment)
    }
}