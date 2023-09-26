package cn.admobiletop.adsuyidemo.activity.ad.splash;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import cn.admobiletop.adsuyi.ad.ADSuyiSplashAd;
import cn.admobiletop.adsuyi.ad.api.ADSuyiNetworkRequestInfo;
import cn.admobiletop.adsuyi.ad.api.KsSplashAdRequestInfo;
import cn.admobiletop.adsuyi.ad.data.ADSuyiAdInfo;
import cn.admobiletop.adsuyi.ad.entity.ADSuyiAdSize;
import cn.admobiletop.adsuyi.ad.entity.ADSuyiExtraParams;
import cn.admobiletop.adsuyi.ad.error.ADSuyiError;
import cn.admobiletop.adsuyi.ad.listener.ADSuyiSplashAdListener;
import cn.admobiletop.adsuyi.util.ADSuyiToastUtil;
import cn.admobiletop.adsuyidemo.R;
import cn.admobiletop.adsuyidemo.constant.ADSuyiDemoConstant;
import cn.admobiletop.adsuyidemo.util.UIUtils;

/**
 * @author ciba
 * @description 开屏广告示例，开屏广告容器请保证有屏幕高度的75%，建议开屏界面设置为全屏模式并禁止返回按钮
 * @date 2020/3/25
 */
public class SplashAdActivity extends AppCompatActivity {

    private ADSuyiSplashAd adSuyiSplashAd;

    private TextView tvSkip;
    private FrameLayout flContainer;
    private RelativeLayout rlLogoContainer;

    private String posId;
    /**
     * 开屏广告全屏并去除状态了
     */
    public static int IMMERSIVE_AND_FULLSCREEN = 0;
    /**
     * 开屏广告全屏不去除状态栏
     */
    public static int FULL_SCREEN = 1;
    /**
     * 开屏广告半屏
     */
    public static int HALF_SCREEN = 2;
    public static int splashType = IMMERSIVE_AND_FULLSCREEN;

