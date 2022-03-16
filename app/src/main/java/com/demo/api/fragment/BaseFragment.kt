package com.demo.api.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.demo.api.ViewBindingUtil

/**
 *
 * @author like
 * @date 3/15/22 6:18 PM
 */
abstract class BaseFragment<VB : ViewBinding> : Fragment() {
    private var _binding: VB? = null

    val binding: VB get() = _binding!!

    private fun viewBinding(inflater: LayoutInflater, container: ViewGroup?): VB {
        return ViewBindingUtil.viewBindingJavaClass(inflater, container, null, javaClass)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = viewBinding(inflater, container)
        return _binding!!.root
    }

}