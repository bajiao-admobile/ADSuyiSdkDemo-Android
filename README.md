# ADSuyiSdk Android Sdk——接入文档 V3.1.1.11261

 目录 

[TOC]

<div STYLE="page-break-after:always;"></div>
## 1. 概述
### 1.1 概述

尊敬的开发者朋友，欢迎您使用ADSuyi广告聚合SDK。通过本文档，您可以快速完成多平台广告SDK的集成。

**注意：本SDK仅支持中国大陆地区**；如需发布到Google Play，请勿引入本SDK及相关依赖文件。



### 1.2 ADSuyi广告聚合SDK 组成结构

ADSuyi广告聚合SDK主要由**ADSuyi核心SDK（简称ADSuyiSdk）**和一个或多个**三方平台适配器SDK（简称AdapterSdk）**组成，开发者可以自由的在后台配置中选择需要接入的三方广告平台，然后导入所对应的AdapterSdk，其中**艾狄墨搏平台的AdapterSdk是必须导入的**。



### 1.3 三方广告平台名称概述

| Name      | 平台名称 | 平台别称 |
| --------- | -------- | -------- |
| admobile  | 艾狄墨搏 | 艾狄墨搏 |
| gdt       | 广点通   | 优量汇   |
| toutiao   | 头条     | 穿山甲   |
| baidu     | 百度     | 百青藤   |
| inmobi    | Inmobi   | Inmobi   |
| mintegral | 汇量     | Mobvsita |
| oneway    | 万维     | 万维     |
| appic     | appic    | AppicAd  |
| Ifly      | 讯飞     | 讯飞     |
| mgadsdk   | 芒果     | 芒果TV   |
| ksad      | 快手     | 快手     |
| mimo   | 米盟     | 米盟   |



## 2. 支持的广告类型

<table>
  <tr>
    <th style="width:150px">类型</th>
    <th>简介</th>
    <th>适用场景</th>
  </tr>
  <tr>
    <td><a href="#ad_splash">开屏广告</a></td>
    <td>开屏广告以APP启动作为曝光时机的模板广告，需要将开屏广告视图添加到承载的广告容器中，提供5s可感知广告展示</td>
    <td>APP启动界面常会使用开屏广告</td>
  </tr>
  <tr>
    <td><a href="#ad_banner">Banner广告</a></td>
    <td>Banner广告是横向贯穿整个可视页面的模板广告，需要将Banner广告视图添加到承载的广告容器中</td>
    <td>任意界面的固定位置，不建议放在RecyclerView、List这种滚动布局中当item</td>
  </tr>
  <tr>
    <td><a href="#ad_native">信息流广告</a></td>
    <td>信息流广告集合原生自渲染广告和模板广告两种，可以通过后台配置和SDK相关方法判断进行不同的渲染，以满足不同的样式需求</td>
    <td>信息流列表，轮播控件，固定位置都是较为适合</td>
  </tr>
   <tr>
    <td><a href="#ad_reward_vod">激励视频广告</a></td>
    <td>将短视频融入到APP场景当中，用户观看短视频广告后可以给予一些应用内奖励</td>
    <td>常出现在游戏的复活、任务等位置，或者网服类APP的一些增值服务场景</td>
  </tr>
  </tr>
   <tr>
    <td><a href="#ad_full_screen_vod">全屏视频广告</a></td>
    <td>类似激励视频，与激励视频不同的是，全屏视频广告在观看一定时长后即可跳过广告，无需全部观看完成，有视频跳过回调，但是没有激励回调</td>
    <td>常出现在游戏的复活、任务等位置，或者网服类APP的一些增值服务场景</td>
  </tr>
   <tr>
    <td><a href="#ad_interstitial">插屏广告</a></td>
    <td>插屏广告是移动广告的一种常见形式，在应用流程中弹出，当应用展示插屏广告时，用户可以选择点击广告，访问其目标网址，也可以将其关闭并返回应用</td>
    <td>在应用执行流程的自然停顿点，适合投放这类广告</td>
  </tr>
<tr>
    <td><a href="#ad_draw_vod">Draw视频广告</a></td>
    <td>类似小视频一样的视频广告</td>
    <td>类似小视频列表的场景</td>
  </tr>
<tr>
    <td><a href="#inner_notice">浮窗广告</a></td>
    <td>类似通知栏样式展示的广告，只在应用中弹出，几乎不影响用户操作，用户可以上滑左右滑动移除广告</td>
    <td>任意场景</td>
  </tr>
</table>

<div STYLE="page-break-after:always;"></div>
## 3. Demo及SDK下载链接

