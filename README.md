# ADSuyiSdk Android Sdk——接入文档 V3.3.2.08201

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
| Ifly      | 讯飞     | 讯飞     |
| ksad      | 快手基础版     | 快手基础版     |
| ksad   | 快手内容版   | 快手内容版     |
| mimo   | 米盟     | 米盟   |
| hwpps   | 华为广告联盟     | 华为广告联盟   |
| yunma   | 云码     | 云码   |
| iqy   | 爱奇艺     | 爱奇艺   |

### 1.4 ADSuyi必添包容量

| Name      | 大小 |
| --------- | -------- |
| ADSuyi基础包  |0.8M  |
| OAID  |1.1M  |

### 1.5 三方广告平台适配器+三方广告sdk总容量

| Name      | 容量 |
| --------- | -------- |
| gdt       | 1.41M   |
| toutiao   | 4.11M     |
| baidu     | 1.00M     |
| baiduenhanced     | 1.20M     |
| inmobi    | 0.95M   |
| mintegral | 2.80M     |
| Ifly      | 0.48M     |
| ksad(快手基础版)      | 2.30M     |
| ksadcontent(快手内容版)      | 6.00M     |
| mimo   | 0.45M     |
| hwpps   | 1.01M     |
| yunma   | 1.00M     |
| iqy   | 0.30M     |



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
  <tr>
      <td><a href="#ad_content_alliance">内容联盟</a></td>
      <td>增加App内容。注意目前仅支持快手</td>
      <td>根据需求添加</td>
    </tr>
</table>

## 3. Demo及SDK下载链接

