package com.demo.api.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.alibaba.android.arouter.launcher.ARouter
import com.core.result.navigation
import com.core.result.registerForActivityResult
import com.demo.api.Router
import com.demo.api.activity.SecondActivity
import com.demo.api.databinding.FragmentTestBinding

/**
 *
 * @author like
 * @date 3/15/22 2:27 PM
 */
class TestFragment : Fragment() {

    lateinit var binding: FragmentTestBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTestBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //普通跳转
        binding.normalBtn.setOnClickListener {
            val intent = Intent(requireActivity(), SecondActivity::class.java)
            intent.putExtra("params", "Fragment中传递过来的")
            registerForActivityResult(intent) {
                binding.content.text = "返回的数据：${it.data?.getStringExtra("params")}"
            }
        }
        //ARouter跳转
        binding.aRouterBtn.setOnClickListener {
            ARouter.getInstance().build(Router.A_ROUTER_ACTIVITY)
                .withString("params", "Fragment中传递过来的")
                .navigation(this) {
                    binding.content.text = "返回的数据：${it.data?.getStringExtra("params")}"
                }
        }
    }

}