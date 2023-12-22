package cn.admobiletop.adsuyidemo.activity.ad.feed;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.List;

import cn.admobiletop.adsuyi.ad.ADSuyiNativeAd;
import cn.admobiletop.adsuyi.ad.data.ADSuyiNativeAdInfo;
import cn.admobiletop.adsuyi.ad.entity.ADSuyiAdSize;
import cn.admobiletop.adsuyi.ad.entity.ADSuyiExtraParams;
import cn.admobiletop.adsuyi.ad.error.ADSuyiError;
import cn.admobiletop.adsuyi.ad.listener.ADSuyiNativeAdListener;
import cn.admobiletop.adsuyi.util.ADSuyiAdUtil;
import cn.admobiletop.adsuyi.util.ADSuyiDisplayUtil;
import cn.admobiletop.adsuyidemo.R;
import cn.admobiletop.adsuyidemo.activity.ad.feed.dialog.BaseNativeInterstitialDialog;
import cn.admobiletop.adsuyidemo.activity.ad.feed.dialog.NativeExpressAdapterInterstitialAdDialog;
import cn.admobiletop.adsuyidemo.activity.ad.feed.dialog.NativeFeedAdapterInterstitialAdDialog;
import cn.admobiletop.adsuyidemo.constant.ADSuyiDemoConstant;

/**
 * @author : 草莓
 * @date : 2021/11/01
 * @description : 将信息流广告作为插屏广告使用
 *                样式需要自行调整，demo只做参考
 */
public class NativeInterstitialActivity extends AppCompatActivity {

    private Button btnLoadAd;
    private Button btnShowAd;

    private ADSuyiNativeAd adSuyiNativeAd;
    private ADSuyiNativeAdInfo adSuyiNativeAdInfo;

    private BaseNativeInterstitialDialog interstitialAdDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_native_interstitial_ad);

        initView();
        initListener();

    }

    private void initView() {
        btnLoadAd = findViewById(R.id.btnLoadAd);
        btnShowAd = findViewById(R.id.btnShowAd);
    }

    private void initListener() {
        btnLoadAd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadAd();
            }
        });

        btnShowAd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAd();
            }
        });
    }

    /**
     * 加载广告
     *
     */
    private void loadAd() {
        releaseAd();

        // 模板广告容器宽度
        int widthPixels = getResources().getDisplayMetrics().widthPixels - ADSuyiDisplayUtil.dp2px(20);
        // 创建信息流广告实例
        adSuyiNativeAd = new ADSuyiNativeAd(this);
        // 创建额外参数实例
        ADSuyiExtraParams extraParams = new ADSuyiExtraParams.Builder()
                // 设置整个广告视图预期宽高(目前仅优量汇、头条、艾狄墨搏平台需要，没有接入优量汇、头条、艾狄墨搏可不设置)，单位为px，高度如果小于等于0则高度自适应
                .adSize(new ADSuyiAdSize(widthPixels, 0))
                // 设置广告视图中MediaView的预期宽高(目前仅Inmobi平台需要,Inmobi的MediaView高度为自适应，没有接入Inmobi平台可不设置)，单位为px
                .nativeAdMediaViewSize(new ADSuyiAdSize((int) (widthPixels - 24 * getResources().getDisplayMetrics().density)))
                // 设置信息流广告适配播放是否静音，默认静音，目前优量汇、百度、汇量、快手、Admobile支持修改
                .nativeAdPlayWithMute(ADSuyiDemoConstant.NATIVE_AD_PLAY_WITH_MUTE)
                .build();
        // 设置一些额外参数，有些平台的广告可能需要传入一些额外参数，如果有接入头条、Inmobi平台，如果包含这些平台该参数必须设置
        adSuyiNativeAd.setLocalExtraParams(extraParams);
        adSuyiNativeAd.setTimeout(5000);

        // 设置仅支持的广告平台，设置了这个值，获取广告时只会去获取该平台的广告，null或空字符串为不限制，默认为null，方便调试使用，上线时建议不设置
        // 注：仅debug模式为true时生效。
        adSuyiNativeAd.setOnlySupportPlatform(ADSuyiDemoConstant.NATIVE_AD_ONLY_SUPPORT_PLATFORM);
        // 设置广告监听
        adSuyiNativeAd.setListener(new ADSuyiNativeAdListener() {
            @Override
            public void onRenderFailed(ADSuyiNativeAdInfo adSuyiNativeAdInfo, ADSuyiError adSuyiError) {
                Log.d(ADSuyiDemoConstant.TAG, "onRenderFailed: " + adSuyiError.toString());
            }

            @Override
            public void onAdReceive(List<ADSuyiNativeAdInfo> adInfoList) {
                Log.d(ADSuyiDemoConstant.TAG, "onAdReceive: " + adInfoList.size());
                if (adInfoList != null && adInfoList.size() > 0) {
                    Toast.makeText(NativeInterstitialActivity.this, "广告获取成功", Toast.LENGTH_SHORT).show();
                    adSuyiNativeAdInfo = adInfoList.get(0);
                }
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
                if (interstitialAdDialog != null) {
                    interstitialAdDialog.dismiss();
                    interstitialAdDialog = null;
                }
            }

            @Override
            public void onAdFailed(ADSuyiError adSuyiError) {
                if (adSuyiError != null) {
                    Log.d(ADSuyiDemoConstant.TAG, "onAdFailed: " + adSuyiError.toString());
                }
            }
        });

        adSuyiNativeAd.loadAd(ADSuyiDemoConstant.NATIVE_AD_POS_ID, 1);
    }

    /**
     * 展示广告
     */
    private void showAd() {
        if (ADSuyiAdUtil.adInfoIsRelease(adSuyiNativeAdInfo)) {
            Toast.makeText(this, "广告已被释放", Toast.LENGTH_SHORT).show();
            Log.d(ADSuyiDemoConstant.TAG, "广告已被释放");
            return;
        }
        if (adSuyiNativeAdInfo == null) {
            Toast.makeText(this, "未获取到广告，请先请求广告", Toast.LENGTH_SHORT).show();
            Log.d(ADSuyiDemoConstant.TAG, "未获取到广告，请先请求广告");
            return;
        }

        if (adSuyiNativeAdInfo.isNativeExpress()) {
            interstitialAdDialog = new NativeExpressAdapterInterstitialAdDialog(this);
            interstitialAdDialog.render(adSuyiNativeAdInfo);
        } else {
            interstitialAdDialog = new NativeFeedAdapterInterstitialAdDialog(this);
            interstitialAdDialog.render(adSuyiNativeAdInfo);
        }
    }

    /**
     * 释放广告
     */
    private void releaseAd() {
        if (adSuyiNativeAd != null) {
            adSuyiNativeAd.release();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        releaseAd();
    }
}