> [ADSuyiSdkDemo-Android项目下载地址](https://codeload.github.com/ADSuyi/ADSuyiSdkDemo-Android/zip/master)
>
> [ADSuyiSdkDemo-演示APK下载地址](https://doc.admobile.top/file/ADSuyiSdkDemo.apk)
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
        maven { url "https://maven.admobile.top/repository/maven-releases/" }
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
    implementation 'cn.admobiletop.adsuyi.ad:core:3.3.2.08201'
    // common库是必须导入的，请保持和Demo中版本一致
    implementation 'com.admobile:common:1.3.2'
    // material库是必须导入的，请保持和Demo中版本一致
    implementation 'cn.admobiletop.adsuyi.ad:material:1.0.4.08191'

    // OAID库是必须导入的，请保持和Demo中版本一致（如果当前Suyi是3.0.9及以上版本，
    // 必须保证oaid版本为oaid_sdk_1.0.25，oaid_sdk_1.0.25为msa_mdid_1.0.13、oaid_sdk_1.0.23的升级版，请删除原有的msa_mdid）
    implementation(name: 'oaid_sdk_1.0.25', ext: 'aar')
    // oaid1.0.25版本适配器，导入1.0.25版本oaid必须的
    implementation 'cn.admobiletop.adsuyi.ad:oaid:1.0.25.08021'
    // 注意 oaid1.0.26与oaid1.0.25版本间有差异，不能同时导入
//    implementation(name: 'oaid_sdk_1.0.26', ext: 'aar')
    // oaid1.0.26版本适配器，导入1.0.26版本oaid必须的
//    implementation 'cn.admobiletop.adsuyi.ad:oaid:1.0.26.08041'

    // 艾狄墨搏AdapterSdk，必须的`
    implementation 'cn.admobiletop.adsuyi.ad.adapter:admobile:4.9.9.09061'

    // 广点通AdapterSdk，可选的
    implementation 'cn.admobiletop.adsuyi.ad.adapter:gdt:4.400.1270.09022'

    // 头条AdapterSdk，可选的
    implementation 'cn.admobiletop.adsuyi.ad.adapter:toutiao:3.9.0.5.09022'

    // 百度AdapterSdk，可选的
//    implementation 'cn.admobiletop.adsuyi.ad.adapter:baidu:5.98.05132'

    // 百度增强版AdapterSdk，可选的（请勿与百度同时导入）
    implementation 'cn.admobiletop.adsuyi.ad.adapter:baidu-enhanced:9.15.09021'

    // 汇量AdapterSdk，可选的
    implementation 'cn.admobiletop.adsuyi.ad.adapter:mintegral:15.7.17.09021'

    // InmobiAdapterSdk，可选的
    implementation 'cn.admobiletop.adsuyi.ad.adapter:inmobi:7.5.1.11112'
    implementation 'com.squareup.picasso:picasso:2.5.2'

    // 讯飞AdapterSdk，可选的
    implementation 'cn.admobiletop.adsuyi.ad.adapter:ifly:5.0.2.06011'

    // 快手基础版AdapterSdk，可选的
//    implementation 'cn.admobiletop.adsuyi.ad.adapter:ksadbase:3.3.14.1.09021'

    // 快手内容版AdapterSdk，可选的（比快手基础版多一个内容组件，不需要内容组件无需导入该版本，不可和快手基础版同时导入）
    implementation 'cn.admobiletop.adsuyi.ad.adapter:ksadcontent:3.3.22.3.09013'

    // 米盟AdapterSdk，可选的
    implementation 'cn.admobiletop.adsuyi.ad.adapter:mimo:5.1.1.08111'
    implementation 'com.google.code.gson:gson:2.8.5'
    implementation 'com.github.bumptech.glide:glide:4.9.0'
    annotationProcessor 'com.github.bumptech.glide:compiler:4.9.0'

    // 华为广告联盟AdadapterSdk，可选的
    implementation 'cn.admobiletop.adsuyi.ad.adapter:hwpps:13.4.45.308.08111'

    // 云码AdapterSdk，可选的
    implementation 'cn.admobiletop.adsuyi.ad.adapter:yunma:1.0.5.09021'

    // 爱奇艺AdapterSdk，可选的
    implementation 'cn.admobiletop.adsuyi.ad.adapter:iqy:1.3.12.08111'
      
    // 小说内容SDK（还需要gson、glide4.9.0和recyclerview支持）
    implementation 'cn.admobiletop.adsuyi.ad.adapter:novel:1.2.8.06112'
    implementation 'com.google.code.gson:gson:2.8.5'
    // 小说sdk必须依赖4.9.0版本glide
    implementation 'com.github.bumptech.glide:glide:4.9.0'
    implementation 'com.android.support:recyclerview-v7:28.0.0'

}
```



#### 5.1.3 注意事项 

* 支持主流架构，x86架构暂不支持

  ```java
  ndk {
  	// 设置支持的SO库架构，暂不支持x86
  	abiFilters 'armeabi-v7a', 'arm64-v8a' // 'armeabi'
  }
  ```

* **AdapterSdk默认已经集成了三方的广告SDK**，如果因为项目中也使用了相同的三方广告SDK而发生冲突，可通过以下方法尝试避免或解决；

1. 移除己方使用的三方广告SDK和相关配置；

2. 使用**AdapterSdk**的**without**集成方式，该方式没有集成三方广告SDK和配置，开发者可自行集成三方广告SDK，但是需要注意，<font color=#ff0000>我们的AdapterSdk是基于三方广告SDK某个版本开发的，如果自行集成三方广告SDK，需要承担三方广告SDK版本不一致可能引起的兼容性和其他不可预知问题；</font>

   ```java
   // 广点通AdapterSdk的without集成示例，其中x.x.x.x为AdapterSdk版本号
   implementation 'cn.admobiletop.adsuyi.ad.adapter:gdt-without:x.x.x.x'
   ```

* 如果接入汇量，需要加入第三方依赖库https://dl-maven-android.mintegral.com/repository/mbridge_android_sdk_support/
* 如果接入云码，需要加入第三方依赖库https://maven.aliyun.com/nexus/content/repositories/releases/
* 如果接入华为联盟，需要加入第三方依赖库https://developer.huawei.com/repo/
* **广点通适配器4.270.1140版本及以上已经导入了腾讯的tbs，请移除原有的tbs避免编译失败；**
* **广点通适配器4.310.1180版本及以上已经将腾讯tbs移除，媒体需要手动导入tbs，避免自身项目需要依赖tbs导致编译失败；**
* **由于头条(穿山甲)渠道支持了Android R，引入了Android R的 <queries> 标签,需要对gradle版本进行限制，限制范围为：3.3.3、 3.4.3、 3.5.4、3.6.4、4.0.1 ，开发者根据自身情况酌情升级**
* **如果要对接小说sdk，需要导入java8配置**
   ```java
    compileOptions {
        sourceCompatibility JavaVersion.VERSION_1_8
        targetCompatibility JavaVersion.VERSION_1_8
    }
   ```
* **如对接华为广告联盟，激励视频要提前预加载，并且播放完成后需要预加载下一个激励视频；banner广告使用场景是程序页面的顶部或者底部。**
* **快手区分为基础版和内容版，内容版比基础版多了一个内容组件，请开发者按需求进行导入。**

3. 激励、全屏视频、插屏等广告对象一次成功拉取的广告数据只允许展示一次，还需展示请再次加载广告。

4. 关于项目使用autosize后出现广告样式出现异常问题处理方案，请参考master-screen-adapter分支中的BannerActivity，并将适配单位改为pt。
### 5.2 OAID支持

**Android10之后IMEI等数据无法获取，这对广告投放将产生一定影响，所以移动安全联盟(MSA)提出OAID来代替IMEI参与广告投放决策，OAID的支持会在一定程度上影响广告收益；**

<font color=#ff0000>OAID是必须集成项，没有集成将会抛出异常提醒开发者</font>，OAID集成并不繁琐，SDK中已经进行了OAID的封装，只需以下几步即可完成OAID的支持；

1. 导入安全联盟的OAID支持库 **oaid_sdk_1.0.25.aar或oaid_sdk_1.0.26.aar**，可在Demo的libs目录下找到，**强烈建议使用和Demo中一样版本的OAID库（包括项目中已存在的依赖的oaid版本）；**<br>
    由于oaid1.0.25版本和oaid1.0.26版本接口有变更，不能向下兼容，并且1.0.26版本还需要申请密钥才可使用，故suyi平台提供相应版本适配器进行适配。<br>
    如果没有特殊需求，可以使用oaid1.0.25及相应适配器。<br>
    如果有特殊需求，可以使用oaid1.0.26及相应适配器，并且在初始化时传入assets中的密钥（demo SplashAdActivity 145行），以便适配器中进行初始化。<br>
    媒体如果想获取ADSuyi中的oaid，可以使用改方法进行获取ADSuyiSdk.getInstance().getOAID()，由于oaid的获取是异步的，可能获取到空字符串的情况。<br>

2. 将Demo中assets文件夹下的**supplierconfig.json**文件复制到自己的assets目录下并按照**supplierconfig.json**文件中的说明进行OAID的 **AppId** 配置，**supplierconfig.json**文件名不可修改。需要设置 appid 的部分需要去对应厂商的应用商店的应用信息中查看。；

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
   -keep class com.zui.opendeviceidlibrary.**{*;}
   -keep class org.json.**{*;}
   -keep public class com.netease.nis.sdkwrapper.Utils {public <methods>;}
   ```
4. 修改AndroidManifest.xml，**OAID SDK minSdkVersion为21，如果应用的minSdkVersion小于21，则添加：**
    ```java
    // 如果导入后有冲突可以不添加，suyi中已经添加过了
    <uses-sdk tools:overrideLibrary="com.bun.miitmdid"/>
    ```

**PS：需要更多帮助可参考目录下《移动智能终端补充设备标识体系统一调用SDK开发者说明文档》；**



### 5.3 权限申请

  使用SDK时可能需要以下权限，为了保证使用广告的正确，请在6.0及以上的手机中使用SDK前及时申请。

  ```java
    <!-- 广告必须的权限 -->
    <uses-permission android:name="android.permission.INTERNET" />
    <!-- 广告必须的权限，写入权限，用于下载类广告数据写入 -->
    <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
    <!-- 广告必须的权限，读取权限，用于下载类广告数据读取（如判断是否已下载过该APK，避免重复下载）-->
    <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" />
    <!-- 广告必须的权限，允许安装未知来源权限（如下载类广告下载完成后唤起安卓） -->
    <uses-permission android:name="android.permission.REQUEST_INSTALL_PACKAGES" />
    <!-- 广告必须的权限，地理位置权限，获取位置信息 -->
    <uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" />

    <!-- 广点通广告必须的权限 -->
    <uses-permission android:name="android.permission.ACCESS_WIFI_STATE" />
    <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />

    <!-- 影响广告填充，强烈建议的权限，获取设备信息 -->
    <uses-permission android:name="android.permission.READ_PHONE_STATE" />

    <!-- 为了提高广告收益，建议设置的权限，获取粗略位置信息 -->
    <uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION" />

    <!-- 如果有视频相关的广告播放请务必添加，屏幕保持唤醒不锁屏（部分渠道未添加该权限时会出现视频类广告黑屏）-->
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
-keep class cn.admobiletop.materialutil.**{public *;}
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
-keep class com.zui.opendeviceidlibrary.**{*;}
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
-keep class com.bytedance.embed_dr.** {*;}
-keep class com.bytedance.embedapplog.** {*;}
-keep class com.bytedance.frameworks.** { *; }
-keep class ms.bd.c.Pgl.**{*;}
-keep class com.bytedance.mobsec.metasec.ml.**{*;}
-keep class com.ss.android.**{*;}
-keep class com.bykv.vk.** {*;}

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
-keepattributes Signature
-keepattributes *Annotation*
-keep class com.mbridge.** {*; }
-keep interface com.mbridge.** {*; }
-keep class android.support.v4.** { *; }
-dontwarn com.mbridge.**
-keep class **.R$* { public static final int mbridge*; }

# 快手广告平台混淆
-keep class org.chromium.** { *; }
-keep class aegon.chrome.** { *; }
-keep class com.kwai.**{ *; }
-keep class com.kwad.**{ *; }
-dontwarn com.kwai.**
-dontwarn com.kwad.**
-dontwarn com.ksad.**
-dontwarn aegon.chrome.**
-keep class com.kwad.sdk.** { *;}
-keep class com.ksad.download.** { *;}
-keep class com.kwai.filedownloader.** { *;}
-keepclasseswithmembernames class * { native <methods>;}

# AppicAd广告平台混淆
-keep class * extends com.ap.android.trunk.sdk.core.base.ad.Ad
-keep class * extends com.ap.android.trunk.sdk.core.base.ad.AdSDK
-keep class * implements com.ap.android.trunk.sdk.core.base.lifecycle.IApplicationLifecycle

# 讯飞广告平台混淆
-dontwarn com.iflytek.**
-keep class com.iflytek.** {* ;}
-keep class android.support.v4.**{public * ;}
-dontwarn com.shu.priory.**
-keep class com.shu.priory.**{*;}
-keep class android.support.v4.**{public * ;}

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

# 华为PPS混淆
-keep class com.huawei.openalliance.ad.** { *; }
-dontwarn com.huawei.openalliance.ad.activity.PPSActivity
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public class * extends com.bumptech.glide.module.AppGlideModule
-keep public enum com.bumptech.glide.load.ImageHeaderParser$** { **[] $VALUES; public *; }
-dontwarn com.bumptech.glide.load.resource.bitmap.VideoDecoder

# 云码混淆
-keep class com.alibaba.sdk.android.** {*;}
-keep class io.vov.** {*;}
-keep class com.UCMobile.Apollo.** {*;}

# 爱奇艺
-keep class com.mcto.sspsdk.** { *; }

# NovelAdapter混淆
-keep class android.**{*;}
-keep class com.ecook.** {* ;}
-keep class com.parting_soul.http.** {* ;}
-keep class com.ttx.reader.support.** {* ;}
-ignorewarnings
-keepattributes Signature
-keep class android.**{*;}
-keep class com.ecook.novel_sdk.bookstore.data.bean.* {*;}
```

### 5.5 隐私信息控制开关

**为了保证您的App顺利通过检测，结合当前监管关注重点，我们可以将ADSuyiSdk的初始化放在用户同意隐私政策之后。**

**如果有更高需求，可以使用以下方法进行控制，但会对广告填充造成影响。**
同时ADSuyiSDK初始化时开放以下接口，确保mac，imei等设备标识不被读取（目前部分三方广告平台支持）：
```java
// 是否同意隐私政策
.agreePrivacyStrategy(true)
// 是否同意使用oaid
.isCanUseOaid(true)
// 是否可读取wifi状态
.isCanUseWifiState(true)
// 是否可获取定位数据
.isCanUseLocation(true)
// 是否可获取设备信息
.isCanUsePhoneState(true)
```
另外还可从根源上解决设备标识被读取等问题，可对配置清单中的权限增加tools:node="remove"配置；
如下：
```java
<!-- 影响广告填充，强烈建议的权限 -->
<uses-permission android:name="android.permission.READ_PHONE_STATE" tools:node="remove" />
<!-- 为了提高广告收益，建议设置的权限 -->
<uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" tools:node="remove" />
<uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION" tools:node="remove" />
```
以上操作会对广告填充造成影响，请斟酌使用。

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
         // 是否可读取wifi状态
         .isCanUseWifiState(true)
         // 是否可获取定位数据
         .isCanUseLocation(true)
         // 是否可获取设备信息
         .isCanUsePhoneState(true)
         // 是否过滤第三方平台的问题广告（例如: 已知某个广告平台在某些机型的Banner广告可能存在问题，如果开启过滤，则在该机型将不再去获取该平台的Banner广告）
         .filterThirdQuestion(true)
         // 注意：如果使用oaid1.0.26版本，需要在assets中放置密钥，并将密钥传入ADSuyi（suyi内部初始化oaid需要使用）
         // 密钥需要到移动安全联盟申请（非oaid1.0.26版本无需使用该接口）
         .setOaidCertPath("cn.admobiletop.adsuyidemo.cert.pem")
         .build());
  ```

  <font color=#ff0000>PS ：AppId通过后台配置生成，初始化必须在主线程中进行，SDK暂不支持多进程。</font>



### <a name="ad_splash">6.2 开屏广告示例</a>

开屏广告建议在闪屏页进行展示，开屏广告的宽度和高度取决于容器的宽高，都是会撑满广告容器；**开屏广告的高度必须大于等于屏幕高度（手机屏幕完整高度，包括状态栏之类）的75%**，否则可能会影响收益计费（广点通的开屏甚至会影响跳过按钮的回调）。

```java
// 创建开屏广告实例，第一个参数可以是Activity或Fragment，第二个参数是广告容器
adSuyiSplashAd = new ADSuyiSplashAd(this, flContainer);

