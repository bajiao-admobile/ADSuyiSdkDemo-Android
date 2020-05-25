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

# admobile广告平台混淆
-keep class admsdk.library.**{*;}

# 广点通广告平台混淆
-keep class com.qq.e.** {public protected *;}
-keep class MTT.ThirdAppInfoNew {*;}
-keep class com.tencent.** {*;}

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
-keep class com.kwad.sdk.** { *;}
-keep class com.ksad.download.** { *;}
-keep class com.kwai.filedownloader.** { *;}

# AppicAd 所需要的混淆内容
-keep class * extends com.ap.android.trunk.sdk.core.base.ad.Ad
-keep class * extends com.ap.android.trunk.sdk.core.base.ad.AdSDK
-keep class * implements com.ap.android.trunk.sdk.core.base.lifecycle.IApplicationLifecycle