    private int logoHeightPx;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_ad);

        posId = getIntent().getStringExtra("POSID");
        if (TextUtils.isEmpty(posId)) {
            posId = ADSuyiDemoConstant.SPLASH_AD_POS_ID1;
        }

        splashType = getIntent().getIntExtra("splashType", IMMERSIVE_AND_FULLSCREEN);


        if (splashType == 0) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }

        logoHeightPx = getIntent().getIntExtra("logoHeightPx", 0);

        flContainer = findViewById(R.id.flContainer);
        tvSkip = findViewById(R.id.tvSkip);
        rlLogoContainer = findViewById(R.id.rlLogoContainer);

        initSplashAd();
    }

    private void initSplashAd() {
        // 创建开屏广告实例，第一个参数可以是Activity或Fragment，第二个参数是广告容器（请保证容器不会拦截点击、触摸等事件，高度不小于真实屏幕高度的75%，并且处于可见状态）
        adSuyiSplashAd = new ADSuyiSplashAd(this, flContainer);

        if (splashType == IMMERSIVE_AND_FULLSCREEN) {
            // 设置是否是沉浸式，如果为true，跳过按钮距离顶部的高度会加上状态栏高度
            adSuyiSplashAd.setImmersive(true);
            rlLogoContainer.setVisibility(View.GONE);
        } else if (splashType == FULL_SCREEN) {
            adSuyiSplashAd.setImmersive(true);
            rlLogoContainer.setVisibility(View.GONE);
        } else if (splashType == HALF_SCREEN) {
            int widthPixels = UIUtils.getScreenWidthInPx(this);
            int HeightPixels = UIUtils.getRealHeight(this);

            // 创建额外参数实例
            ADSuyiExtraParams extraParams = new ADSuyiExtraParams.Builder()
                    // 设置整个广告视图预期宽高(目前仅头条平台需要，没有接入头条可不设置)，单位为px，如果不设置头条开屏广告视图将会以9 : 16的比例进行填充，小屏幕手机可能会出现素材被压缩的情况
                    .adSize(new ADSuyiAdSize(widthPixels, HeightPixels - logoHeightPx))
                    .build();
            // 如果开屏容器不是全屏可以设置额外参数
            adSuyiSplashAd.setLocalExtraParams(extraParams);

            ViewGroup.LayoutParams layoutParams = rlLogoContainer.getLayoutParams();
            layoutParams.height = logoHeightPx;
            rlLogoContainer.setLayoutParams(layoutParams);
            rlLogoContainer.setVisibility(View.VISIBLE);
        }

        if (ADSuyiDemoConstant.SPLASH_AD_CUSTOM_SKIP_VIEW) {
            // 设置自定义跳过按钮，自定义跳过按钮倒计时时长，默认是5秒，范围3000~5000，建议不修改
             adSuyiSplashAd.setSkipView(tvSkip, 5000);
        }
        // 设置仅支持的广告平台，设置了这个值，获取广告时只会去获取该平台的广告，null或空字符串为不限制，默认为null，方便调试使用，上线时建议不设置
        adSuyiSplashAd.setOnlySupportPlatform(ADSuyiDemoConstant.SPLASH_AD_ONLY_SUPPORT_PLATFORM);
        // 设置开屏广告监听
        adSuyiSplashAd.setListener(new ADSuyiSplashAdListener() {

            @Override
            public void onADTick(long millisUntilFinished) {
                Log.d(ADSuyiDemoConstant.TAG, "广告剩余倒计时时长回调：" + millisUntilFinished);
                tvSkip.setText(millisUntilFinished + "s自动跳转");
            }

            @Override
            public void onReward(ADSuyiAdInfo adSuyiAdInfo) {
                // 目前仅仅优量汇渠道会被使用
                Log.d(ADSuyiDemoConstant.TAG, "广告奖励回调... ");
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
                if (ADSuyiDemoConstant.SPLASH_AD_CUSTOM_SKIP_VIEW) {
                    tvSkip.setAlpha(1f);
                }
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
                    ADSuyiToastUtil.show(getApplicationContext(), "广告获取失败 : " + failedJson);
                }
                jumpMain();
            }
        });

        loadSplashAd();
    }

    private void loadSplashAd() {
        // 加载开屏广告，参数为广告位ID，同一个ADSuyiSplashAd只有一次loadAd有效
        adSuyiSplashAd.loadAd(ADSuyiDemoConstant.SPLASH_AD_POS_ID);
//        adSuyiSplashAd.loadAd(ADSuyiDemoConstant.SPLASH_AD_POS_ID,
//                getSplashInfo("e866cfb0", "2058622", "891", DownloadTipParam.DOWNLOAD_TIP_ALL));
    }

    /**
     * 设置开屏保底广告 目前支持优量汇、头条、百度、快手保底，保底是为了加速用户第一次获取开屏广告时的速度。
     * demo中演示百度平台，有其它平台需求的可以查看文档或在对接群中咨询
     *
     * @param platformAppId suyi开屏广告源应用ID
     * @param platformPosId suyi开屏广告源ID
     * @param adPosListId suyi开屏广告源AdPosList ID
     * @param downloadTip 下载提示 DOWNLOAD_TIP_NOTHING不提示 DOWNLOAD_TIP_MOBILE_TRAFFIC移动网络提示 DOWNLOAD_TIP_ALL 全提示
     * @return
     */
    private ADSuyiNetworkRequestInfo getSplashInfo(String platformAppId, String platformPosId, String adPosListId, int downloadTip) {
        return new KsSplashAdRequestInfo(platformAppId, platformPosId, adPosListId, downloadTip);
    }

    @Override
    public void onBackPressed() {
        // 取消返回事件，增加开屏曝光率
    }

    /**
     * 跳转到主界面
     */
    private void jumpMain() {
        finish();
    }

}