// 底部logo容器高度，请根据实际情况进行计算
int logoHeight = 底部logo布局高度;
// 屏幕宽度px
int widthPixels = getResources().getDisplayMetrics().widthPixels;
// 屏幕高度px
int heightPixels = getResources().getDisplayMetrics().heightPixels;
// 创建额外参数实例
ADSuyiExtraParams extraParams = new ADSuyiExtraParams.Builder()
        // 设置整个广告视图预期宽高(目前仅头条平台需要，没有接入头条可不设置)，单位为px，如果不设置头条开屏广告视图将会以9 : 16的比例进行填充，小屏幕手机可能会出现素材被压缩的情况
        .adSize(new ADSuyiAdSize(widthPixels, heightPixels - logoHeight))
        .build();
// 如果开屏容器不是全屏可以设置额外参数
adSuyiSplashAd.setLocalExtraParams(extraParams);

// 设置是否是沉浸式，如果为true，跳过按钮距离顶部的高度会加上状态栏高度
adSuyiSplashAd.setImmersive(false);

// 设置自定义跳过按钮和倒计时时长（非必传，倒计时时长范围[3000,5000]建议不要传入倒计时时长） 目前不支持inmobi, ksad, oneway, ifly平台自定义跳过按钮
// 注意不要隐藏跳过按钮，可以在布局中将跳过按钮alpha设置为0，在onAdReceive回调中将alpha设置为1
adSuyiSplashAd.setSkipView(skipView, 5000);

