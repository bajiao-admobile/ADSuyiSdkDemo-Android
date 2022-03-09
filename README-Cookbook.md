## ADmobile 广告聚合SDK\_Android\_菜谱内容接入文档V1.0.3

### 1、概述

尊敬的开发者您好，本文档为ADSuyi广告聚合SDK菜谱内容分发的补充文档，通过本文档旨在帮助您完成菜谱内容分发SDK的集成，并通过内部广告位获取广告分成。

注1 ：本功能接入需要权限，您可以联系ADmobile媒介商务人员进行了解和开通。

注2 ：需要使用gson2.8.0版本（以上）。

### 2、SDK的导入

1.导入相关依赖

```java
    // 菜谱内容SDK（还需要gson和recyclerview支持）需要导入ADSuyiSdk
		implementation 'cn.admobiletop.adsuyi.ad.adapter:cookbook:1.0.3.02091'
    implementation 'com.google.code.gson:gson:2.8.5'
    implementation 'com.android.support:recyclerview-v7:28.0.0'
```

2.添加混淆配置

```java
# ADSuyiSdk混淆
-dontwarn cn.admobiletop.adsuyi.**
-dontwarn org.apache.commons.**
-keep class cn.admobiletop.adsuyi.**{public *;}
-keep class com.android.**{*;}
-keep class com.ciba.**{ *; }
-keep class org.apache.**{*;}

# CookbookAdapter混淆
-keep class cn.admobiletop.cookbook.**{*;}
```

ADSuyiSDK导入参考https://github.com/ADSuyi/ADSuyiSdkDemo-Android

### 3、SDK的初始化

```java
// 初始化菜谱SDK
CookbookSDKManger.getInstance().init(this, new ADSuyiAdRecipeConfig.Builder()
        .setAppId("请设置AppId")
        .setAppSecret("请设置密钥")
        .build());
```

### 4、一键式接入菜谱内容

```java
// 设置菜谱广告位（建议不要接入视频有关的信息流广告，广点通渠道请误配置信息流自渲染广告）
CookbookSDKManger.setAdSuyiAdIdConfig(new ADSuyiAdIdConfig.Builder()
                        .setNativeAdId("信息流广告位id")
                        .setBannerAdId("横幅广告位id")
                        .setInterstitialAdId("插屏广告位id")
                        .build());
// 设置菜谱是否沉浸效果true为沉浸，会在菜谱首页顶部增加一个状态栏高度，避免状态栏遮挡布局
CookbookSDKManger.getInstance().setHomeImmersion(true);
// 方式一：一键打开菜谱内容界面
boolean openCookbookSuccess = CookbookSDKManger.openCookbookActivity();
// 方式二：获取菜谱内容Fragment自由嵌入
Fragment cookbookFragment = CookbookSDKManger.getCookbookFragment();
```







