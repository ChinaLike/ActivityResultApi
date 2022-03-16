package com.demo.api.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.callback.NavigationCallback;
import com.alibaba.android.arouter.launcher.ARouter;
import com.core.result.ActivityResultApiExKt;
import com.demo.api.Key;
import com.demo.api.R;
import com.demo.api.Router;
import com.demo.api.databinding.ActivityJavaBinding;

public class JavaActivity extends BaseActivity<ActivityJavaBinding> {

    private String source = "JavaActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //普通Intent跳转
        binding.normalStyle1Callback.setOnClickListener(v -> {
            //方式1（有回调）
            binding.normalContent.setText(getResources().getString(R.string.result_data, ""));
            Intent intent = new Intent(this, SecondActivity.class);
            intent.putExtra(Key.SOURCE, source);
            ActivityResultApiExKt.registerForActivityResult(this, intent, new ActivityResultCallback<ActivityResult>() {

                @Override
                public void onActivityResult(ActivityResult result) {
                    binding.normalContent.setText(getResources().getString(R.string.result_data, result.getData().getStringExtra(Key.RESULT_DATE)));
                }
            });
        });

        binding.normalStyle1NoCallback.setOnClickListener(v -> {
            //方式1（没有回调），当然此用法和startActivity（）一样
            binding.normalContent.setText(getResources().getString(R.string.result_data, ""));
            Intent intent = new Intent(this, SecondActivity.class);
            intent.putExtra(Key.SOURCE, source);
            ActivityResultApiExKt.registerForActivityResult(this, intent);
        });

        //ARouter跳转
        binding.aRouterStyle1.setOnClickListener(v -> {
            binding.aRouterContent.setText(getResources().getString(R.string.result_data, ""));
            //带监听和回调
            Postcard postcard = ARouter.getInstance()
                    .build(Router.SECOND_ACTIVITY)
                    .withString(Key.SOURCE, source);
            ActivityResultApiExKt.navigation(postcard, this, new NavigationCallback() {
                @Override
                public void onFound(Postcard postcard) {
                    Toast.makeText(JavaActivity.this, "成功打开界面", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onLost(Postcard postcard) {

                }

                @Override
                public void onArrival(Postcard postcard) {

                }

                @Override
                public void onInterrupt(Postcard postcard) {

                }
            }, new ActivityResultCallback<ActivityResult>() {

                /**
                 * Called when result is available
                 *
                 * @param result
                 */
                @Override
                public void onActivityResult(ActivityResult result) {
                    binding.aRouterContent.setText(getResources().getString(R.string.result_data, result.getData().getStringExtra(Key.RESULT_DATE)));
                }
            });
        });
        binding.aRouterStyle2.setOnClickListener(v -> {
            binding.aRouterContent.setText(getResources().getString(R.string.result_data, ""));
            //不带监听，有回调
            Postcard postcard = ARouter.getInstance()
                    .build(Router.SECOND_ACTIVITY)
                    .withString(Key.SOURCE, source);
            ActivityResultApiExKt.navigation(postcard, this, new ActivityResultCallback<ActivityResult>() {

                /**
                 * Called when result is available
                 *
                 * @param result
                 */
                @Override
                public void onActivityResult(ActivityResult result) {
                    binding.aRouterContent.setText(getResources().getString(R.string.result_data, result.getData().getStringExtra(Key.RESULT_DATE)));
                }
            });
        });
    }
}