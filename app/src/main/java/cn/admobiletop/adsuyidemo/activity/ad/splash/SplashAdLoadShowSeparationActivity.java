package cn.admobiletop.adsuyidemo.activity.ad.splash;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import cn.admobiletop.adsuyi.ad.ADSuyiSplashAd;
import cn.admobiletop.adsuyi.ad.data.ADSuyiAdInfo;
import cn.admobiletop.adsuyi.ad.error.ADSuyiError;
import cn.admobiletop.adsuyi.ad.listener.ADSuyiSplashAdListener;
import cn.admobiletop.adsuyi.util.ADSuyiToastUtil;
import cn.admobiletop.adsuyidemo.R;
import cn.admobiletop.adsuyidemo.constant.ADSuyiDemoConstant;

/**
 * @author : 草莓
 * @date : 2022/09/07
 * @description : 开屏加载展示分离
 */
public class SplashAdLoadShowSeparationActivity extends AppCompatActivity implements View.OnClickListener {

    private ADSuyiSplashAd adSuyiSplashAd;

    private RelativeLayout flSplashContainer;
    private FrameLayout flContainer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_ad_load_show_separation);

        flSplashContainer = findViewById(R.id.flSplashContainer);
        flContainer = findViewById(R.id.flContainer);

        initListener();
    }

    private void initListener() {
        findViewById(R.id.btnLoadAd).setOnClickListener(this);
        findViewById(R.id.btnShowAd).setOnClickListener(this);
    }

    private void loadAd() {
        releaseAd();
        // 创建开屏广告实例，第一个参数可以是Activity或Fragment，第二个参数是广告容器（请保证容器不会拦截点击、触摸等事件，高度不小于真实屏幕高度的75%，并且处于可见状态）
        adSuyiSplashAd = new ADSuyiSplashAd(this, flContainer);

        adSuyiSplashAd.setImmersive(true);

        // 设置仅支持的广告平台，设置了这个值，获取广告时只会去获取该平台的广告，null或空字符串为不限制，默认为null，方便调试使用，上线时建议不设置
        adSuyiSplashAd.setOnlySupportPlatform(ADSuyiDemoConstant.SPLASH_AD_ONLY_SUPPORT_PLATFORM);
        // 设置开屏广告监听
        adSuyiSplashAd.setListener(new ADSuyiSplashAdListener() {

            @Override
            public void onADTick(long millisUntilFinished) {
                Log.d(ADSuyiDemoConstant.TAG, "广告剩余倒计时时长回调：" + millisUntilFinished);
            }

            @Override
            public void onReward(ADSuyiAdInfo adSuyiAdInfo) {
                Log.d(ADSuyiDemoConstant.TAG, "广点通奖励回调");
            }

            @Override
            public void onAdSkip(ADSuyiAdInfo adSuyiAdInfo) {
                Log.d(ADSuyiDemoConstant.TAG, "广告跳过回调，不一定准确，埋点数据仅供参考... ");
            }

            @Override
            public void onAdReceive(ADSuyiAdInfo adSuyiAdInfo) {
                ADSuyiToastUtil.show(getApplicationContext(), "开屏广告获取成功");
                Log.d(ADSuyiDemoConstant.TAG, "广告获取成功回调... ");

                findViewById(R.id.btnLoadAd).setVisibility(View.GONE);
                findViewById(R.id.btnShowAd).setVisibility(View.VISIBLE);
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
                findViewById(R.id.btnLoadAd).setVisibility(View.VISIBLE);
                Log.d(ADSuyiDemoConstant.TAG, "广告关闭回调，需要在此进行页面跳转");
                jumpMain();
            }

            @Override
            public void onAdFailed(ADSuyiError adSuyiError) {
                if (adSuyiError != null) {
                    String failedJson = adSuyiError.toString();
                    Log.d(ADSuyiDemoConstant.TAG, "onAdFailed----->" + failedJson);
                    ADSuyiToastUtil.show(getApplicationContext(), "广告获取失败 : " + failedJson);
                }
                jumpMain();
            }
        });

        loadSplash();
    }

    /**
     * 释放广告
     */
    private void releaseAd() {
        if (adSuyiSplashAd != null) {
            adSuyiSplashAd.release();
            adSuyiSplashAd = null;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnLoadAd:
                loadAd();
                break;
            case R.id.btnShowAd:
                findViewById(R.id.btnShowAd).setVisibility(View.GONE);
                flSplashContainer.setVisibility(View.VISIBLE);
                adSuyiSplashAd.showSplash();
                break;
            default:
                break;
        }
    }

    private void loadSplash() {
        adSuyiSplashAd.loadOnly(ADSuyiDemoConstant.SPLASH_AD_POS_ID);
    }

    /**
     * 跳转到主界面
     */
    private void jumpMain() {
        flSplashContainer.setVisibility(View.GONE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        releaseAd();
    }
}