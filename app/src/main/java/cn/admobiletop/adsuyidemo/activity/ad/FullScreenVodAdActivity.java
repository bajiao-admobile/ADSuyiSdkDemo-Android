package cn.admobiletop.adsuyidemo.activity.ad;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import cn.admobiletop.adsuyi.ad.ADSuyiFullScreenVodAd;
import cn.admobiletop.adsuyi.ad.data.ADSuyiFullScreenVodAdInfo;
import cn.admobiletop.adsuyi.ad.error.ADSuyiError;
import cn.admobiletop.adsuyi.ad.listener.ADSuyiFullScreenVodAdListener;
import cn.admobiletop.adsuyi.util.ADSuyiAdUtil;
import cn.admobiletop.adsuyi.util.ADSuyiToastUtil;
import cn.admobiletop.adsuyidemo.R;
import cn.admobiletop.adsuyidemo.activity.base.BaseAdActivity;
import cn.admobiletop.adsuyidemo.constant.ADSuyiDemoConstant;

/**
 * @author ciba
 * @description 全屏视频广告示例
 * @date 2020/3/27
 */
public class FullScreenVodAdActivity extends BaseAdActivity implements View.OnClickListener {
    private ADSuyiFullScreenVodAd fullScreenVodAd;
    private ADSuyiFullScreenVodAdInfo fullScreenVodAdInfo;

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

        btnLoadAd.setText("获取全屏视频广告");
        btnShowAd.setText("展示全屏视频广告");

        btnLoadAd.setOnClickListener(this);
        btnShowAd.setOnClickListener(this);
    }

    private void initAd() {
        fullScreenVodAd = new ADSuyiFullScreenVodAd(this);
        // 设置仅支持的广告平台，设置了这个值，获取广告时只会去获取该平台的广告，null或空字符串为不限制，默认为null，方便调试使用，上线时建议不设置
        fullScreenVodAd.setOnlySupportPlatform(ADSuyiDemoConstant.FULL_SCREEN_VOD_AD_ONLY_SUPPORT_PLATFORM);
        // 设置全屏视频监听
        fullScreenVodAd.setListener(new ADSuyiFullScreenVodAdListener() {

            @Override
            public void onAdReceive(ADSuyiFullScreenVodAdInfo fullScreenVodAdInfo) {
                // 建议在该回调中展示广告
                // TODO 全屏视频广告对象一次成功拉取的广告数据只允许展示一次
                FullScreenVodAdActivity.this.fullScreenVodAdInfo = fullScreenVodAdInfo;
                ADSuyiToastUtil.show(getApplicationContext(), "全屏视频广告获取成功");
                Log.d(ADSuyiDemoConstant.TAG, "onAdReceive...");
            }

            @Override
            public void onVideoCache(ADSuyiFullScreenVodAdInfo adSuyiFullScreenVodAdInfo) {
                Log.d(ADSuyiDemoConstant.TAG, "onVideoCache...");
            }

            @Override
            public void onVideoComplete(ADSuyiFullScreenVodAdInfo adSuyiFullScreenVodAdInfo) {
                Log.d(ADSuyiDemoConstant.TAG, "onVideoComplete...");
            }

            @Override
            public void onVideoError(ADSuyiFullScreenVodAdInfo adSuyiFullScreenVodAdInfo, ADSuyiError adSuyiError) {
                Log.d(ADSuyiDemoConstant.TAG, "onVideoError..." + adSuyiError.toString());
            }

            @Override
            public void onAdExpose(ADSuyiFullScreenVodAdInfo adSuyiFullScreenVodAdInfo) {
                Log.d(ADSuyiDemoConstant.TAG, "onAdExpose...");
            }

            @Override
            public void onAdClick(ADSuyiFullScreenVodAdInfo adSuyiFullScreenVodAdInfo) {
                Log.d(ADSuyiDemoConstant.TAG, "onAdClick...");
            }

            @Override
            public void onAdClose(ADSuyiFullScreenVodAdInfo adSuyiFullScreenVodAdInfo) {
                Log.d(ADSuyiDemoConstant.TAG, "onAdClose...");
            }

            @Override
            public void onAdFailed(ADSuyiError adSuyiError) {
                // ADSuyiToastUtil.show(getApplicationContext(), "广告获取失败");
                if (adSuyiError != null) {
                    String failedJson = adSuyiError.toString();
                    Log.d(ADSuyiDemoConstant.TAG, "onAdFailed..." + failedJson);
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
                ADSuyiAdUtil.showFullScreenAdConvenient(this, fullScreenVodAdInfo);
                break;
            default:
                break;
        }
    }

    /**
     * 加载广告
     */
    private void loadAd() {
        fullScreenVodAd.loadAd(ADSuyiDemoConstant.FULL_SCREEN_VOD_AD_POS_ID);
    }

}
