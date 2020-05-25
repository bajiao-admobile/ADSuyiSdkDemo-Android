package cn.admobiletop.adsuyidemo.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.FrameLayout;

import cn.admobiletop.adsuyi.ad.ADSuyiBannerAd;
import cn.admobiletop.adsuyi.ad.data.ADSuyiAdInfo;
import cn.admobiletop.adsuyi.ad.error.ADSuyiError;
import cn.admobiletop.adsuyi.ad.listener.ADSuyiBannerAdListener;
import cn.admobiletop.adsuyi.util.ADSuyiToastUtil;
import cn.admobiletop.adsuyidemo.R;
import cn.admobiletop.adsuyidemo.constant.ADSuyiDemoConstant;

/**
 * @author ciba
 * @description Banner广告示例
 * @date 2020/3/26
 */
public class BannerAdActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banner);
        FrameLayout flContainer = findViewById(R.id.flContainer);

        // 创建Banner广告实例，第一个参数可以是Activity或Fragment，第二个参数是广告容器（请保证容器不会拦截点击、触摸等事件）
        ADSuyiBannerAd suyiBannerAd = new ADSuyiBannerAd(this, flContainer);
        // 设置自刷新时间间隔，0为不自动刷新（部分平台无效，如百度），其他取值范围为[30,120]，单位秒
        suyiBannerAd.setAutoRefreshInterval(ADSuyiDemoConstant.BANNER_AD_AUTO_REFRESH_INTERVAL);
        // 设置仅支持的广告平台，设置了这个值，获取广告时只会去获取该平台的广告，null或空字符串为不限制，默认为null，方便调试使用，上线时建议不设置
        suyiBannerAd.setOnlySupportPlatform(ADSuyiDemoConstant.BANNER_AD_ONLY_SUPPORT_PLATFORM);
        // 设置Banner广告监听
        suyiBannerAd.setListener(new ADSuyiBannerAdListener() {
            @Override
            public void onAdReceive(ADSuyiAdInfo adSuyiAdInfo) {
                Log.d(ADSuyiDemoConstant.TAG, "onAdReceive...");
            }

            @Override
            public void onAdExpose(ADSuyiAdInfo adSuyiAdInfo) {
                Log.d(ADSuyiDemoConstant.TAG, "onAdExpose...");
            }

            @Override
            public void onAdClick(ADSuyiAdInfo adSuyiAdInfo) {
                Log.d(ADSuyiDemoConstant.TAG, "onAdClick...");
            }

            @Override
            public void onAdClose(ADSuyiAdInfo adSuyiAdInfo) {
                Log.d(ADSuyiDemoConstant.TAG, "onAdClose...");
            }

            @Override
            public void onAdFailed(ADSuyiError adSuyiError) {
                if (adSuyiError != null) {
                    String failedJson = adSuyiError.toString();
                    Log.d(ADSuyiDemoConstant.TAG, "onAdFailed..." + failedJson);
                    ADSuyiToastUtil.show(getApplicationContext(), "广告获取失败" + failedJson);
                }
            }
        });
        // 加载Banner广告，参数为广告位ID，同一个ADSuyiBannerAd只有一次loadAd有效
        suyiBannerAd.loadAd(ADSuyiDemoConstant.BANNER_AD_POS_ID);
    }
}
