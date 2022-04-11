# ADSuyiSdk Android Sdk——竞价接入文档

 目录 

[TOC]

## 1. 概述
### 1.1 概述

尊敬的开发者朋友，欢迎您使用ADSuyi广告聚合SDK。通过本文档，您可以快速完成多平台竞价广告SDK的集成。

**注意：本SDK仅支持中国大陆地区**；如需发布到Google Play，请勿引入本SDK及相关依赖文件。



### 1.2 ADSuyi广告聚合SDK 组成结构

ADSuyi广告聚合SDK主要由**ADSuyi核心SDK（简称ADSuyiSdk）**和一个或多个**三方平台适配器SDK（简称AdapterSdk）**组成，开发者可以自由的在后台配置中选择需要接入的三方广告平台，然后导入所对应的AdapterSdk和竞价AdapterSdk**。



### 1.3 三方广告支持竞价平台名称概述

| Name      | 平台名称 | 平台别称 |
| --------- | -------- | -------- |
| mintegral | 汇量     | Mobvsita |


## 2. 竞价SDK接入流程

### 2.1 添加SDK到工程中

接入环境：**Android Studio**。

#### 2.1.1 添加仓库地址

首先需要在项目的build.gradle文件中引入如下配置：

```java
allprojects {
    repositories {
        ...
        // 添加以下仓库地址
        maven { url "https://maven.admobile.top/repository/maven-releases/" }
        // 添加汇量远程仓库
        maven { url "https://dl-maven-android.mintegral.com/repository/mbridge_android_sdk_support/" }
    }
}
```



#### 2.1.2 添加ADSuyiSdk和需要的AdapterSdk

将广告所需要的依赖集成进去，AdapterSdk可根据接入平台情况进行选择接入。

```java
dependencies {
  
    ...
    除了ADSuyi必要依赖外，还需要集成以下渠道竞价依赖
    ...

    // 汇量AdapterSdk（如已导入请忽视）
    implementation 'cn.admobiletop.adsuyi.ad.adapter:mintegral:16.0.27.03112'
    // 汇量竞价AdapterSdk（需要与上述版本号对应，如16.0.27）
    implementation 'cn.admobiletop.adsuyi.bid.adapter:mintegral-bid:16.0.27.03111'

}
```

## 竞价功能集成完毕，请进行调试或参照[ADSuyi聚合文档](https://gitee.com/admobile/ADSuyiSdkDemo-Android)继续完成接入 ##