> [ADSuyiSdkDemo-Android下载地址](https://codeload.github.com/ADSuyi/ADSuyiSdkDemo-Android/zip/master)
>
> [SDK版本更新日志](http://doc.admobile.top/ssp/4-%E6%9B%B4%E6%96%B0%E6%97%A5%E5%BF%97/1-androidchangelog.html)



## 4. SDK版本说明

### 4.1 ADSuyiSdk版本号说明

版本号格式为3.0.0.xxxxn，其中xxxx代表日期，最后一位n为版本扩展号；



### 4.2 AdapterSdk版本号说明

版本号格式为 y.y.xxxxn，y.y代表三方SDK版本号（可能两位、也可能三位、四位...），其中xxxx代表日期，最后一位n为版本扩展号；



### 4.3 AdapterSdk和ADSuyiSdk版本对应说明

AdapterSdk会指定支持的ADSuyiSdk版本，**如果导入的AdapterSdk和ADSuyiSdk版本不对应会抛出异常提醒开发者使用相对应的版本**；



### 4.4 ADMobGenSdk升级ADSuyiSdk指引

1. 升级ADSuyiSdk前请先移除ADMobGenSdk的SDK依赖和相关配置；
2. ADMobGenSdk移除之后再根据ADSuyiSdk文档接入ADSuyiSdk；
3. 无对接过ADMobGenSdk可忽略。



## 5. SDK接入流程

### 5.1 添加SDK到工程中

接入环境：**Android Studio**。



#### 5.1.1 添加仓库地址

首先需要在项目的build.gradle文件中引入如下配置：

```java
allprojects {
    repositories {
        ...
        google()
        jcenter()
        mavenCentral()
        // 添加以下仓库地址
        maven { url "http://101.37.191.20:9091/repository/maven-releases/" }
    }
}
```



#### 5.1.2 添加ADSuyiSdk和需要的AdapterSdk

将广告所需要的依赖集成进去，AdapterSdk可根据接入平台情况进行选择接入。

```java
dependencies {
    // support支持库，如果是AndroidX请使用对应的支持库
    implementation 'com.android.support:appcompat-v7:28.0.0'
    implementation 'com.android.support:support-v4:28.0.0'
    implementation 'com.android.support:design:28.0.0'
  
     // ADSuyiSdk核心库是必须导入的
    implementation 'cn.admobiletop.adsuyi.ad:core:3.1.1.11261'
    // common库是必须导入的，请保持和Demo中版本一致
    implementation 'com.admobile:common:1.2.2'

    // OAID库是必须导入的，请保持和Demo中版本一致（如果当前Suyi是3.0.9及以上版本，
    // 必须保证oaid版本为oaid_sdk_1.0.23，oaid_sdk_1.0.23为msa_mdid_1.0.13的升级版，请删除原有的msa_mdid）
    implementation(name: 'oaid_sdk_1.0.23', ext: 'aar')

    // 艾狄墨搏AdapterSdk，必须的`
    implementation 'cn.admobiletop.adsuyi.ad.adapter:admobile:4.8.2.11261'

    // 广点通AdapterSdk，可选的
    implementation 'cn.admobiletop.adsuyi.ad.adapter:gdt:4.294.1164.12021'

    // 头条AdapterSdk，可选的
    implementation 'cn.admobiletop.adsuyi.ad.adapter:toutiao:3.3.0.3.11251'

    // 百度AdapterSdk，可选的
    implementation 'cn.admobiletop.adsuyi.ad.adapter:baidu:5.95.11251'

    // 汇量AdapterSdk，可选的
    implementation 'cn.admobiletop.adsuyi.ad.adapter:mintegral:10.9.02.11111'

    // InmobiAdapterSdk，可选的
    implementation 'cn.admobiletop.adsuyi.ad.adapter:inmobi:7.5.1.11111'
    implementation 'com.squareup.picasso:picasso:2.5.2'

    // OneWayAdapterSdk，可选的
    implementation 'cn.admobiletop.adsuyi.ad.adapter:oneway:2.4.6.11251'

    // AppicAdapterSdk(信息流无曝光回调，全屏视频无播放完成回调)，可选的
    implementation 'cn.admobiletop.adsuyi.ad.adapter:appic:4.2.0.4.08241'
    // Appic还需要以下两个三方库支持
    implementation 'com.android.volley:volley:1.1.0'
    implementation 'pl.droidsonroids.gif:android-gif-drawable:1.2.6'

    // 讯飞AdapterSdk，可选的
    implementation 'cn.admobiletop.adsuyi.ad.adapter:ifly:4.5.4.10271'

    // 快手AdapterSdk，可选的
    implementation 'cn.admobiletop.adsuyi.ad.adapter:ksad:3.3.5.11131'

    // 芒果TV AdapterSdk，可选的(芒果SDK 当前与Inmobi 存在冲突，两者无法同时接入)
    implementation 'cn.admobiletop.adsuyi.ad.adapter:mgtv:3.2.1.11111'
    // 芒果TV还需要以下三方库支持
    implementation 'com.android.volley:volley:1.1.0'
    implementation 'com.facebook.fresco:fresco:1.5.0'
    implementation 'com.facebook.fresco:animated-gif:1.5.0'
    implementation 'com.facebook.fresco:animated-webp:1.5.0'
    implementation 'com.facebook.fresco:webpsupport:1.5.0'
    implementation 'com.facebook.fresco:imagepipeline-okhttp3:0.12.0'
    implementation 'com.google.code.gson:gson:2.6.2'

    // 米盟AdapterSdk，可选的（还需要gson和glide支持）
    implementation 'cn.admobiletop.adsuyi.ad.adapter:mimo:5.0.6.11261'
    implementation 'com.google.code.gson:gson:2.8.5'
    implementation 'com.github.bumptech.glide:glide:4.9.0'
    annotationProcessor 'com.github.bumptech.glide:compiler:4.9.0'
      
    // 小说内容SDK（还需要gson和recyclerview支持）
    implementation 'cn.admobiletop.adsuyi.ad.adapter:novel-alpha:1.0.8.11231'
    implementation 'com.google.code.gson:gson:2.8.5'
    implementation 'com.android.support:recyclerview-v7:28.0.0'

}
```



#### 5.1.3 注意事项 

* 支持主流架构，x86架构暂不支持

  ```java
  ndk {
  	// 设置支持的SO库架构，暂不支持x86
  	abiFilters 'armeabi-v7a' // 'armeabi', 'arm64-v8a'
  }
  ```

* **AdapterSdk默认已经集成了三方的广告SDK**，如果因为项目中也使用了相同的三方广告SDK而发生冲突，可通过以下方法尝试避免或解决；

1. 移除己方使用的三方广告SDK和相关配置；

2. 使用**AdapterSdk**的**without**集成方式，该方式没有集成三方广告SDK和配置，开发者可自行集成三方广告SDK，但是需要注意，<font color=#ff0000>我们的AdapterSdk是基于三方广告SDK某个版本开发的，如果自行集成三方广告SDK，需要承担三方广告SDK版本不一致可能引起的兼容性和其他不可预知问题；</font>

   ```java
   // 广点通AdapterSdk的without集成示例，其中x.x.x.x为AdapterSdk版本号
   implementation 'cn.admobiletop.adsuyi.ad.adapter:gdt-without:x.x.x.x'
   ```

* 如果接入汇量，需要加入第三方依赖库https://dl.bintray.com/mintegral-official/Andorid_ad_SDK_for_china_support
* **广点通适配器4.270.1140版本及以上已经导入了腾讯的tbs，请移除原有的tbs避免编译失败；**

### 5.2 OAID支持

**Android10之后IMEI等数据无法获取，这对广告投放将产生一定影响，所以移动安全联盟(MSA)提出OAID来代替IMEI参与广告投放决策，OAID的支持会在一定程度上影响广告收益；**

<font color=#ff0000>OAID是必须集成项，没有集成将会抛出异常提醒开发者</font>，OAID集成并不繁琐，SDK中已经进行了OAID的封装，只需以下几步即可完成OAID的支持；

1. 导入安全联盟的OAID支持库 **oaid_sdk_1.0.23.aar**，可在Demo的libs目录下找到，**强烈建议使用和Demo中一样版本的OAID库（包括项目中已存在的依赖的oaid版本）；**

2. 将Demo中assets文件夹下的**supplierconfig.json**文件复制到自己的assets目录下并按照**supplierconfig.json**文件中的说明进行OAID的 **AppId** 配置，**supplierconfig.json**文件名不可修改；

3. 添加以下混淆配置；

   ```java
   -keep class com.bun.miitmdid.core.** {*;}
   -keep class XI.CA.XI.**{*;}
   -keep class XI.K0.XI.**{*;}
   -keep class XI.XI.K0.**{*;}
   -keep class XI.vs.K0.**{*;}
   -keep class XI.xo.XI.XI.**{*;}
   -keep class com.asus.msa.SupplementaryDID.**{*;}
   -keep class com.asus.msa.sdid.**{*;}
   -keep class com.bun.lib.**{*;}
   -keep class com.bun.miitmdid.**{*;}
   -keep class com.huawei.hms.ads.identifier.**{*;}
   -keep class com.samsung.android.deviceidservice.**{*;}
   -keep class org.json.**{*;}
   -keep public class com.netease.nis.sdkwrapper.Utils {public <methods>;}
   ```
4. 修改AndroidManifest.xml，**OAID SDK minSdkVersion为21，如果应用的minSdkVersion小于21，则添加：**
    ```java
    <uses-sdk tools:overrideLibrary="com.bun.miitmdid"/>
    ```

**PS：需要更多帮助可参考目录下《移动智能终端补充设备标识体系统一调用SDK开发者说明文档》；**



### 5.3 权限申请

  使用SDK时可能需要以下权限，为了保证使用广告的正确，请在6.0及以上的手机中使用SDK前及时申请。

  ```java
    <!-- 广告必须的权限 -->
    <uses-permission android:name="android.permission.INTERNET" />
    <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
    <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" />
    <uses-permission android:name="android.permission.REQUEST_INSTALL_PACKAGES" />

    <!-- 广点通广告必须的权限 -->
    <uses-permission android:name="android.permission.ACCESS_WIFI_STATE" />
    <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />

    <!-- 影响广告填充，强烈建议的权限 -->
    <uses-permission android:name="android.permission.READ_PHONE_STATE" />

    <!-- 为了提高广告收益，建议设置的权限 -->
    <uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" />
    <uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION" />

    <!-- 如果有视频相关的广告播放请务必添加-->
    <uses-permission android:name="android.permission.WAKE_LOCK" />
  ```



### 5.4 兼容配置

#### 5.4.1 FileProvider配置

1. 适配Anroid7.0以及以上，请在AndroidManifest中添加如下代码：

* 如果支持库是support

  ```java
  <provider
  	  android:name="android.support.v4.content.FileProvider"
      android:authorities="${applicationId}.fileprovider"
      android:exported="false"
      android:grantUriPermissions="true">
      <meta-data
      		android:name="android.support.FILE_PROVIDER_PATHS"
          android:resource="@xml/adsuyi_file_paths" />
  </provider>
  ```

* 如果支持库为androidx

  ```java
  <provider
  	  android:name="androidx.core.content.FileProvider" 
      android:authorities="${applicationId}.fileprovider"
      android:exported="false"
      android:grantUriPermissions="true">
      <meta-data
      		android:name="android.support.FILE_PROVIDER_PATHS"
          android:resource="@xml/adsuyi_file_paths" />
  </provider>
  ```

  

2. 在res/xml目录下(如果xml目录不存在需要手动创建)，新建xml文件adsuyi_file_paths，在该文件中加入如下配置，如果存在相同android:authorities的provider，请将paths标签中的路劲配置到自己的xml文件中：

  ```java
  <?xml version="1.0" encoding="utf-8"?>  
  <paths xmlns:android="http://schemas.android.com/apk/res/android">  
      <external-path name="external_path" path="." />
      <external-files-path name="external_files_path" path="." />  
  </paths>
  ```

 <font color=#ff0000>PS  :  为了适配下载和安装相关功能，在工程中引用的包 com.android.support:support-v4:x.x.x请使用26.0.0及以上版本。</font>



#### 5.4.2 网络配置

需要在 AndroidManifest.xml 添加依赖声明**uses-library android:name="org.apache.http.legacy" android:required="false"**， 且 application标签中添加 **android:usesCleartextTraffic="true"**，适配网络http请求，否则 SDK可能无法正常工作，接入代码示例如下：

```java
<application
    android:name=".MyApplication"
        ... ...
    android:usesCleartextTraffic="true">

    <uses-library
        android:name="org.apache.http.legacy"
        android:required="false" />
    ... ...
</application>
```



#### 5.4.3 混淆配置

如果打包时开启了混淆配置，请按需添加以下混淆内容，并保证广告资源文件不被混淆

```java
-ignorewarnings
# v4、v7（如果是Support支持库需添加）
-keep class android.support.v4.**{public *;}
-keep class android.support.v7.**{public *;}

# AndroidX (如果是AndroidX支持库需添加)
-keep class com.google.android.material.** {*;}
-keep class androidx.** {*;}
-keep class * extends androidx.**

# 资源文件混淆配置
-keep class **.R$* { *; }
-keep public class **.R$*{
   public static final int *;
}
-keepclassmembers class **.R$* {
    public static <fields>;
}

# ADSuyiSdk混淆
-dontwarn cn.admobiletop.adsuyi.**
-dontwarn org.apache.commons.**
-keep class cn.admobiletop.adsuyi.**{public *;}
-keep class com.android.**{*;}
-keep class com.ciba.**{ *; }
-keep class org.apache.**{*;}

# okhttp
-dontwarn okhttp3.**
-keep class okhttp3.**{*;}

# OAID混淆
-keep class com.bun.miitmdid.core.** {*;}
-keep class XI.CA.XI.**{*;}
-keep class XI.K0.XI.**{*;}
-keep class XI.XI.K0.**{*;}
-keep class XI.vs.K0.**{*;}
-keep class XI.xo.XI.XI.**{*;}
-keep class com.asus.msa.SupplementaryDID.**{*;}
-keep class com.asus.msa.sdid.**{*;}
-keep class com.bun.lib.**{*;}
-keep class com.bun.miitmdid.**{*;}
-keep class com.huawei.hms.ads.identifier.**{*;}
-keep class com.samsung.android.deviceidservice.**{*;}
-keep class org.json.**{*;}
-keep public class com.netease.nis.sdkwrapper.Utils {public <methods>;}

# admobile广告平台混淆
-keep class admsdk.library.**{*;}

# 广点通广告平台混淆
-keep class com.qq.e.** {public protected *;}
-keep class MTT.ThirdAppInfoNew {*;}
-keep class com.tencent.** {*;}

# 如果使用了tbs版本的sdk需要进行以下配置
-keep class com.tencent.smtt.** { *; }
-dontwarn dalvik.**
-dontwarn com.tencent.smtt.**

# 百度广告SDK混淆
-keepclassmembers class * extends android.app.Activity { public void *(android.view.View);}
-keepclassmembers enum * {
 public static **[] values();
 public static ** valueOf(java.lang.String);
}
-keep class com.baidu.mobads.** { *; }
-keep class com.baidu.mobad.** { *; }

# 头条广告平台混淆
-keep class com.bytedance.sdk.openadsdk.** { *; }
-keep public interface com.bytedance.sdk.openadsdk.downloadnew.** {*;}
-keep class com.pgl.sys.ces.* {*;}

# imobi广告平台混淆
-dontwarn com.inmobi.**
-dontwarn com.squareup.picasso.**
-dontwarn com.squareup.okhttp.**
-dontwarn com.bun.**
-dontwarn com.iab.**
-keepattributes SourceFile,LineNumberTable
-keep class com.inmobi.** { *; }
-keep class com.squareup.picasso.** {*;}
-keep class com.integralads.avid.library.** {*;}
-keep class com.iab.** {*;}

# mintegral广告平台混淆
-dontwarn com.mintegral.**
-keepattributes Signature
-keepattributes *Annotation*
-keep class com.mintegral.** {*; }
-keep interface com.mintegral.** {*; }
-keep class com.alphab.** {*; }
-keep interface com.alphab.** {*; }
-keep class **.R$* { public static final int mintegral*; }

# 快手广告平台混淆
-keep class org.chromium.** {*;}
-keep class org.chromium.** { *; }
-keep class aegon.chrome.** { *; }
-keep class com.kwai.**{ *; }
-keep class com.kwad.**{ *; }
-dontwarn com.kwai.**
-dontwarn com.kwad.**
-dontwarn com.ksad.**
-dontwarn aegon.chrome.**

# AppicAd广告平台混淆
-keep class * extends com.ap.android.trunk.sdk.core.base.ad.Ad
-keep class * extends com.ap.android.trunk.sdk.core.base.ad.AdSDK
-keep class * implements com.ap.android.trunk.sdk.core.base.lifecycle.IApplicationLifecycle

# 讯飞广告平台混淆
-dontwarn com.iflytek.**
-keep class com.iflytek.** {* ;}
-keep class android.support.v4.**{public * ;}

# 芒果广告平台混淆
-keep class * implements java.io.Serializable {*;}
-keep class com.hunantv.media.** { *;}
-keep class com.mgmi.** { *;}
-keep class com.mgadplus.** { *;}
-dontwarn com.hmt.analytics.**
-dontwarn org.apaches.commons.codec.**
-keep class com.hmt.analytics.**{*; }
-keep class org.apaches.commons.codec.**{*; }
-dontwarn com.facebook.**
-keep enum com.facebook.**
-keep public interface com.facebook.**
-keep class com.facebook.**
-keep class com.facebook.** { *; }
-keep,allowobfuscation @interface com.facebook.common.internal.DoNotStrip -keep @com.facebook.common.internal.DoNotStrip class *
-keepclassmembers class * {
@com.facebook.common.internal.DoNotStrip *;
}
-keep public class com.mi.ad.sdk.**{*;}
-keep public class com.doman.core.**{*;}
-keep public class com.core.cell.** {*;}
-keep class android.**{*;}
-keep @interface system.** {*;}
-keepclassmembers class system.**{ public *;}
-dontwarn android.**
-dontwarn com.android.**
-dontwarn system.**
-keep @interface com.core.cell.helper.Keep {*;}
-keep @interface com.android.a.a.**{*;}
-keep class io.reactivex.**{*;}
-keep class com.github.megatronking.stringfog.**{*;}
-keep @interface com.github.megatronking.stringfog.**{*;}

# 快手广告平台混淆
-keep class org.chromium.** { *; }
-keep class aegon.chrome.** { *; }
-keep class com.kwai.**{ *; }
-keep class com.kwad.**{ *; }
-dontwarn com.kwai.**
-dontwarn com.kwad.**
-dontwarn com.ksad.**
-dontwarn aegon.chrome.**

# 米盟混淆
-keep class com.miui.zeus.mimo.sdk.* { *; }
-keep class com.miui.analytics.** { *; }
-keep class com.xiaomi.analytics.* { public protected *; }
-keep class * extends android.os.IInterface{*; }
# gson
-keepattributes Signature
-keepattributes *Annotation*
-dontwarn sun.misc.**
-keep class com.google.gson.examples.android.model.** { <fields>; }
-keep class * implements com.google.gson.TypeAdapterFactory
-keep class * implements com.google.gson.JsonSerializer
-keep class * implements com.google.gson.JsonDeserializer
-keepclassmembers,allowobfuscation class * { @com.google.gson.annotations.SerializedName <fields>; }
# glide
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep class * extends com.bumptech.glide.module.AppGlideModule { <init>(...);}
-keep public enum com.bumptech.glide.load.ImageHeaderParser$** {**[] $VALUES;public *;}
-keep class com.bumptech.glide.load.data.ParcelFileDescriptorRewinder$InternalRewinder {*** rewind();}

# NovelAdapter混淆
-keep class android.**{*;}
-keep class com.ecook.** {* ;}
-keep class com.parting_soul.http.** {* ;}
-keep class com.ttx.reader.support.** {* ;}
```



## 6. 示例代码

具体的广告SDK接口和接口说明请参考 

>[ADSuyiSdk JavaDoc文档](http://doc.admobile.top/ADSuyiSdkAndroidJavaDoc/index.html)



### 6.1 SDK初始化

  在Application中进行SDK的初始化(详情请参考Demo)

  ```java
// 初始化ADSuyi广告SDK
ADSuyiSdk.getInstance().init(this, new ADSuyiInitConfig.Builder()
         // 设置APPID，必须的
         .appId(ADSuyiDemoConstant.APP_ID)
         // 是否开启Debug，开启会有详细的日志信息打印，如果用上ADSuyiToastUtil工具还会弹出toast提示。
         // 注意上线后请置为false
         .debug(BuildConfig.DEBUG)
         // 是否同意隐私政策
         .agreePrivacyStrategy(true)
         // 是否同意使用oaid
         .isCanUseOaid(true)
         // 是否过滤第三方平台的问题广告（例如: 已知某个广告平台在某些机型的Banner广告可能存在问题，如果开启过滤，则在该机型将不再去获取该平台的Banner广告）
         .filterThirdQuestion(true)
         .build());
  ```

  <font color=#ff0000>PS ：AppId通过后台配置生成，初始化必须在主线程中进行，SDK暂不支持多进程。</font>



### <a name="ad_splash">6.2 开屏广告示例</a>

开屏广告建议在闪屏页进行展示，开屏广告的宽度和高度取决于容器的宽高，都是会撑满广告容器；**开屏广告的高度必须大于等于屏幕高度（手机屏幕完整高度，包括状态栏之类）的75%**，否则可能会影响收益计费（广点通的开屏甚至会影响跳过按钮的回调）。

```java
// 创建开屏广告实例，第一个参数可以是Activity或Fragment，第二个参数是广告容器
adSuyiSplashAd = new ADSuyiSplashAd(this, flContainer);

// 设置是否是沉浸式，如果为true，跳过按钮距离顶部的高度会加上状态栏高度
adSuyiSplashAd.setImmersive(false);

// 设置自定义跳过按钮和倒计时时长（非必传，倒计时时长范围[3000,5000]建议不要传入倒计时时长） 目前不支持inmobi, ksad, oneway, ifly平台自定义跳过按钮
adSuyiSplashAd.setSkipView(skipView, 5000);

// 设置开屏广告监听
adSuyiSplashAd.setListener(new ADSuyiSplashAdListener() {
  	@Override
    public void onADTick(long millisUntilFinished) {
      // 如果没有设置自定义跳过按钮不会回调该方法
      Log.d(ADSuyiDemoConstant.TAG, "倒计时剩余时长" + millisUntilFinished);
    }
		@Override
    public void onAdSkip(ADSuyiAdInfo adSuyiAdInfo) {
      	Log.d(ADSuyiDemoConstant.TAG, "广告跳过回调，不一定准确，埋点数据仅供参考... ");
    }
		@Override
    public void onAdReceive(ADSuyiAdInfo adSuyiAdInfo) {
    		Log.d(ADSuyiDemoConstant.TAG, "广告获取成功回调... ");
    }

    @Override
    public void onAdExpose(ADSuyiAdInfo adSuyiAdInfo) {
    		Log.d(ADSuyiDemoConstant.TAG, "广告展示回调，有展示回调不一定是有效曝光，如网络等情况导致上报失败");
    }

    @Override
    public void onAdClick(ADSuyiAdInfo adSuyiAdInfo) {
    		Log.d(ADSuyiDemoConstant.TAG, "广告点击回调，有点击回调不一定是有效点击，如网络等情况导致上报失败");
    }

    @Override
    public void onAdClose(ADSuyiAdInfo adSuyiAdInfo) {
    		Log.d(ADSuyiDemoConstant.TAG, "广告关闭回调，需要在此进行页面跳转");
        jumpMain();
    }

    @Override
    public void onAdFailed(ADSuyiError adSuyiError) {
    		if (adSuyiError != null) {
        		String failedJson = adSuyiError.toString();
            Log.d(ADSuyiDemoConstant.TAG, "onAdFailed----->" + failedJson);
        }
        jumpMain();
    }
});

// 加载开屏广告
adSuyiSplashAd.loadAd(ADSuyiDemoConstant.SPLASH_AD_POS_ID);
```

> [开屏广告示例详情](https://gitee.com/admobile/ADSuyiSdkDemo-Android/blob/master/app/src/main/java/cn/admobiletop/adsuyidemo/activity/SplashAdActivity.java)



  ### <a name="ad_banner">6.3 Banner横幅广告示例</a>

Banner横幅广告建议放置在 **固定位置**，而非ListView、RecyclerView、ViewPager等控件中充当Item，Banner广告支持多种尺寸比例，可在后台创建广告位时配置，Banner广告的宽度将会撑满容器，高度自适应，建议Banner广告容器高度也为自适应。

```java
// 创建Banner广告实例，第一个参数可以是Activity或Fragment，第二个参数是广告容器（请保证容器不会拦截点击、触摸等事件）
ADSuyiBannerAd suyiBannerAd = new ADSuyiBannerAd(this, flContainer);

// 设置自刷新时间间隔，0为不自动刷新，其他取值范围为[30,120]，单位秒
suyiBannerAd.setAutoRefreshInterval(ADSuyiDemoConstant.BANNER_AD_AUTO_REFRESH_INTERVAL);

// 设置Banner广告监听
suyiBannerAd.setListener(new ADSuyiBannerAdListener() {
		@Override
    public void onAdReceive(ADSuyiAdInfo adSuyiAdInfo) {
    		Log.d(ADSuyiDemoConstant.TAG, "onAdReceive----->");
    }

    @Override
    public void onAdExpose(ADSuyiAdInfo adSuyiAdInfo) {
    		Log.d(ADSuyiDemoConstant.TAG, "onAdExpose----->");
    }

    @Override
    public void onAdClick(ADSuyiAdInfo adSuyiAdInfo) {
    		Log.d(ADSuyiDemoConstant.TAG, "onAdClick----->");
    }

    @Override
    public void onAdClose(ADSuyiAdInfo adSuyiAdInfo) {
    		Log.d(ADSuyiDemoConstant.TAG, "onAdClose----->");
   	}

    @Override
    public void onAdFailed(ADSuyiError adSuyiError) {
    		if (adSuyiError != null) {
        		String failedJson = adSuyiError.toString();
            Log.d(ADSuyiDemoConstant.TAG, "onAdFailed----->" + failedJson);
        }
    }
});

// banner广告场景id（场景id非必选字段，如果需要可到开发者后台创建）
suyiBannerAd.setSceneId(ADSuyiDemoConstant.BANNER_AD_SCENE_ID);
// 加载Banner广告，参数为广告位ID，同一个ADSuyiBannerAd只有一次loadAd有效
suyiBannerAd.loadAd(ADSuyiDemoConstant.BANNER_AD_POS_ID);
```

>[Banner广告示例详情](https://gitee.com/admobile/ADSuyiSdkDemo-Android/blob/master/app/src/main/java/cn/admobiletop/adsuyidemo/activity/BannerAdActivity.java)



  ### <a name="ad_native">6.4 信息流广告示例</a>

信息流广告，具备自渲染和模板两种广告样式：自渲染是SDK将返回广告标题、描述、Icon、图片、多媒体视图等信息，开发者可通过自行拼装渲染成喜欢的样式；模板样式则是返回拼装好的广告视图，开发者只需将视图添加到相应容器即可，模板样式的容器高度建议是自适应。**由于信息流广告不同广告平台支持的样式不一致，有些平台不支持自渲染，有些平台不支持模板，所以下发的广告可能是模板和自渲染混合，必须开发者参考Demo适配两种类型。**
**请务必确保广告渲染时包含广告创意素材（至少包含一张图片）、平台logo、广告标识、关闭按钮； 模板广告不得被遮挡。**

``` lua
ADSuyiNativeAdInfo -- 信息流对象 根据isNativeExpress()方法：true模板类型，false自渲染类型
				|
        ├── ADSuyiNativeExpressAdInfo -- 模板类型
				|
        └── ADSuyiNativeFeedAdInfo -- 自渲染类型 根据hasMediaView()方法：true包含视频，false不包含视频
                      ├── ADSuyiNativeFeedAdInfo -- 包含视频
                      └── ADSuyiNativeFeedAdInfo -- 不包含视频
```

```java
// 创建信息流广告实例
adSuyiNativeAd = new ADSuyiNativeAd(this);
int widthPixels = getResources().getDisplayMetrics().widthPixels;
// 创建额外参数实例
ADSuyiExtraParams extraParams = new ADSuyiExtraParams.Builder()
// 设置整个广告视图预期宽高(目前仅头条平台需要，没有接入头条可不设置)，单位为px，高度如果小于等于0则高度自适应
		.adSize(new ADSuyiAdSize(widthPixels, 0))
    // 设置广告视图中MediaView的预期宽高(目前仅Inmobi平台需要,Inmobi的MediaView高度为自适应，没有接入Inmobi平台可不设置)，单位为px
    .nativeAdMediaViewSize(new ADSuyiAdSize(widthPixels))
    // 设置信息流广告适配播放是否静音，默认静音，目前广点通、百度、汇量、Admobile支持修改
    .nativeAdPlayWithMute(ADSuyiDemoConstant.NATIVE_AD_PLAY_WITH_MUTE)
   	.build();
// 设置一些额外参数，有些平台的广告可能需要传入一些额外参数，如果有接入头条、Inmobi平台，该参数必须设置
adSuyiNativeAd.setLocalExtraParams(extraParams);

// 设置广告监听
adSuyiNativeAd.setListener(new ADSuyiNativeAdListener() {
		@Override
		public void onRenderFailed(ADSuyiNativeAdInfo adSuyiNativeAdInfo, ADSuyiError adSuyiError) {
				Log.d(ADSuyiDemoConstant.TAG, "onRenderFailed: " + adSuyiError.toString());
      	// 广告渲染失败，释放和移除ADSuyiNativeAdInfo  
      	nativeAdAdapter.removeData(adSuyiNativeAdInfo);
    }

    @Override
    public void onAdReceive(List<ADSuyiNativeAdInfo> adInfoList) {
    		Log.d(ADSuyiDemoConstant.TAG, "onAdReceive: " + adInfoList.size());
        List<NativeAdSampleData> nativeAdSampleDataList = new ArrayList<>();
        for (int i = 0; i < adInfoList.size(); i++) {
        		ADSuyiNativeAdInfo nativeAdInfo = adInfoList.get(i);
            nativeAdSampleDataList.add(new NativeAdSampleData(nativeAdInfo));
        }
        nativeAdAdapter.addData(nativeAdSampleDataList);
        refreshLayout.finish(refreshType, true, false);
    }

    @Override
    public void onAdExpose(ADSuyiNativeAdInfo adSuyiNativeAdInfo) {
    		Log.d(ADSuyiDemoConstant.TAG, "onAdExpose: " + adSuyiNativeAdInfo.hashCode());
    }

    @Override
    public void onAdClick(ADSuyiNativeAdInfo adSuyiNativeAdInfo) {
    		Log.d(ADSuyiDemoConstant.TAG, "onAdClick: " + adSuyiNativeAdInfo.hashCode());
    }

    @Override
    public void onAdClose(ADSuyiNativeAdInfo adSuyiNativeAdInfo) {
    		Log.d(ADSuyiDemoConstant.TAG, "onAdClose: " + adSuyiNativeAdInfo.hashCode());
      	// 广告被关闭，释放和移除ADSuyiNativeAdInfo
        nativeAdAdapter.removeData(adSuyiNativeAdInfo);
    }

    @Override
    public void onAdFailed(ADSuyiError adSuyiError) {
    		if (adSuyiError != null) {
        		Log.d(ADSuyiDemoConstant.TAG, "onAdFailed: " + adSuyiError.toString());
        }
        refreshLayout.finish(refreshType, false, false);
    }
});

// 信息流广告场景id（场景id非必选字段，如果需要可到开发者后台创建）
adSuyiNativeAd.setSceneId(ADSuyiDemoConstant.NATIVE_AD_SCENE_ID);
// 请求广告数据，参数一广告位ID，参数二请求数量[1,3]
adSuyiNativeAd.loadAd(ADSuyiDemoConstant.NATIVE_AD_POS_ID, ADSuyiDemoConstant.NATIVE_AD_COUNT);
```

> [信息流广告示例详情](https://gitee.com/admobile/ADSuyiSdkDemo-Android/blob/master/app/src/main/java/cn/admobiletop/adsuyidemo/activity/NativeAdActivity.java)



### <a name="ad_reward_vod">6.5 激励视频广告示例</a>

将短视频融入到APP场景当中，用户观看短视频广告后可以给予一些应用内奖励。

```java
 // 创建激励视频广告实例
rewardVodAd = new ADSuyiRewardVodAd(this);

// 设置激励视频广告监听
rewardVodAd.setListener(new ADSuyiRewardVodAdListener() {
		@Override
    public void onVideoCache(ADSuyiRewardVodAdInfo adSuyiRewardVodAdInfo) {
    		Log.d(ADSuyiDemoConstant.TAG, "onVideoCache----->");
    }

		@Override
    public void onVideoComplete(ADSuyiRewardVodAdInfo adSuyiRewardVodAdInfo) {
    		Log.d(ADSuyiDemoConstant.TAG, "onVideoComplete----->");
    }

    @Override
    public void onVideoError(ADSuyiRewardVodAdInfo adSuyiRewardVodAdInfo, ADSuyiError adSuyiError) {
    		Log.d(ADSuyiDemoConstant.TAG, "onVideoError----->");
    }

    @Override
    public void onReward(ADSuyiRewardVodAdInfo adSuyiRewardVodAdInfo) {
    		Log.d(ADSuyiDemoConstant.TAG, "onReward----->");
    }

    @Override
    public void onAdReceive(ADSuyiRewardVodAdInfo rewardVodAdInfo) {
    		RewardVodAdActivity.this.rewardVodAdInfo = rewardVodAdInfo;
        Log.d(ADSuyiDemoConstant.TAG, "onAdReceive----->");
    }

    @Override
    public void onAdExpose(ADSuyiRewardVodAdInfo adSuyiRewardVodAdInfo) {
    		Log.d(ADSuyiDemoConstant.TAG, "onAdExpose----->");
    }

    @Override
    public void onAdClick(ADSuyiRewardVodAdInfo adSuyiRewardVodAdInfo) {
    		Log.d(ADSuyiDemoConstant.TAG, "onAdClick----->");
    }

    @Override
    public void onAdClose(ADSuyiRewardVodAdInfo adSuyiRewardVodAdInfo) {
    		Log.d(ADSuyiDemoConstant.TAG, "onAdClose----->");
    }

    @Override
    public void onAdFailed(ADSuyiError adSuyiError) {
    		if (adSuyiError != null) {
        		String failedJosn = adSuyiError.toString();
            Log.d(ADSuyiDemoConstant.TAG, "onAdFailed----->" + failedJosn);
        }
    }
});

// 激励广告场景id（场景id非必选字段，如果需要可到开发者后台创建）
rewardVodAd.setSceneId(ADSuyiDemoConstant.REWARD_VOD_AD_SCENE_ID);
// 加载激励视频广告，参数为广告位ID
rewardVodAd.loadAd(ADSuyiDemoConstant.REWARD_VOD_AD_POS_ID);
// 激励视频的展示，由于激励视频的获取是异步的，请在onAdReceive后调用该方法对激励视频进行展示（部分平台需要在onVideoCache回调后）
ADSuyiAdUtil.showRewardVodAdConvenient(this, RewardVodAdActivity.this.rewardVodAdInfo);
```

> [激励视频广告示例详情](https://gitee.com/admobile/ADSuyiSdkDemo-Android/blob/master/app/src/main/java/cn/admobiletop/adsuyidemo/activity/RewardVodAdActivity.java)



### <a name="ad_full_screen_vod">6.6 全屏视频广告示例</a>

全屏视频广告是类似激励视频样式的广告形式，与激励视频不同之处在于全屏视频广告播放一定时间时间后即可跳过，同时全屏视频广告拥有跳过回调不具备奖励回调。

```java
fullScreenVodAd = new ADSuyiFullScreenVodAd(this);

// 设置全屏视频监听
fullScreenVodAd.setListener(new ADSuyiFullScreenVodAdListener() {
		@Override
    public void onVideoCache(ADSuyiFullScreenVodAdInfo adSuyiFullScreenVodAdInfo) {
    		Log.d(ADSuyiDemoConstant.TAG, "onVideoCache----->");
		}

    @Override
    public void onVideoComplete(ADSuyiFullScreenVodAdInfo adSuyiFullScreenVodAdInfo) {
    		Log.d(ADSuyiDemoConstant.TAG, "onVideoComplete----->");
   	}

    @Override
    public void onVideoError(ADSuyiFullScreenVodAdInfo adSuyiFullScreenVodAdInfo, ADSuyiError adSuyiError) {
    		Log.d(ADSuyiDemoConstant.TAG, "onVideoError----->" + adSuyiError.toString());
    }

    @Override
    public void onAdReceive(ADSuyiFullScreenVodAdInfo fullScreenVodAdInfo) {
    		FullScreenVodAdActivity.this.fullScreenVodAdInfo = fullScreenVodAdInfo;
        Log.d(ADSuyiDemoConstant.TAG, "onAdReceive----->");
    }

    @Override
    public void onAdExpose(ADSuyiFullScreenVodAdInfo adSuyiFullScreenVodAdInfo) {
    		Log.d(ADSuyiDemoConstant.TAG, "onAdExpose----->");
    }

    @Override
    public void onAdClick(ADSuyiFullScreenVodAdInfo adSuyiFullScreenVodAdInfo) {
    		Log.d(ADSuyiDemoConstant.TAG, "onAdClick----->");
    }

    @Override
    public void onAdClose(ADSuyiFullScreenVodAdInfo adSuyiFullScreenVodAdInfo) {
    		Log.d(ADSuyiDemoConstant.TAG, "onAdClose----->");
    }

    @Override
    public void onAdFailed(ADSuyiError adSuyiError) {
    		if (adSuyiError != null) {
        		String failedJson = adSuyiError.toString();
            Log.d(ADSuyiDemoConstant.TAG, "onAdFailed----->" + failedJson);
        }
   	}
});

// 加载全屏视频广告
fullScreenVodAd.loadAd(ADSuyiDemoConstant.FULL_SCREEN_VOD_AD_POS_ID);
// 全屏视频的展示，由于全屏视频的获取是异步的，请在onAdReceive后调用该方法对全屏视频进行展示（部分平台需要在onVideoCache回调后）
ADSuyiAdUtil.showFullScreenAdConvenient(this, FullScreenVodAdActivity.this.fullScreenVodAdInfo);
```

> [全屏视频广告示例详情](https://gitee.com/admobile/ADSuyiSdkDemo-Android/blob/master/app/src/main/java/cn/admobiletop/adsuyidemo/activity/FullScreenVodAdActivity.java)



### <a name="ad_interstitial">6.7 插屏广告示例</a>

插屏广告是移动广告的一种常见形式，在应用流程中弹出，当应用展示插屏广告时，用户可以选择点击广告，也可以将其关闭并返回应用。

```java
interstitialAd = new ADSuyiInterstitialAd(this);

// 设置插屏广告监听
interstitialAd.setListener(new ADSuyiInterstitialAdListener() {
		@Override
    public void onAdReady(ADSuyiInterstitialAdInfo interstitialAdInfo) {
    		// 建议在该回调之后展示广告
        Log.d(ADSuyiDemoConstant.TAG, "onAdReady----->");
    }

    @Override
    public void onAdReceive(ADSuyiInterstitialAdInfo interstitialAdInfo) {
    		InterstitialAdActivity.this.interstitialAdInfo = interstitialAdInfo;
        Log.d(ADSuyiDemoConstant.TAG, "onAdReceive----->");
    }

    @Override
    public void onAdExpose(ADSuyiInterstitialAdInfo interstitialAdInfo) {
   			Log.d(ADSuyiDemoConstant.TAG, "onAdExpose----->");
    }

    @Override
    public void onAdClick(ADSuyiInterstitialAdInfo interstitialAdInfo) {
    		Log.d(ADSuyiDemoConstant.TAG, "onAdClick----->");
    }

    @Override
    public void onAdClose(ADSuyiInterstitialAdInfo interstitialAdInfo) {
    		Log.d(ADSuyiDemoConstant.TAG, "onAdClose----->");
    }

    @Override
    public void onAdFailed(ADSuyiError adSuyiError) {
    		if (adSuyiError != null) {
        		String failedJson = adSuyiError.toString();
            Log.d(ADSuyiDemoConstant.TAG, "onAdFailed----->" + failedJson);
        }
    }
});

// 插屏广告场景id（场景id非必选字段，如果需要可到开发者后台创建）
interstitialAd.setSceneId(ADSuyiDemoConstant.INTERSTITIAL_AD_SCENE_ID);
// 加载插屏广告
interstitialAd.loadAd(ADSuyiDemoConstant.INTERSTITIAL_AD_POS_ID);
// 插屏的展示，由于插屏的获取是异步的，请在onAdReceive后调用该方法对插屏进行展示
ADSuyiAdUtil.showInterstitialAdConvenient(this, InterstitialAdActivity.this.interstitialAdInfo);
```

> [插屏广告示例详情](https://gitee.com/admobile/ADSuyiSdkDemo-Android/blob/master/app/src/main/java/cn/admobiletop/adsuyidemo/activity/InterstitialAdActivity.java)



### <a name="ad_draw_vod">6.8 DrawVod广告示例</a>

类似抖音、快手小视频一样的沉浸式视频广告类型

```java
drawVodAd = new ADSuyiDrawVodAd(this);
int width = getResources().getDisplayMetrics().widthPixels;
int height = width * 16 / 9;

// 创建额外参数实例
ADSuyiExtraParams extraParams = new ADSuyiExtraParams.Builder()
		// 设置广告预期宽高(目前仅头条平台需要，没有接入头条平台可不设置)，单位为px，宽高均不能小于等于0
    .adSize(new ADSuyiAdSize(width, height))
    .build();
// 设置一些额外参数，有些平台的广告可能需要传入一些额外参数，如果有接入头条平台，该参数必须设置
drawVodAd.setLocalExtraParams(extraParams);

// 设置DrawVod广告监听
drawVodAd.setListener(new ADSuyiDrawVodAdListener() {
		@Override
    public void onRenderFailed(ADSuyiDrawVodAdInfo adSuyiDrawVodAdInfo, ADSuyiError adSuyiError) {
    		// 广告渲染失败，释放并移除ADSuyiDrawVodAdInfo
				Log.d(ADSuyiDemoConstant.TAG, "onRenderFailed: " + adSuyiError.toString());
        drawVodAdAdapter.removeData(adSuyiDrawVodAdInfo);
   	}

    @Override
    public void onAdReceive(List<ADSuyiDrawVodAdInfo> adInfoList) {
    		Log.d(ADSuyiDemoConstant.TAG, "onAdReceive: " + adInfoList.size());
        List<DrawVodAdSampleData> drawVodAdSampleDataList = new ArrayList<>();
        for (int i = 0; i < adInfoList.size(); i++) {
        		drawVodAdSampleDataList.add(new DrawVodAdSampleData(adInfoList.get(i)));
        }
        drawVodAdAdapter.addData(drawVodAdSampleDataList);
        refreshLayout.finish(refreshType, true, false);
   	}

    @Override
    public void onAdExpose(ADSuyiDrawVodAdInfo adSuyiDrawVodAdInfo) {
    		Log.d(ADSuyiDemoConstant.TAG, "onAdExpose: " + adSuyiDrawVodAdInfo.hashCode());
    }

    @Override
    public void onAdClick(ADSuyiDrawVodAdInfo adSuyiDrawVodAdInfo) {
    		Log.d(ADSuyiDemoConstant.TAG, "onAdClick: " + adSuyiDrawVodAdInfo.hashCode());
    }

    @Override
    public void onAdClose(ADSuyiDrawVodAdInfo adSuyiDrawVodAdInfo) {
    		Log.d(ADSuyiDemoConstant.TAG, "onAdClose: " + adSuyiDrawVodAdInfo.hashCode());
    		// 广告关闭，释放并移除ADSuyiDrawVodAdInfo
      	drawVodAdAdapter.removeData(adSuyiDrawVodAdInfo);
   	}

   	@Override
    public void onAdFailed(ADSuyiError adSuyiError) {
    		if (adSuyiError != null) {
        		String failedJson = adSuyiError.toString();
            Log.d(ADSuyiDemoConstant.TAG, "onAdFailed : " + failedJson);
       	}
        refreshLayout.finish(refreshType, false, false);
		}
});

// 请求广告数据，参数一广告位ID，参数二请求数量[1,3]
drawVodAd.loadAd(ADSuyiDemoConstant.DRAW_VOD_AD_POS_ID, ADSuyiDemoConstant.DRAW_VOD_AD_COUNT);
```

> [DrawVod广告示例详情](https://gitee.com/admobile/ADSuyiSdkDemo-Android/blob/master/app/src/main/java/cn/admobiletop/adsuyidemo/activity/DrawVodActivity.java)



### <a name="inner_notice">6.9 浮窗广告</a>

类似通知栏样式展示的广告，只在应用中弹出，几乎不影响用户操作，用户可以上滑左右滑动移除广告。**浮窗广告无需自行对接，只需要联系我们后台开通即可。同时我们提供一些接口方法，可以自行控制浮窗广告开启/关闭，暂停/恢复，界面过滤等。**

```java
 // 初始化ADSuyi广告SDK
ADSuyiSdk.getInstance().init(this, new ADSuyiInitConfig.Builder()
		// 设置APPID
    .appId(ADSuyiDemoConstant.APP_ID)
    // 可以通过设置该值手动关闭或开启浮窗广告，默认开启（服务端没有配置开启也不会有浮窗广告）                         
    .openFloatingAd(false)                         
    // 如果开了浮窗广告，可设置不展示浮窗广告的界面，第一个参数为是否开启默认不展示的页面（例如:激励视频播放页面），第二可变参数为自定义不展示的页面
    .floatingAdBlockList(false, "cn.admobiletop.adsuyidemo.activity.SplashAdActivity")
    .build());
```



**浮窗广告的暂停和恢复**

```java
// 可通过调用此方法暂停浮窗广告投放
ADSuyiSdk.getInstance().pauseFloatingAd();

// 可通过调用此方法恢复浮窗广告投放
ADSuyiSdk.getInstance().restartFloatingAd();
```



### 6.10 备注

具体的接入代码和流程，请参考Demo

 

## 7. 常见问题和错误调试

> [常见问题和错误调试及错误码](http://doc.admobile.top/ssp/3-常见问题/1-Android_QA.html)

如果以上地址无法跳转，请访问[备用地址](http://doc.admobile.top/ssp/)，下拉找到**Android集成常见问题**



## 8.商务合作

邮箱 : tomato@admobile.top



