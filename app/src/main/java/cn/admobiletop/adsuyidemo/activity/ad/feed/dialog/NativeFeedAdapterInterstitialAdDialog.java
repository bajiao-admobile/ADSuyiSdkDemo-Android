package cn.admobiletop.adsuyidemo.activity.ad.feed.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.qq.e.ads.nativ.widget.NativeAdContainer;

import cn.admobiletop.adsuyi.ad.data.ADSuyiNativeAdInfo;
import cn.admobiletop.adsuyi.ad.data.ADSuyiNativeFeedAdInfo;
import cn.admobiletop.adsuyi.util.ADSuyiAdUtil;
import cn.admobiletop.adsuyi.util.ADSuyiViewUtil;
import cn.admobiletop.adsuyidemo.R;

/**
 * @author maipian
 * @description admobileDl自渲染广告弹出框
 * @date 2020/10/21
 */
public class NativeFeedAdapterInterstitialAdDialog extends BaseNativeInterstitialDialog {

    private NativeAdContainer nativeAdContainer;
    private RelativeLayout rlAdContainer;
    private FrameLayout flContainer;
    private TextView tvClose;
    private ImageView ivAdTarget;

    public NativeFeedAdapterInterstitialAdDialog(@NonNull Context context) {
        super(context);
        setContentView(R.layout.dialog_feed_native_adapter_interstitial_ad);
        nativeAdContainer = findViewById(R.id.nativeAdContainer);
        rlAdContainer = findViewById(R.id.rlAdContainer);
        flContainer = findViewById(R.id.flContainer);
        tvClose = findViewById(R.id.tvClose);
        ivAdTarget = findViewById(R.id.ivAdTarget);
        // 设置点击外部不关闭dialog
        setCanceledOnTouchOutside(false);
        Window dialogWindow = getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        dialogWindow.setAttributes(lp);
    }

    @Override
    public void render(ADSuyiNativeAdInfo adSuyiNativeAdInfo) {
        super.render(adSuyiNativeAdInfo);
        if(adSuyiNativeAdInfo instanceof ADSuyiNativeFeedAdInfo) {
            ADSuyiNativeFeedAdInfo nativeFeedAdInfo = (ADSuyiNativeFeedAdInfo) adSuyiNativeAdInfo;
            if (nativeFeedAdInfo == null) {
                Log.d(TAG, "ADSuyiNativeFeedAdInfo is null");
                return;
            }
            // 判断广告Info对象是否被释放（调用过ADSuyiNativeAd的release()或ADSuyiNativeAdInfo的release()会释放广告Info对象）
            // 释放后的广告Info对象不能再次使用
            if (ADSuyiAdUtil.adInfoIsRelease(nativeFeedAdInfo)) {
                Log.d(TAG, "ADSuyiNativeFeedAdInfo 广告对象已被释放");
                return;
            }
            if (nativeFeedAdInfo.hasMediaView()) {
                // 当前信息流原生广告，获取的是多媒体视图（可能是视频、或者图片之类的），mediaView不为空时强烈建议进行展示
                View mediaView = nativeFeedAdInfo.getMediaView(flContainer);
                // 将广告视图添加到容器中的便捷方法，mediaView为空会移除flMediaContainer的所有子View
                ADSuyiViewUtil.addAdViewToAdContainer(flContainer, mediaView);
            } else {
                ImageView imageView = new ImageView(flContainer.getContext());
                Glide.with(imageView.getContext()).load(nativeFeedAdInfo.getImageUrl()).into(imageView);
                ADSuyiViewUtil.addAdViewToAdContainer(flContainer, imageView );
            }
            ivAdTarget.setImageResource(nativeFeedAdInfo.getPlatformIcon());
            // 注册关闭按钮，将关闭按钮点击事件交于SDK托管，以便于回调onAdClose
            nativeFeedAdInfo.registerCloseView(tvClose);
            // 注册广告交互, 必须调用
            // 务必最后调用
            nativeFeedAdInfo.registerViewForInteraction(nativeAdContainer, rlAdContainer);
            show();
        } else {
            Log.d(TAG, "ADSuyiNativeFeedAdInfo 广告对象类型异常");
        }
    }
}
