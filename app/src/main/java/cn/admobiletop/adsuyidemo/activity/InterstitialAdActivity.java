package cn.admobiletop.adsuyidemo.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.List;

import cn.admobiletop.adsuyi.ad.ADSuyiInterstitialAd;
import cn.admobiletop.adsuyi.ad.data.ADSuyiInterstitialAdInfo;
import cn.admobiletop.adsuyi.ad.error.ADSuyiError;
import cn.admobiletop.adsuyi.ad.listener.ADSuyiInterstitialAdListener;
import cn.admobiletop.adsuyi.util.ADSuyiAdUtil;
import cn.admobiletop.adsuyi.util.ADSuyiToastUtil;
import cn.admobiletop.adsuyidemo.R;
import cn.admobiletop.adsuyidemo.constant.ADSuyiDemoConstant;

/**
 * @author ciba
 * @description 插屏广告示例
 * @date 2020/3/27
 */
public class InterstitialAdActivity extends AppCompatActivity implements View.OnClickListener {
    private ADSuyiInterstitialAd interstitialAd;
    private ADSuyiInterstitialAdInfo interstitialAdInfo;

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

        btnLoadAd.setText("获取插屏广告");
        btnShowAd.setText("展示插屏广告");

        btnLoadAd.setOnClickListener(this);
        btnShowAd.setOnClickListener(this);
    }

    private void initAd() {
        interstitialAd = new ADSuyiInterstitialAd(this);
        // 设置仅支持的广告平台，设置了这个值，获取广告时只会去获取该平台的广告，null或空字符串为不限制，默认为null
        interstitialAd.setOnlySupportPlatform(ADSuyiDemoConstant.INTERSTITIAL_AD_ONLY_SUPPORT_PLATFORM);
        // 设置插屏广告监听
        interstitialAd.setListener(new ADSuyiInterstitialAdListener() {
            @Override
            public void onAdReceive(List<ADSuyiInterstitialAdInfo> adList) {
                interstitialAdInfo = adList.get(0);
                ADSuyiToastUtil.show(getApplicationContext(), "插屏广告获取成功");
                Log.d(ADSuyiDemoConstant.TAG, "onAdReceive----->" + interstitialAdInfo.hashCode());
            }

            @Override
            public void onAdExpose(ADSuyiInterstitialAdInfo interstitialAdInfo) {
                Log.d(ADSuyiDemoConstant.TAG, "onAdExpose----->" + interstitialAdInfo.hashCode());
            }

            @Override
            public void onAdClick(ADSuyiInterstitialAdInfo interstitialAdInfo) {
                Log.d(ADSuyiDemoConstant.TAG, "onAdClick----->" + interstitialAdInfo.hashCode());
            }

            @Override
            public void onAdClose(ADSuyiInterstitialAdInfo interstitialAdInfo) {
                Log.d(ADSuyiDemoConstant.TAG, "onAdClose----->" + interstitialAdInfo.hashCode());
            }

            @Override
            public void onAdFailed(ADSuyiError adSuyiError) {
                if (adSuyiError != null) {
                    String failedJson = adSuyiError.toString();
                    Log.d(ADSuyiDemoConstant.TAG, "onAdFailed----->" + failedJson);
                    ADSuyiToastUtil.show(getApplicationContext(), "广告获取失败" + failedJson);
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
                ADSuyiAdUtil.showInterstitialAdConvenient(this, interstitialAdInfo);
                break;
            default:
                break;
        }
    }

    /**
     * 加载广告
     */
    private void loadAd() {
        interstitialAd.loadAd(ADSuyiDemoConstant.INTERSTITIAL_AD_POS_ID);
    }

}
