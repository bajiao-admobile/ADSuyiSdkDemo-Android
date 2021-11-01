package cn.admobiletop.adsuyidemo.activity.other;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import cn.admobiletop.adsuyi.ad.ADSuyiNativeAd;
import cn.admobiletop.adsuyi.ad.ADSuyiRewardVodAd;
import cn.admobiletop.adsuyi.ad.data.ADSuyiNativeAdInfo;
import cn.admobiletop.adsuyi.ad.data.ADSuyiRewardVodAdInfo;
import cn.admobiletop.adsuyi.ad.error.ADSuyiError;
import cn.admobiletop.adsuyi.ad.listener.ADSuyiNativeAdListener;
import cn.admobiletop.adsuyi.ad.listener.ADSuyiRewardVodAdListener;
import cn.admobiletop.adsuyi.util.ADSuyiAdUtil;
import cn.admobiletop.adsuyidemo.R;
import cn.admobiletop.adsuyidemo.activity.base.BaseAdActivity;
import cn.admobiletop.adsuyidemo.constant.ADSuyiDemoConstant;
import cn.admobiletop.adsuyidemo.widget.AdMobileDlExpressAdDialog;

/**
 * @author maipian
 * @description Dl广告加载失败后请求激励视频广告示例
 * @date 2020/10/21
 */
public class RewardAdSwitchActivity extends BaseAdActivity implements View.OnClickListener {

