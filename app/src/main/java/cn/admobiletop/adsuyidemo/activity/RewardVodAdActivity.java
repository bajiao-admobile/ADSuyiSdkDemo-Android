package cn.admobiletop.adsuyidemo.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import cn.admobiletop.adsuyi.ad.ADSuyiRewardVodAd;
import cn.admobiletop.adsuyi.ad.data.ADSuyiRewardVodAdInfo;
import cn.admobiletop.adsuyi.ad.error.ADSuyiError;
import cn.admobiletop.adsuyi.ad.listener.ADSuyiRewardVodAdListener;
import cn.admobiletop.adsuyi.util.ADSuyiAdUtil;
import cn.admobiletop.adsuyi.util.ADSuyiToastUtil;
import cn.admobiletop.adsuyidemo.R;
import cn.admobiletop.adsuyidemo.constant.ADSuyiDemoConstant;

/**
 * @author ciba
 * @description 激励视频广告示例
 * @date 2020/3/27
 */
public class RewardVodAdActivity extends AppCompatActivity implements View.OnClickListener {
    private ADSuyiRewardVodAdInfo rewardVodAdInfo;
    private ADSuyiRewardVodAd rewardVodAd;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reward_vod);

        initListener();
        initAd();
    }

    private void initListener() {
        findViewById(R.id.btnLoadAd).setOnClickListener(this);
        findViewById(R.id.btnShowAd).setOnClickListener(this);
    }

    private void initAd() {
        // 创建激励视频广告实例
        rewardVodAd = new ADSuyiRewardVodAd(this);

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
            }

            @Override
            public void onReward(ADSuyiRewardVodAdInfo adSuyiRewardVodAdInfo) {
                Log.d(ADSuyiDemoConstant.TAG, "onReward...");
            }

            @Override
            public void onAdReceive(ADSuyiRewardVodAdInfo rewardVodAdInfo) {
                RewardVodAdActivity.this.rewardVodAdInfo = rewardVodAdInfo;
                ADSuyiToastUtil.show(getApplicationContext(), "激励视频广告获取成功");
                Log.d(ADSuyiDemoConstant.TAG, "onAdReceive...");
            }

            @Override
            public void onAdExpose(ADSuyiRewardVodAdInfo adSuyiRewardVodAdInfo) {
                Log.d(ADSuyiDemoConstant.TAG, "onAdExpose...");
            }

            @Override
            public void onAdClick(ADSuyiRewardVodAdInfo adSuyiRewardVodAdInfo) {
                Log.d(ADSuyiDemoConstant.TAG, "onAdClick...");
            }

            @Override
            public void onAdClose(ADSuyiRewardVodAdInfo adSuyiRewardVodAdInfo) {
                Log.d(ADSuyiDemoConstant.TAG, "onAdClose...");
            }

            @Override
            public void onAdFailed(ADSuyiError adSuyiError) {
                ADSuyiToastUtil.show(getApplicationContext(), "广告获取失败");
                if (adSuyiError != null) {
                    String failedJosn = adSuyiError.toString();
                    Log.d(ADSuyiDemoConstant.TAG, "onAdFailed..." + failedJosn);
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnLoadAd:
                loadAd();
                break;
            case R.id.btnShowAd:
                ADSuyiAdUtil.showRewardVodAdConvenient(this, rewardVodAdInfo);
                break;
            default:
                break;
        }
    }

    /**
     * 加载广告
     */
    private void loadAd() {
        // 激励广告场景id（场景id非必选字段，如果需要可到开发者后台创建）
        rewardVodAd.setSceneId(ADSuyiDemoConstant.REWARD_VOD_AD_SCENE_ID);
        // 加载激励视频广告，参数为广告位ID
        rewardVodAd.loadAd(ADSuyiDemoConstant.REWARD_VOD_AD_POS_ID);
    }

}
