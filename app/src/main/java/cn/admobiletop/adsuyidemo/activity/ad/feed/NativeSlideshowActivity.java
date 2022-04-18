package cn.admobiletop.adsuyidemo.activity.ad.feed;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

import com.zhengsr.viewpagerlib.callback.PageHelperListener;
import com.zhengsr.viewpagerlib.indicator.CircleIndicator;
import com.zhengsr.viewpagerlib.view.BannerViewPager;

import java.util.ArrayList;
import java.util.List;

import cn.admobiletop.adsuyi.ad.ADSuyiNativeAd;
import cn.admobiletop.adsuyi.ad.data.ADSuyiNativeAdInfo;
import cn.admobiletop.adsuyi.ad.data.ADSuyiNativeExpressAdInfo;
import cn.admobiletop.adsuyi.ad.entity.ADSuyiAdSize;
import cn.admobiletop.adsuyi.ad.entity.ADSuyiExtraParams;
import cn.admobiletop.adsuyi.ad.error.ADSuyiError;
import cn.admobiletop.adsuyi.ad.listener.ADSuyiNativeAdListener;
import cn.admobiletop.adsuyi.util.ADSuyiAdUtil;
import cn.admobiletop.adsuyi.util.ADSuyiViewUtil;
import cn.admobiletop.adsuyidemo.R;
import cn.admobiletop.adsuyidemo.constant.ADSuyiDemoConstant;

/**
 * @author : maipian
 * @date : 2021/12/06
 * @description : 将信息流模版广告放到轮播控件的案例
 * 第三方轮播控件 : https://github.com/LillteZheng/ViewPagerHelper/blob/master/README_Banner.md
 */
public class NativeSlideshowActivity extends AppCompatActivity {

    private BannerViewPager bannerViewPager;
    private CircleIndicator circleIndicator;

    private ADSuyiNativeAd adSuyiNativeAd;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_native_slideshow_ad);

        initView();
        initListener();
        initData();
    }

    private void initView() {
        bannerViewPager = findViewById(R.id.bannerViewPager);
        circleIndicator = findViewById(R.id.circleIndicator);
        bannerViewPager.addIndicator(circleIndicator);
    }

    private void initListener() {

    }

    private void initData() {
        // 模版广告容器宽度
        int widthPixels = getResources().getDisplayMetrics().widthPixels;
        // 创建信息流广告实例
        adSuyiNativeAd = new ADSuyiNativeAd(this);
        // 创建额外参数实例
        ADSuyiExtraParams extraParams = new ADSuyiExtraParams.Builder()
                // 设置整个广告视图预期宽高(目前仅广点通、头条、艾狄墨搏平台需要，没有接入广点通、头条、艾狄墨搏可不设置)，单位为px，高度如果小于等于0则高度自适应
                .adSize(new ADSuyiAdSize(widthPixels, 0))
                // 设置广告视图中MediaView的预期宽高(目前仅Inmobi平台需要,Inmobi的MediaView高度为自适应，没有接入Inmobi平台可不设置)，单位为px
                .nativeAdMediaViewSize(new ADSuyiAdSize((int) (widthPixels - 24 * getResources().getDisplayMetrics().density)))
                // 设置信息流广告适配播放是否静音，默认静音，目前广点通、百度、汇量、快手、Admobile支持修改
                .nativeAdPlayWithMute(ADSuyiDemoConstant.NATIVE_AD_PLAY_WITH_MUTE)
                .build();
        // 设置一些额外参数，有些平台的广告可能需要传入一些额外参数，如果有接入头条、Inmobi平台，如果包含这些平台该参数必须设置
        adSuyiNativeAd.setLocalExtraParams(extraParams);

        // 设置仅支持的广告平台，设置了这个值，获取广告时只会去获取该平台的广告，null或空字符串为不限制，默认为null，方便调试使用，上线时建议不设置
        adSuyiNativeAd.setOnlySupportPlatform(ADSuyiDemoConstant.NATIVE_AD_ONLY_SUPPORT_PLATFORM);
        // 设置广告监听
        adSuyiNativeAd.setListener(new ADSuyiNativeAdListener() {
            @Override
            public void onRenderFailed(ADSuyiNativeAdInfo adSuyiNativeAdInfo, ADSuyiError adSuyiError) {
                Log.d(ADSuyiDemoConstant.TAG, "onRenderFailed: " + adSuyiError.toString());
                // 广告渲染失败，释放和移除ADSuyiNativeAdInfo
            }

            @Override
            public void onAdReceive(List<ADSuyiNativeAdInfo> adInfoList) {
                Log.d(ADSuyiDemoConstant.TAG, "onAdReceive: " + adInfoList.size());
                List<ADSuyiNativeExpressAdInfo> datas = new ArrayList<>();
                for (int i = 0; i < adInfoList.size(); i++) {
                    ADSuyiNativeAdInfo nativeAdInfo = adInfoList.get(i);
                    if (nativeAdInfo.isNativeExpress()) {
                        datas.add((ADSuyiNativeExpressAdInfo) nativeAdInfo);
                    }
                }
                if (datas != null && datas.size() > 0) {
                    initBanner(datas);
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
                // 广告被关闭，释放和移除ADSuyiNativeAdInfo
                // 注意，信息流广告点击关闭时，开发者需要在onAdClose回调中将广告容器隐藏或移除，避免如头条渠道点击关闭后视图依旧存在
            }

            @Override
            public void onAdFailed(ADSuyiError adSuyiError) {
                if (adSuyiError != null) {
                    Log.d(ADSuyiDemoConstant.TAG, "onAdFailed: " + adSuyiError.toString());
                }
            }
        });

        adSuyiNativeAd.loadAd(ADSuyiDemoConstant.NATIVE_AD_POS_ID4, 3);
    }

    private void initBanner(List<ADSuyiNativeExpressAdInfo> datas) {
        /**
         * 设置监听即可，loop_layout 为要展示的内容，比如一个 ImageView，或者参考示例
         * 其中，setText 为模板方法，为了简便代码，当然还有其他一些方法，可查阅 PageHelperListener
         * onItemClick 为点击事件，当然还有其他方法，重写即可，比如子控件事件 onItemChildClick，如果有子控件
         * 的点击事件，需要先在 bindView 中注册，比如 addChildrenClick(view,R.id.item_text,position)，
         * 其他一些方法，可查阅 PageHelperListener
         */
        bannerViewPager.setPageListener(R.layout.item_native_ad_express_ad, datas, new PageHelperListener<ADSuyiNativeExpressAdInfo>() {
            @Override
            public void bindView(View view, final ADSuyiNativeExpressAdInfo nativeExpressAdInfo, int position) {
                RelativeLayout rlAdContainer = view.findViewById(R.id.rlAdContainer);
                if (!ADSuyiAdUtil.adInfoIsRelease(nativeExpressAdInfo)) {
                    // 当前是信息流模板广告，getNativeExpressAdView获取的是整个模板广告视图
                    View nativeExpressAdView = nativeExpressAdInfo.getNativeExpressAdView(rlAdContainer);
                    // 将广告视图添加到容器中的便捷方法
                    ADSuyiViewUtil.addAdViewToAdContainer(rlAdContainer, nativeExpressAdView);

                    // 渲染广告视图, 必须调用, 因为是模板广告, 所以传入ViewGroup和响应点击的控件可能并没有用
                    // 务必在最后调用
                    nativeExpressAdInfo.render(rlAdContainer);
                }
            }

            @Override
            public void onItemClick(View view, ADSuyiNativeExpressAdInfo data, int position) {
                super.onItemClick(view, data, position);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