// 设置开屏广告监听
adSuyiSplashAd.setListener(new ADSuyiSplashAdListener() {
  	@Override
    public void onADTick(long countdownSeconds) {
      // 如果没有设置自定义跳过按钮不会回调该方法（单位为秒）
      Log.d(ADSuyiDemoConstant.TAG, "倒计时剩余时长（单位秒）" + countdownSeconds);
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

/**
 * 加载开屏保底广告，可选的
 * 功能说明：App在首次启动时，需要先请求获取广告位配置文件后，然后再去请求开屏广告，也就是首次加载开屏广告时需要两次串行网络请求，因此很容易因超时导致开屏广告展示失败。
 * 解决方案：为避免开屏超时问题，开放此设置给开发者，开发者可以根据实际需求选择一家广告平台，通过API接口将必需参数传递给Suyi聚合SDK。（该设置只能指定一家广告平台，并且首次启动时只会请求该平台的广告，但App首次开屏广告将不受ADmobile后台控制，包括下载提示，广告位关闭。）
 * 该设置仅会在首次加载开屏广告时，SDK会使用开发者传入的参数进行广告请求，同时获取后台配置文件的广告配置信息缓存到本地（首次请求广告平台广告和获取配置信息时并发进行），后续的开屏广告将按照缓存缓存的后台广告位配置顺序进行开屏广告请求。
 * 支持穿山甲、优量汇、快手、百度
 */
adSuyiSplashAd.loadAd(ADSuyiDemoConstant.SPLASH_AD_POS_ID, new BaiduSplashAdRequestInfo(platformAppId, platformPosId, adPosListId, downloadTip));

/**
 * 获取百度开屏保底广告Info
 *
 * @param platformAppId suyi开屏广告源应用ID
 * @param platformPosId suyi开屏广告源ID
 * @param adPosListId suyi开屏广告源AdPosList ID
 * @param downloadTip 下载提示 DownloadTipParam.DOWNLOAD_TIP_NOTHING不提示 DownloadTipParam.DOWNLOAD_TIP_MOBILE_TRAFFIC移动网络提示 DownloadTipParam.DOWNLOAD_TIP_ALL 全提示
 * @return
 */
new BaiduSplashAdRequestInfo(String platformAppId, String platformPosId, String adPosListId, int downloadTip)
/**
 * 获取广点通开屏保底广告Info
 * ...
 */
new GdtSplashAdRequestInfo(String platformAppId, String platformPosId, String adPosListId, int downloadTip)
/**
 * 获取快手开屏保底广告Info
 * ...
 */
new KsSplashAdRequestInfo(String platformAppId, String platformPosId, String adPosListId, int downloadTip)

/**
 * 获取头条开屏保底广告Info
 *
 * @param platformAppId suyi开屏广告源应用ID
 * @param platformPosId suyi开屏广告源ID
 * @param adPosListId suyi开屏广告源AdPosList ID
 * @param downloadTip 下载提示 DownloadTipParam.DOWNLOAD_TIP_NOTHING不提示 DownloadTipParam.DOWNLOAD_TIP_MOBILE_TRAFFIC移动网络提示 DownloadTipParam.DOWNLOAD_TIP_ALL 全提示
 * @param renderTip 渲染方式 RenderTypeParam.RENDER_TYPE_NATIVE_EXPRESS模版渲染 RenderTypeParam.RENDER_TYPE_NATIVE原生渲染
 * @return
 */
new TTSplashAdRequestInfo(String platformAppId, String platformPosId, String adPosListId, int downloadTip, int renderTip)

```

> [开屏广告示例详情](https://gitee.com/admobile/ADSuyiSdkDemo-Android/blob/master/app/src/main/java/cn/admobiletop/adsuyidemo/activity/ad/SplashAdActivity.java)



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

>[Banner广告示例详情](https://gitee.com/admobile/ADSuyiSdkDemo-Android/blob/master/app/src/main/java/cn/admobiletop/adsuyidemo/activity/ad/BannerAdActivity.java)



  ### <a name="ad_native">6.4 信息流广告示例</a>

信息流广告，具备自渲染和模板两种广告样式：自渲染是SDK将返回广告标题、描述、Icon、图片、多媒体视图等信息，开发者可通过自行拼装渲染成喜欢的样式；模板样式则是返回拼装好的广告视图，开发者只需将视图添加到相应容器即可，模板样式的容器高度建议是自适应。
**请务必确保自渲染类型广告渲染时包含广告创意素材（至少包含一张图片）、平台logo、广告标识、关闭按钮；模板广告不得被遮挡。**
**注意，信息流广告点击关闭时，开发者需要在onAdClose回调中将广告容器隐藏或移除，避免如头条渠道点击关闭后视图依旧存在问题**

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
// 设置整个广告视图预期宽高(目前仅头条，艾狄墨搏平台需要，没有接入头条、艾狄墨搏可不设置)，单位为px，高度如果小于等于0则高度自适应
		.adSize(new ADSuyiAdSize(widthPixels, 0))
    // 设置广告视图中MediaView的预期宽高(目前仅Inmobi平台需要,Inmobi的MediaView高度为自适应，没有接入Inmobi平台可不设置)，单位为px
    .nativeAdMediaViewSize(new ADSuyiAdSize(widthPixels))
    // 设置模版广告文字、内边距特殊样式（目前仅艾狄墨搏平台需要，没有特殊需求可不设置，请查看demo进行设置）
    .nativeStyle(nativeStyle)
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
      	// 注意，信息流广告点击关闭时，开发者需要在onAdClose回调中将广告容器隐藏或移除，避免如头条渠道点击关闭后视图依旧存在
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

> [信息流广告示例详情](https://gitee.com/admobile/ADSuyiSdkDemo-Android/blob/master/app/src/main/java/cn/admobiletop/adsuyidemo/activity/ad/NativeAdActivity.java)



### <a name="ad_reward_vod">6.5 激励视频广告示例</a>

将短视频融入到APP场景当中，用户观看短视频广告后可以给予一些应用内奖励。

```java
 // 创建激励视频广告实例
rewardVodAd = new ADSuyiRewardVodAd(this);

ADSuyiRewardExtra adSuyiRewardExtra = new ADSuyiRewardExtra("用户id");
// 设置激励视频服务端验证的自定义信息
adSuyiRewardExtra.setCustomData("设置激励视频服务端验证的自定义信息");
// 设置激励视频服务端激励名称(mintegral渠道不支持)
adSuyiRewardExtra.setRewardName("激励名称");
// 设置激励视频服务端激励数量(mintegral渠道不支持)
adSuyiRewardExtra.setRewardAmount(1);
rewardVodAd.setLocalExtraParams(new ADSuyiExtraParams.Builder().rewardExtra(adSuyiRewardExtra).build());

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
        // 激励视频广告对象一次成功拉取的广告数据只允许展示一次
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

> [激励视频广告示例详情](https://gitee.com/admobile/ADSuyiSdkDemo-Android/blob/master/app/src/main/java/cn/admobiletop/adsuyidemo/activity/ad/RewardVodAdActivity.java)



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
        // 全屏视频广告对象一次成功拉取的广告数据只允许展示一次
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

> [全屏视频广告示例详情](https://gitee.com/admobile/ADSuyiSdkDemo-Android/blob/master/app/src/main/java/cn/admobiletop/adsuyidemo/activity/ad/FullScreenVodAdActivity.java)



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
        // 插屏广告对象一次成功拉取的广告数据只允许展示一次
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

> [插屏广告示例详情](https://gitee.com/admobile/ADSuyiSdkDemo-Android/blob/master/app/src/main/java/cn/admobiletop/adsuyidemo/activity/ad/InterstitialAdActivity.java)



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

> [DrawVod广告示例详情](https://gitee.com/admobile/ADSuyiSdkDemo-Android/blob/master/app/src/main/java/cn/admobiletop/adsuyidemo/activity/ad/DrawVodActivity.java)



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
    .floatingAdBlockList(false, "cn.admobiletop.adsuyidemo.activity.ad.SplashAdActivity")
    .build());
```



**浮窗广告的暂停和恢复**

```java
// 可通过调用此方法暂停浮窗广告投放
ADSuyiSdk.getInstance().pauseFloatingAd();

// 可通过调用此方法恢复浮窗广告投放
ADSuyiSdk.getInstance().restartFloatingAd();
```

  ### <a name="ad_content_alliance">6.10 内容联盟示例</a>

**内容联盟，目前支持快手入口组件**

```java
// 创建内容联盟实例
adSuyiContentAllianceAd = new ADSuyiContentAllianceAd(this);
// 设置监听
adSuyiContentAllianceAd.setListener(new ADSuyiContentAllianceAdListener() {


    @Override
    public void onAdReceive(ADSuyiContentAllianceAdInfo adSuyiContentAllianceAdInfo) {
        Log.d(ADSuyiDemoConstant.TAG, "onAdReceive: " + adSuyiContentAllianceAdInfo);
        if (adSuyiContentAllianceAdInfo != null) {
            contentAllianceAdInfo = adSuyiContentAllianceAdInfo;
            // 可以根据demo中将广告对象放入适配器中进行渲染或在必要的位置调用contentAllianceAdInfo.openKSContentPage(this)方法进行内容详情页的跳转
            tempDataList.add(new ContentAllianceAdSampleData(adSuyiContentAllianceAdInfo));
            contentAllianceAdAdapter.addData(tempDataList);
        }
        refreshLayout.finish(refreshType, true, false);
    }

    @Override
    public void onAdExpose(ADSuyiContentAllianceAdInfo adSuyiContentAllianceAdInfo) {
        Log.d(ADSuyiDemoConstant.TAG, "onAdExpose: " + adSuyiContentAllianceAdInfo);
    }

    @Override
    public void onAdClick(ADSuyiContentAllianceAdInfo adSuyiContentAllianceAdInfo) {
        Log.d(ADSuyiDemoConstant.TAG, "onAdClick: " + adSuyiContentAllianceAdInfo);
    }

    @Override
    public void onAdClose(ADSuyiContentAllianceAdInfo adSuyiContentAllianceAdInfo) {
        Log.d(ADSuyiDemoConstant.TAG, "onAdClose: " + adSuyiContentAllianceAdInfo);
    }

    @Override
    public void onAdFailed(ADSuyiError adSuyiError) {
        if (adSuyiError != null) {
            Log.d(ADSuyiDemoConstant.TAG, "onAdFailed: " + adSuyiError.toString());
        }
        contentAllianceAdAdapter.addData(tempDataList);
        refreshLayout.finish(refreshType, false, false);
    }
});

// 内容联盟广告场景id（场景id非必选字段，如果需要可到开发者后台创建）
adSuyiContentAllianceAd.setSceneId(ADSuyiDemoConstant.CONTENT_ALLIANCE_AD_SCENE_ID);
// 请求广告数据，参数一广告位ID
adSuyiContentAllianceAd.loadAd(ADSuyiDemoConstant.CONTENT_ALLIANCE_AD_POS_ID);
// 不放入适配器中直接跳转展示内容，由于内容联盟的获取是异步的，请在onAdReceive后调用该方法进行跳转展示
contentAllianceAdInfo.openKSContentPage(this)
```

> [内容联盟示例详情](https://gitee.com/admobile/ADSuyiSdkDemo-Android/blob/master/app/src/main/java/cn/admobiletop/adsuyidemo/activity/other/ContentAllianceAdActivity.java)

### 6.11 备注

具体的接入代码和流程，请参考Demo

 

## 7. 常见问题和错误调试

> [常见问题和错误调试及错误码](https://doc.admobile.top/ssp/2-%E6%8A%80%E6%9C%AF%E5%AF%B9%E6%8E%A5/6-Android_QA.html)

如果以上地址无法跳转，请访问[备用地址](http://doc.admobile.top/ssp/)，下拉找到**Android集成常见问题**



## 8.商务合作

邮箱 : tomato@admobile.top



