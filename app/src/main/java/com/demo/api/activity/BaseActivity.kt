package com.demo.api.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import androidx.annotation.CallSuper
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.demo.api.ViewBindingUtil

/**
 *
 * @author like
 * @date 3/15/22 6:14 PM
 */
abstract class BaseActivity<VB : ViewBinding> : AppCompatActivity() {

    lateinit var binding: VB

    private fun viewBinding(inflater: LayoutInflater, container: ViewGroup?): VB {
        return ViewBindingUtil.viewBindingJavaClass(inflater, container, null, javaClass)
    }

    @CallSuper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = viewBinding(layoutInflater, null)
        setContentView(binding.root)
    }

}