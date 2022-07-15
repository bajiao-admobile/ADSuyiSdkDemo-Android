package cn.admobiletop.adsuyidemo;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.multidex.MultiDex;

import com.bytedance.sdk.openadsdk.stub.activity.Stub_Standard_Landscape_Activity;
import com.bytedance.sdk.openadsdk.stub.activity.Stub_Standard_Portrait_Activity;
import com.tencent.bugly.crashreport.CrashReport;

import cn.admobiletop.adsuyi.util.SuyiPackageStrategy;
import cn.admobiletop.adsuyidemo.activity.ad.ADSuyiInitAndLoadSplashAdActivity;
import cn.admobiletop.adsuyidemo.activity.setting.SettingActivity;
import cn.admobiletop.adsuyidemo.constant.ADSuyiDemoConstant;
import cn.admobiletop.adsuyidemo.manager.ADSuyiInterstitialManager;
import cn.admobiletop.adsuyidemo.util.SPUtil;

/**
 * @author ciba
 * @description 描述
 * @date 2020/3/25
 */
public class ADSuyiApplication extends Application {
    public static Context context;
    /**
     * 检查是否需要再次打开开屏界面的间隔时长。
     * 注意，由于activity的启动大概是70毫秒，请不要设置低于1000毫秒，不然会出现卡开屏页的bug
     * 180 * 1000 为 3分钟间隔时长，可自行修改时长
     */
    private static final long OPEN_SPLASH_ACTIVITY_INTERVAL_TIME = 180 * 1000;
    private long preMillis;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        setOnlySupportPlatform();
        context = this;
        // 添加bugly初始化（该初始化与广告SDK无关，广告SDK中不包含bugly相关内容，仅供Demo错误信息收集使用）
        CrashReport.initCrashReport(getApplicationContext(), "6d9d9f24ee", true);

        // 据悉，工信部将在2020年8月底前上线运行全国APP技术检测平台管理系统，2020年12月10日前完成覆盖40万款主流App的合规检测工作。
        // 为了保证您的App顺利通过检测，结合当前监管关注重点，我们可以将ADSuyiSdk的初始化放在用户同意隐私政策之后。

        // 如果有接开屏广告，可以设置应用进入后台一段时间后回到应用再次开启开屏界面，增加开屏广告收益（仅供参考，无需要可不设置）
        openSplashActivityAgain();
    }

    private void openSplashActivityAgain() {
        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(@NonNull Activity activity, Bundle savedInstanceState) {
                if (ADSuyiDemoConstant.INTERSTITIAL_AD_AUTO_CLOSE) {
                    if (activity instanceof Stub_Standard_Portrait_Activity
                            || activity instanceof Stub_Standard_Landscape_Activity) {
                        // 将头条activity放置到插屏管理类中，用于倒计时
                        ADSuyiInterstitialManager.getInstance().setAdInterstitialActivity(activity);
                    }
                }
            }

            @Override
            public void onActivityStarted(@NonNull Activity activity) {

            }

            @Override
            public void onActivityResumed(@NonNull Activity activity) {
                checkNeedOpenSplashActivity(activity);
            }

            @Override
            public void onActivityPaused(@NonNull Activity activity) {
                preMillis = System.currentTimeMillis();
            }

            @Override
            public void onActivityStopped(@NonNull Activity activity) {

            }

            @Override
            public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle outState) {

            }

            @Override
            public void onActivityDestroyed(@NonNull Activity activity) {

            }
        });
    }

    /**
     * 检查是否需要再次打开开屏界面
     */
    private void checkNeedOpenSplashActivity(Activity activity) {
        long millis = System.currentTimeMillis();
        if (preMillis > 0
                && millis - preMillis > OPEN_SPLASH_ACTIVITY_INTERVAL_TIME
                && !(activity instanceof ADSuyiInitAndLoadSplashAdActivity)) {
            activity.startActivity(new Intent(activity, ADSuyiInitAndLoadSplashAdActivity.class));
        }
        preMillis = millis;
    }

    /**
     * 设置仅仅支持平台
     */
    private void setOnlySupportPlatform() {
        String onlySupportPlatform = SPUtil.getString(this, SettingActivity.KEY_ONLY_SUPPORT_PLATFORM, null);
        ADSuyiDemoConstant.SPLASH_AD_ONLY_SUPPORT_PLATFORM = onlySupportPlatform;
        ADSuyiDemoConstant.BANNER_AD_ONLY_SUPPORT_PLATFORM = onlySupportPlatform;
        ADSuyiDemoConstant.NATIVE_AD_ONLY_SUPPORT_PLATFORM = onlySupportPlatform;
        ADSuyiDemoConstant.REWARD_VOD_AD_ONLY_SUPPORT_PLATFORM = onlySupportPlatform;
        ADSuyiDemoConstant.INTERSTITIAL_AD_ONLY_SUPPORT_PLATFORM = onlySupportPlatform;
    }

    @Override
    public String getPackageName() {
        return SuyiPackageStrategy.getSuyiPackageName(this);
    }
}
