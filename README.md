# ActivityResultApiKx
[![](https://img.shields.io/badge/platform-android-brightgreen.svg)](https://developer.android.com/index.html)  [![API](https://img.shields.io/badge/API-17%2B-blue.svg?style=flat)](https://android-arsenal.com/api?level=17)  [![](https://jitpack.io/v/ChinaLike/ActivityResultApi.svg)](https://jitpack.io/#ChinaLike/ActivityResultApi)  [![Gradle-4.1.2](https://img.shields.io/badge/Gradle-4.1.2-brightgreen.svg)](https://img.shields.io/badge/Gradle-4.1.2-brightgreen.svg)  [![](https://img.shields.io/badge/language-kotlin-brightgreen.svg)](https://kotlinlang.org/)

官方已明确标记`startActivityForResult()`为`@deprecated`，并给出`@link`指定使用`registerForActivityResult`，即Activity Result API，下面我们来看一张图：

![QQ20220318-100841@2x.png](https://p3-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/e279b6f56059485e85ee9963897f0279~tplv-k3u1fbpfcp-watermark.image?)
在这个大环境下，想必很多人也会把新项目或者升级老项目使用Activity Result API，如果还未使用或者想使用的人可以先了解一下Activity Result API的基础使用，但是使用的人知道存在以下几个问题：

+ java.lang.IllegalStateException: LifecycleOwner XXXX is attempting to register while current state is RESUMED. LifecycleOwners must call register before they are STARTED.

+ ARouter不支持Activity Result API（目前不支持，不代表以后不支持）

基于以上问题，本文给出了解决方案

# API特色

+ 优雅的适配Activity Result API，真正的支持在非Activity和Fragment中调用
+ 支持不用在onResume()之前初始化ActivityResultLauncher
+ 支持[ARouter](https://github.com/alibaba/ARouter)
+ 支持Kotlin和Java用法
+ Kotlin扩展，使用简单

# 如何使用

> step 1.在根目录的build.gradle添加：

```
allprojects {
   repositories {
      ...
      maven { url "https://jitpack.io" }
   }
}
```

> step 2.然后在 build.gradle(Module:XXX) 的 dependencies 添加：

当前最新版本：[![](https://p3-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/2df70399060a48678061b6480e5b9937~tplv-k3u1fbpfcp-zoom-1.image)](https://jitpack.io/#ChinaLike/ActivityResultApi)

```
dependencies {
        implementation 'com.github.ChinaLike:ActivityResultApi:最新版本号'
        //根据自己版本选择即可，最低不能低于activity-ktx:1.2.3、fragment-ktx:1.3.4
        implementation("androidx.activity:activity-ktx:1.2.3")
        implementation("androidx.fragment:fragment-ktx:1.3.4")
        //ARouter，根据自己需要引入，版本按照自己需要引入
        //implementation 'com.alibaba:arouter-api:1.5.2'
        //kapt 'com.alibaba:arouter-compiler:1.5.2'
}
```

# API介绍

+ 此SDK支持不回调，当不需要回调的时候，使用我们平时的`startActivity()`也是一样的，按照自己习惯选择即可
+ 当没有Java示例的地方，说明不支持或者使用Java非常麻烦

### registerForActivityResult(intent,activityResultCallback)

#### 参数说明

+ intent：类型Intent，必传
+ activityResultCallback：类型ActivityResultCallback<ActivityResult>，可选，当需要回调时传递，当不需要回调时，可不传递

#### Kotlin示例

+ 带返回

```
val intent = Intent(this, SecondActivity::class.java)
intent.putExtra(Key.SOURCE, source)
registerForActivityResult(intent) {
   //回调
}
```

+ 不带返回

```
val intent = Intent(this, SecondActivity::class.java)
intent.putExtra(Key.SOURCE, source)
registerForActivityResult(intent)
//startActivity(intent) 也一样
```

#### Java示例

+ 带返回

```
Intent intent = new Intent(this, SecondActivity.class);
intent.putExtra(Key.SOURCE, source);
ActivityResultApiExKt.registerForActivityResult(this, intent, result -> 
      //回调
);
```

+ 不带返回

```
Intent intent = new Intent(this, SecondActivity.class);
intent.putExtra(Key.SOURCE, source);
ActivityResultApiExKt.registerForActivityResult(this, intent);
```

### <T:FragmentActivity> registerForActivityResult(intentExtra,activityResultCallback)

#### 参数说明

+ intentExtra：类型(intent: Intent) -> Unit，可选
+ activityResultCallback：类型ActivityResultCallback<ActivityResult>，可选，当需要回调时传递，当不需要回调时，可不传递

#### Kotlin示例

+ 不传参，有回调

```
registerForActivityResult<SecondActivity> {
   //回调
}
```

+ 传参，有回调

```
registerForActivityResult<SecondActivity>({
   it.putExtra(Key.SOURCE, source)
}) {
   //回调
}
```

+ 传参，没有回调

```
registerForActivityResult<SecondActivity>({
   it.putExtra(Key.SOURCE, source)
})
```

+ 不传参，没有回调

```
registerForActivityResult<SecondActivity>(）
```

### ARouter中，navigation(context,navigationCallback,activityResultCallback)

#### 参数说明

+ context：类型FragmentActivity或Fragment，必传
+ navigationCallback：类型NavigationCallback，界面监听，可选
+ activityResultCallback：类型ActivityResultCallback<ActivityResult>，回调，必传

#### Kotlin示例

+ 带界面监听和回调

```
ARouter.getInstance()
    .build(Router.SECOND_ACTIVITY)
    .withString(Key.SOURCE, source)
    .navigation(this, object : NavigationCallback {

        override fun onFound(postcard: Postcard?) {
            
        }

        override fun onLost(postcard: Postcard?) {

        }

        override fun onArrival(postcard: Postcard?) {

        }

        override fun onInterrupt(postcard: Postcard?) {

        }

    }) {
   //回调
    }
```

+ 不带界面监听但有回调

```
ARouter.getInstance()
    .build(Router.SECOND_ACTIVITY)
    .withString(Key.SOURCE, source)
    .navigation(this) {
   //回调
    }
```

#### Java示例

+ 带界面监听和回调

```
Postcard postcard = ARouter.getInstance()
    .build(Router.SECOND_ACTIVITY)
    .withString(Key.SOURCE, source);
ActivityResultApiExKt.navigation(postcard, this, new NavigationCallback() {
    @Override
    public void onFound(Postcard postcard) {
        
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

    @Override
    public void onActivityResult(ActivityResult result) {
        //回调
    }
});
```

+ 不带界面监听但有回调

```
Postcard postcard = ARouter.getInstance()
        .build(Router.SECOND_ACTIVITY)
        .withString(Key.SOURCE, source);
ActivityResultApiExKt.navigation(postcard, this, new ActivityResultCallback<ActivityResult>() {

    @Override
    public void onActivityResult(ActivityResult result) {
        //回调
    }
});
```