    private ADSuyiNativeAd adSuyiNativeAd;
    AdMobileDlExpressAdDialog adMobileDlAdDialog;
    private TextView tvDesc;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dl_switch);
        initListener();
        initDlAd();
    }

    private void initListener() {
        Button btnLoadAd = findViewById(R.id.btnLoadAd);
        btnLoadAd.setOnClickListener(this);
        tvDesc = findViewById(R.id.tvDesc);
    }

    private void initDlAd() {
        // 创建Dl广告实例 (注意dl广告用法与信息流广告相似，但是需要申请dl相应广告位，并非使用信息流的广告位)
        adSuyiNativeAd = new ADSuyiNativeAd(this);
        // 设置广告监听
        adSuyiNativeAd.setListener(new ADSuyiNativeAdListener() {
            @Override
            public void onRenderFailed(ADSuyiNativeAdInfo adSuyiNativeAdInfo, ADSuyiError adSuyiError) {
                Log.d(ADSuyiDemoConstant.TAG, "onRenderFailed: " + adSuyiError.toString());
                tvDesc.append("\n\nDL广告展示失败");
            }

            @Override
            public void onAdReceive(List<ADSuyiNativeAdInfo> adInfoList) {
                Log.d(ADSuyiDemoConstant.TAG, "onAdReceive: " + adInfoList.size());
                if (adInfoList != null && !adInfoList.isEmpty()) {
                    tvDesc.append("\n\n获取DL广告成功");
                    adMobileDlAdDialog = new AdMobileDlExpressAdDialog(RewardAdSwitchActivity.this);
                    adMobileDlAdDialog.render(adInfoList.get(0));
                }
            }

            @Override
            public void onAdExpose(ADSuyiNativeAdInfo adSuyiNativeAdInfo) {
                Log.d(ADSuyiDemoConstant.TAG, "onAdExpose: " + adSuyiNativeAdInfo.hashCode());
                tvDesc.append("\n\nDL广告展示成功");
            }

            @Override
            public void onAdClick(ADSuyiNativeAdInfo adSuyiNativeAdInfo) {
                /**
                 * Dl广告需要用户点击广告后才发放奖励，相当于激励视频的onReward回调
                 */
                Log.d(ADSuyiDemoConstant.TAG, "onAdClick: " + adSuyiNativeAdInfo.hashCode());
                tvDesc.append("\n\nDL广告被点击");
                //TODO 如果需要点击广告关闭dialog逻辑，解除下面注释
//                if (adMobileDlAdDialog != null) { adMobileDlAdDialog.dismiss(); }
            }

            @Override
            public void onAdClose(ADSuyiNativeAdInfo adSuyiNativeAdInfo) {
                Log.d(ADSuyiDemoConstant.TAG, "onAdClose: " + adSuyiNativeAdInfo.hashCode());
                tvDesc.append("\n\nDL广告被关闭");
                if (adMobileDlAdDialog != null) { adMobileDlAdDialog.dismiss(); }
            }

            @Override
            public void onAdFailed(ADSuyiError adSuyiError) {
                // ADSuyiToastUtil.show(getApplicationContext(), "广告获取失败");
                if (adSuyiError != null) {
                    Log.d(ADSuyiDemoConstant.TAG, "onAdFailed: " + adSuyiError.toString());
                }
                if (adMobileDlAdDialog != null) { adMobileDlAdDialog.dismiss(); }
                tvDesc.append("\n\nDL广告获取失败，开始获取激励视频");
                initRewardAd();
            }
        });
    }

    private void initRewardAd() {
        // 创建激励视频广告实例
        ADSuyiRewardVodAd rewardVodAd = new ADSuyiRewardVodAd(this);

        // 设置仅支持的广告平台，设置了这个值，获取广告时只会去获取该平台的广告，null或空字符串为不限制，默认为null，方便调试使用，上线时建议不设置
        rewardVodAd.setOnlySupportPlatform(ADSuyiDemoConstant.REWARD_VOD_AD_ONLY_SUPPORT_PLATFORM);
        // 设置激励视频广告监听
        rewardVodAd.setListener(new ADSuyiRewardVodAdListener() {
            @Override
            public void onVideoCache(ADSuyiRewardVodAdInfo adSuyiRewardVodAdInfo) {
                // 目前汇量和Inmobi走了该回调之后才准备好
                Log.d(ADSuyiDemoConstant.TAG, "onVideoCache...");
            }

            @Override
            public void onVideoComplete(ADSuyiRewardVodAdInfo adSuyiRewardVodAdInfo) {
                Log.d(ADSuyiDemoConstant.TAG, "onVideoComplete...");
            }

            @Override
            public void onVideoError(ADSuyiRewardVodAdInfo adSuyiRewardVodAdInfo, ADSuyiError adSuyiError) {
                Log.d(ADSuyiDemoConstant.TAG, "onVideoError..." + adSuyiError.toString());
                tvDesc.append("\n\n激励视频广告播放失败");
            }

            @Override
            public void onReward(ADSuyiRewardVodAdInfo adSuyiRewardVodAdInfo) {
                Log.d(ADSuyiDemoConstant.TAG, "onReward...");
            }

            @Override
            public void onAdReceive(ADSuyiRewardVodAdInfo rewardVodAdInfo) {
                tvDesc.append("\n\n激励视频广告获取成功，即将展示");
                Log.d(ADSuyiDemoConstant.TAG, "onAdReceive...");
                ADSuyiAdUtil.showRewardVodAdConvenient(RewardAdSwitchActivity.this, rewardVodAdInfo);
            }

            @Override
            public void onAdExpose(ADSuyiRewardVodAdInfo adSuyiRewardVodAdInfo) {
                Log.d(ADSuyiDemoConstant.TAG, "onAdExpose...");
                tvDesc.append("\n\n激励视频广告开始展示");
            }

            @Override
            public void onAdClick(ADSuyiRewardVodAdInfo adSuyiRewardVodAdInfo) {
                Log.d(ADSuyiDemoConstant.TAG, "onAdClick...");
                tvDesc.append("\n\n激励视频广告被点击");
            }

            @Override
            public void onAdClose(ADSuyiRewardVodAdInfo adSuyiRewardVodAdInfo) {
                Log.d(ADSuyiDemoConstant.TAG, "onAdClose...");
                tvDesc.append("\n\n激励视频广告被关闭");
            }

            @Override
            public void onAdFailed(ADSuyiError adSuyiError) {
                tvDesc.append("\n\n激励视频广告获取失败");
                if (adSuyiError != null) {
                    String failedJosn = adSuyiError.toString();
                    Log.d(ADSuyiDemoConstant.TAG, "onAdFailed..." + failedJosn);
                }
            }
        });

        rewardVodAd.loadAd(ADSuyiDemoConstant.REWARD_VOD_AD_POS_ID);
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
        tvDesc.setText("开始获取DL广告");
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
