# ActivityResultApiKx
[![](https://img.shields.io/badge/platform-android-brightgreen.svg)](https://developer.android.com/index.html)  [![API](https://img.shields.io/badge/API-17%2B-blue.svg?style=flat)](https://android-arsenal.com/api?level=17)  [![](https://jitpack.io/v/ChinaLike/ActivityResultApi.svg)](https://jitpack.io/#ChinaLike/ActivityResultApi)  [![Gradle-4.1.2](https://img.shields.io/badge/Gradle-4.1.2-brightgreen.svg)](https://img.shields.io/badge/Gradle-4.1.2-brightgreen.svg)  [![](https://img.shields.io/badge/language-kotlin-brightgreen.svg)](https://kotlinlang.org/)

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

当前最新版本：[![](https://jitpack.io/v/ChinaLike/ActivityResultApi.svg)](https://jitpack.io/#ChinaLike/ActivityResultApi)

```
dependencies {
       implementation 'com.github.ChinaLike:ActivityResultApi:最新版本号'
}
```

# API介绍

+ 此SDK支持不回调，当不需要回调的时候，使用我们平时的`startActivity()`也是一样的，按照自己习惯选择即可
+ 当没有Java示例的地方，说明不支持或者使用Java非常麻烦

## registerForActivityResult(intent,activityResultCallback)

### 参数说明

+ intent：类型Intent，必传
+ activityResultCallback：类型ActivityResultCallback<ActivityResult>，可选，当需要回调时传递，当不需要回调时，可不传递

### Kotlin示例

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

### Java示例

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

## <T:FragmentActivity> registerForActivityResult(intentExtra,activityResultCallback)

### 参数说明

+ intentExtra：类型(intent: Intent) -> Unit，可选
+ activityResultCallback：类型ActivityResultCallback<ActivityResult>，可选，当需要回调时传递，当不需要回调时，可不传递

### Kotlin示例

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

## ARouter中，navigation(context,navigationCallback,activityResultCallback)

### 参数说明

+ context：类型FragmentActivity或Fragment，必传
+ navigationCallback：类型NavigationCallback，界面监听，可选
+ activityResultCallback：类型ActivityResultCallback<ActivityResult>，回调，必传

### Kotlin示例

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

### Java示例

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


