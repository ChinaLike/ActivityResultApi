package com.demo.api.fragment

import android.os.Bundle
import android.view.View
import com.core.result.registerForActivityResult
import com.demo.api.Key
import com.demo.api.R
import com.demo.api.activity.SecondActivity
import com.demo.api.databinding.FragmentNormalBinding

/**
 *
 * @author like
 * @date 3/15/22 2:27 PM
 */
class NormalFragment : BaseFragment<FragmentNormalBinding>() {

    private val source = "Fragment"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.normalBtn.setOnClickListener {
            binding.content.text = resources.getString(R.string.result_data, "")
            registerForActivityResult<SecondActivity>({
                it.putExtra(Key.SOURCE, source)
            }) {
                binding.content.text = resources.getString(
                    R.string.result_data,
                    it.data?.getStringExtra(Key.RESULT_DATE) ?: ""
                )
            }
        }
    }

}