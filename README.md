# ADSuyiSdk Android Sdk——接入文档 V3.7.7.07141



## 1. 概述
### 1.1 概述

尊敬的开发者朋友，欢迎您使用ADSuyi广告聚合SDK。通过本文档，您可以使用最新版三方广告适配器。

**注意：本SDK仅支持中国大陆地区**；如需发布到Google Play，请勿引入本SDK及相关依赖文件。

由于ADSuyi正式版需要经过2～3周灰测后才会发布，三方广告渠道汇测期间可能在此期间又发布新版本，导致广告适配器低于各个渠道的正式版。
此分支在三方广告sdk接口没有大变动的情况下，会支持到最新版，开发者请酌情升级。


### 1.2 ADSuyi广告聚合SDK 组成结构

ADSuyi广告聚合SDK主要由**ADSuyi核心SDK（简称ADSuyiSdk）**和一个或多个**三方平台适配器SDK（简称AdapterSdk）**组成，开发者可以自由的在后台配置中选择需要接入的三方广告平台，然后导入所对应的AdapterSdk，其中**艾狄墨搏平台的AdapterSdk是必须导入的**。

### <a name="platform_name"> 1.3 三方广告平台名称概述 </a>

| Name      | 平台名称     | 平台别称     |
| --------- | ------------ | ------------ |
| tianmu    | 天目         | 天目         |
| gdt       | 优量汇       | 广点通       |
| toutiao   | 头条         | 穿山甲       |
| baidu     | 百度         | 百青藤       |
| mintegral | 汇量         | Mobvsita     |
| ksad      | 快手         | 快手         |
| mimo      | 米盟         | 米盟         |
| hwpps     | 华为广告联盟 | 华为广告联盟 |
| inmobi    | Inmobi       | Inmobi       |
| gromore   | gromore      | gromore      |

### 1.4 ADSuyi必添包容量

| Name         | 大小 | 版本号       | MD5值                            |
| ------------ | ---- | ------------ | -------------------------------- |
| ADSuyi基础包 | 0.3M | V3.7.7.07141  | 8575e20f2b5e44f1512614406fa9af2e |
| OAID         | 1.1M | —            | —                                |

### 1.5 三方广告平台适配器+三方广告sdk总容量

| Name      | 容量  | 版本号             | MD5值                            |
| --------- | ----- | ------------------ | -------------------------------- |
| tianmu    | 1.80M | v2.0.7.07174       | 794e572a4420fecf74148f9a3ac83b24 |
| gdt       | 1.92M | v4.542.1412.09251  | af175160dcb3937585265b23b803e068 |
| toutiao   | 7.00M | v5.6.0.7.09251     | a99e365188af68c537958adbf49f0d1f |
| baidu     | 1.80M | v9.322.09251        | 2651d6a5f4ed82406096fd8669b2497f |
| mintegral | 2.80M | v16.5.21.09221     | 909b8f76b99eb28e6620b73a4c0918d9 |
| ksad      | 4.20M | v3.3.53.09251      | 8626f9a53729a6d03c6abcb1e12e3930 |
| mimo      | 1.60M | v5.2.8.09251       | d96bc1a429f1b39a2271a33d89fd75db |
| hwpps     | 1.01M | v13.4.66.300.09251 | 43617828b5bbf34651ef5df68ca7d532 |
| inmobi    | 0.95M | v7.5.4.11152       | 8ecde00efc7e8af8b1fa2cbc7ebe3f89 |
| gromore   | —     | v4.2.0.2.05151     | be9089e3dcd84c218b7fa39ab1184b6e |


### 1.6 ADSuyiSdk版本号说明

版本号格式为3.0.0.xxxxn，其中xxxx代表日期，最后一位n为版本扩展号；

### 1.7 AdapterSdk版本号说明

版本号格式为 y.y.xxxxn，y.y代表三方SDK版本号（可能两位、也可能三位、四位...），其中xxxx代表日期，最后一位n为版本扩展号；

### 1.8 AdapterSdk和ADSuyiSdk版本对应说明

AdapterSdk会指定支持的ADSuyiSdk版本，**如果导入的AdapterSdk和ADSuyiSdk版本不对应会抛出异常提醒开发者使用相对应的版本**；


