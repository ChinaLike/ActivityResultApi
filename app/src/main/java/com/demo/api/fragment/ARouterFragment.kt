package com.demo.api.fragment

import android.os.Bundle
import android.view.View
import com.alibaba.android.arouter.launcher.ARouter
import com.core.result.navigation
import com.demo.api.Key
import com.demo.api.R
import com.demo.api.Router
import com.demo.api.databinding.FragmentARouterBinding

/**
 *
 * @author like
 * @date 3/15/22 2:27 PM
 */
class ARouterFragment : BaseFragment<FragmentARouterBinding>() {

    private val source = "Fragment"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.aRouterBtn.setOnClickListener {
            binding.content.text = resources.getString(R.string.result_data, "")
            ARouter.getInstance().build(Router.SECOND_ACTIVITY)
                .withString(Key.SOURCE, source)
                .navigation(this) {
                    binding.content.text = resources.getString(
                        R.string.result_data,
                        it.data?.getStringExtra(Key.RESULT_DATE) ?: ""
                    )
                }
        }
    }
}