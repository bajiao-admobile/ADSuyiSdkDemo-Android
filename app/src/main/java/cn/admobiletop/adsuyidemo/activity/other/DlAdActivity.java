package cn.admobiletop.adsuyidemo.activity.other;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import cn.admobiletop.adsuyi.ad.ADSuyiNativeAd;
import cn.admobiletop.adsuyi.ad.data.ADSuyiNativeAdInfo;
import cn.admobiletop.adsuyi.ad.entity.ADSuyiAdSize;
import cn.admobiletop.adsuyi.ad.entity.ADSuyiExtraParams;
import cn.admobiletop.adsuyi.ad.error.ADSuyiError;
import cn.admobiletop.adsuyi.ad.listener.ADSuyiNativeAdListener;
import cn.admobiletop.adsuyi.util.ADSuyiToastUtil;
import cn.admobiletop.adsuyidemo.R;
import cn.admobiletop.adsuyidemo.activity.base.BaseAdActivity;
import cn.admobiletop.adsuyidemo.constant.ADSuyiDemoConstant;
import cn.admobiletop.adsuyidemo.widget.AdMobileDlExpressAdDialog;
import cn.admobiletop.adsuyidemo.widget.AdMobileDlFeedAdDialog;

/**
 * @author 草莓
 * @description Dl广告示例
 * @date 2020/10/21
 */
public class DlAdActivity extends BaseAdActivity implements View.OnClickListener {

    private ADSuyiNativeAd adSuyiNativeAd;
    private AdMobileDlFeedAdDialog adMobileDlAdDialog;
    private AdMobileDlExpressAdDialog adMobileDlExpressAdDialog;
    private TextView tvDesc;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dl_normal);
        initListener();
        initAd();
    }

    private void initListener() {
        Button btnLoadAd = findViewById(R.id.btnLoadAd);
        tvDesc = findViewById(R.id.tvDesc);
        btnLoadAd.setOnClickListener(this);
    }

    private void initAd() {
        dialogDismiss();
        // 模版广告容器宽度
        int widthPixels = getResources().getDisplayMetrics().widthPixels;
        // 创建Dl广告实例 (注意dl广告用法与信息流广告相似，但是需要申请dl相应广告位，并非使用信息流的广告位)
        adSuyiNativeAd = new ADSuyiNativeAd(this);
        // 创建额外参数实例
        ADSuyiExtraParams extraParams = new ADSuyiExtraParams.Builder()
                // 设置整个广告视图预期宽高，单位为px，高度如果小于等于0则高度自适应
                .adSize(new ADSuyiAdSize(widthPixels, 0))
                .build();
        // 设置一些额外参数，有些平台的广告可能需要传入一些额外参数，如果有接入头条、Inmobi平台，如果包含这些平台该参数必须设置
        adSuyiNativeAd.setLocalExtraParams(extraParams);
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
                    ADSuyiToastUtil.show(getApplicationContext(), "广告获取成功");
                    // 判断是否为自渲染广告
                    if (adInfoList.get(0).isNativeExpress()) {
                        adMobileDlExpressAdDialog = new AdMobileDlExpressAdDialog(DlAdActivity.this);
                        adMobileDlExpressAdDialog.render(adInfoList.get(0));
                    } else {
                        adMobileDlAdDialog = new AdMobileDlFeedAdDialog(DlAdActivity.this);
                        adMobileDlAdDialog.render(adInfoList.get(0));
                    }
                    tvDesc.append("\n\n获取DL广告成功");
                }
            }

            @Override
            public void onAdExpose(ADSuyiNativeAdInfo adSuyiNativeAdInfo) {
                tvDesc.append("\n\nDL广告开始展示");
                Log.d(ADSuyiDemoConstant.TAG, "onAdExpose: " + adSuyiNativeAdInfo.hashCode());
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
                dialogDismiss();
                tvDesc.append("\n\nDL广告被关闭");
            }

            @Override
            public void onAdFailed(ADSuyiError adSuyiError) {
                if (adSuyiError != null) {
                    Log.d(ADSuyiDemoConstant.TAG, "onAdFailed: " + adSuyiError.toString());
                }
                dialogDismiss();
                tvDesc.append("\n\n获取DL广告失败");


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
        tvDesc.setText("开始获取DL广告");
        adSuyiNativeAd.loadAd(ADSuyiDemoConstant.ADMOBILE_DL_AD_POS_ID);
    }

    private void dialogDismiss() {
        if (adMobileDlAdDialog != null) {
            adMobileDlAdDialog.dismiss();
            adMobileDlAdDialog = null;
        }
        if (adMobileDlExpressAdDialog != null) {
            adMobileDlExpressAdDialog.dismiss();
            adMobileDlExpressAdDialog = null;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dialogDismiss();
    }
}