## 2. SDK接入流程

### 2.1 添加SDK到工程中

接入环境：**Android Studio**。

#### 2.1.1 添加仓库地址

首先需要在项目的build.gradle文件中引入如下配置：

```java
allprojects {
    repositories {
        ...
        google()
        jcenter()
        mavenCentral()
        // 添加ADSuyi相关仓库依赖
        maven { url "https://maven.admobile.top/repository/maven-releases/" }
        // 如果添加了汇量广告，需要添加汇量的远程仓库依赖
        maven { url "https://dl-maven-android.mintegral.com/repository/mbridge_android_sdk_oversea" }
        // 如果添加了华为联盟广告，需要添加华为联盟的远程仓库依赖
        maven { url 'https://developer.huawei.com/repo/' }
        // 如果添加了gromore广告，需要添加gromore的远程仓库依赖
        maven { url "https://artifact.bytedance.com/repository/pangle" }
    }
}
```

#### 2.1.2 添加ADSuyiSdk和需要的AdapterSdk

将广告所需要的依赖集成进去，AdapterSdk可根据接入平台情况进行选择接入。

```java
dependencies {
    // support支持库，如果是AndroidX请使用对应的支持库
    implementation 'com.android.support:appcompat-v7:28.0.0'
    implementation 'com.android.support:support-v4:28.0.0'
    implementation 'com.android.support:design:28.0.0'

    // ADSuyiSdk、common和OAID库是必须导入的
    implementation 'cn.admobiletop.adsuyi.ad:core:3.7.7.07141'

    // OAID库1.0.25华为渠道请参考文档5.2
    implementation(name: 'oaid_sdk_1.0.25', ext: 'aar')
    // OAID1.0.25版本适配器不支持其它版本，ADSuyi获取oaid使用
    implementation 'cn.admobiletop.adsuyi.ad:oaid:1.0.25.08024'

    // 天目AdapterSdk，必选的
    implementation 'cn.admobiletop.adsuyi.ad.adapter:tianmu:2.0.7.07174'

    // 优量汇（广点通）AdapterSdk，可选的
    implementation 'cn.admobiletop.adsuyi.ad.adapter:gdt-alpha:4.542.1412.09251'

    // 头条AdapterSdk，可选的
    implementation 'cn.admobiletop.adsuyi.ad.adapter:toutiao-alpha:5.6.0.7.09251'

    // 百度增强版AdapterSdk，可选的
    implementation 'cn.admobiletop.adsuyi.ad.adapter:baidu-enhanced-alpha:9.322.09251'

    // 汇量AdapterSdk，可选的
    implementation 'cn.admobiletop.adsuyi.ad.adapter:mintegral-alpha:16.5.21.09221'

    // 快手AdapterSdk，可选的
    implementation 'cn.admobiletop.adsuyi.ad.adapter:ksadbase-alpha:3.3.53.09251'

    // InmobiAdapterSdk，可选的
    implementation 'cn.admobiletop.adsuyi.ad.adapter:inmobi:7.5.4.11152'
    implementation 'com.squareup.picasso:picasso:2.5.2'

    // 米盟AdapterSdk，可选的
    implementation 'cn.admobiletop.adsuyi.ad.adapter:mimo-alpha:5.2.8.09251'
    implementation 'com.google.code.gson:gson:2.8.5'
    implementation 'com.github.bumptech.glide:glide:4.9.0'
    annotationProcessor 'com.github.bumptech.glide:compiler:4.9.0'

    // 华为广告联盟AdadapterSdk，可选的
    implementation 'cn.admobiletop.adsuyi.ad.adapter:hwpps-alpha:13.4.66.300.09251'

    // gromoreAdapterSdk，可选的。如果要使用gromore的其他渠道，请联系开发者。
    implementation 'cn.admobiletop.adsuyi.ad.adapter:gromore:4.2.0.2.05151'
    implementation "by.gm_mediation.com:gdt-adapter:4.520.1390.1" // 广点通 adapter
    implementation "by.gm_mediation.com:pangle-adapter:5.3.0.5.0" // 穿山甲 adapter
    // 有gromore其他渠道需求，请联系开发者。

}
```

## 3.商务合作

邮箱 : yuxingcao@admobile.top