package cn.admobiletop.adsuyidemo.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.List;

import cn.admobiletop.adsuyi.ad.ADSuyiNativeAd;
import cn.admobiletop.adsuyi.ad.data.ADSuyiNativeAdInfo;
import cn.admobiletop.adsuyi.ad.error.ADSuyiError;
import cn.admobiletop.adsuyi.ad.listener.ADSuyiNativeAdListener;
import cn.admobiletop.adsuyi.util.ADSuyiToastUtil;
import cn.admobiletop.adsuyidemo.R;
import cn.admobiletop.adsuyidemo.constant.ADSuyiDemoConstant;
import cn.admobiletop.adsuyidemo.widget.AdMobileDlAdDialog;

/**
 * @author maipian
 * @description Dl广告示例
 * @date 2020/10/21
 */
public class DlAdActivity extends AppCompatActivity implements View.OnClickListener {

    private ADSuyiNativeAd adSuyiNativeAd;
    AdMobileDlAdDialog adMobileDlAdDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reward_vod);

        initListener();
        initAd();
    }

    private void initListener() {
        Button btnLoadAd = findViewById(R.id.btnLoadAd);
        Button btnShowAd = findViewById(R.id.btnShowAd);

        btnLoadAd.setText("获取组合广告");
        btnShowAd.setVisibility(View.GONE);

        btnLoadAd.setOnClickListener(this);
    }

    private void initAd() {
        // 创建Dl广告实例 (注意dl广告用法与信息流广告相似，但是需要申请dl相应广告位，并非使用信息流的广告位)
        adSuyiNativeAd = new ADSuyiNativeAd(this);
        // 设置广告监听
        adSuyiNativeAd.setListener(new ADSuyiNativeAdListener() {
            @Override
            public void onRenderFailed(ADSuyiNativeAdInfo adSuyiNativeAdInfo, ADSuyiError adSuyiError) {
                Log.d(ADSuyiDemoConstant.TAG, "onRenderFailed: " + adSuyiError.toString());
            }

            @Override
            public void onAdReceive(List<ADSuyiNativeAdInfo> adInfoList) {
                Log.d(ADSuyiDemoConstant.TAG, "onAdReceive: " + adInfoList.size());
                if (adInfoList != null && !adInfoList.isEmpty()) {
                    ADSuyiToastUtil.show(getApplicationContext(), "广告获取成功");
                    adMobileDlAdDialog = new AdMobileDlAdDialog(DlAdActivity.this);
                    adMobileDlAdDialog.render(adInfoList.get(0));
                }
            }

            @Override
            public void onAdExpose(ADSuyiNativeAdInfo adSuyiNativeAdInfo) {
                Log.d(ADSuyiDemoConstant.TAG, "onAdExpose: " + adSuyiNativeAdInfo.hashCode());
            }

            @Override
            public void onAdClick(ADSuyiNativeAdInfo adSuyiNativeAdInfo) {
                /**
                 * Dl广告需要用户点击广告后才发放奖励，相当于激励视频的onReward回调
                 */
                Log.d(ADSuyiDemoConstant.TAG, "onAdClick: " + adSuyiNativeAdInfo.hashCode());
                ADSuyiToastUtil.show(getApplicationContext(), "广告被点击");
                // 需要用户点击后关闭dialog广告，解除下面注释
//                if (adMobileDlAdDialog != null) { adMobileDlAdDialog.dismiss(); }
            }

            @Override
            public void onAdClose(ADSuyiNativeAdInfo adSuyiNativeAdInfo) {
                Log.d(ADSuyiDemoConstant.TAG, "onAdClose: " + adSuyiNativeAdInfo.hashCode());
                if (adMobileDlAdDialog != null) { adMobileDlAdDialog.dismiss(); }
            }

            @Override
            public void onAdFailed(ADSuyiError adSuyiError) {
                // ADSuyiToastUtil.show(getApplicationContext(), "广告获取失败");
                if (adSuyiError != null) {
                    Log.d(ADSuyiDemoConstant.TAG, "onAdFailed: " + adSuyiError.toString());
                }
                if (adMobileDlAdDialog != null) { adMobileDlAdDialog.dismiss(); }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnLoadAd:
                loadAd();
                break;
            default:
                break;
        }
    }

    /**
     * 加载广告
     */
    private void loadAd() {
        adSuyiNativeAd.loadAd(ADSuyiDemoConstant.ADMOBILE_DL_AD_POS_ID);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (adMobileDlAdDialog != null) {
            adMobileDlAdDialog.dismiss();
            adMobileDlAdDialog = null;
        }
    }
}
