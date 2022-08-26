package cn.admobiletop.adsuyidemo.adapter.holder;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import cn.admobiletop.adsuyi.ad.data.ADSuyiNativeAdInfo;
import cn.admobiletop.adsuyi.ad.data.ADSuyiNativeFeedAdInfo;
import cn.admobiletop.adsuyi.ad.error.ADSuyiError;
import cn.admobiletop.adsuyi.ad.listener.ADSuyiNativeVideoListener;
import cn.admobiletop.adsuyi.util.ADSuyiAdUtil;
import cn.admobiletop.adsuyidemo.R;
import cn.admobiletop.adsuyidemo.constant.ADSuyiDemoConstant;

/**
 * 信息流原生广告BaseViewHolder
 */
public abstract class BaseNativeFeedAdViewHolder extends RecyclerView.ViewHolder {

    private final ImageView ivIcon;
    private final RelativeLayout rlAdContainer;
    private final ImageView ivAdTarget;
    private final TextView tvTitle;
    private final TextView tvDesc;
    private final ImageView ivClose;

    public BaseNativeFeedAdViewHolder(@NonNull ViewGroup viewGroup, @LayoutRes int layoutRes) {
        super(LayoutInflater.from(viewGroup.getContext()).inflate(layoutRes, viewGroup, false));
        rlAdContainer = itemView.findViewById(R.id.rlAdContainer);
        ivIcon = itemView.findViewById(R.id.ivIcon);
        ivAdTarget = itemView.findViewById(R.id.ivAdTarget);
        tvTitle = itemView.findViewById(R.id.tvTitle);
        tvDesc = itemView.findViewById(R.id.tvDesc);
        ivClose = itemView.findViewById(R.id.ivClose);
    }

    public void setData(ADSuyiNativeFeedAdInfo nativeFeedAdInfo) {
        // 判断广告Info对象是否被释放（调用过ADSuyiNativeAd的release()或ADSuyiNativeAdInfo的release()会释放广告Info对象）
        // 释放后的广告Info对象不能再次使用
        if (!ADSuyiAdUtil.adInfoIsRelease(nativeFeedAdInfo)) {
            setVideoListener(nativeFeedAdInfo);

            // 交由子类实现加载图片还是MediaView
            setImageOrMediaData(itemView.getContext(), nativeFeedAdInfo);
            if (ivIcon != null) {
                // 广告icon
                Glide.with(ivIcon).load(nativeFeedAdInfo.getIconUrl()).into(ivIcon);
            }
            if (tvTitle != null) {
                // 广告标题
                tvTitle.setText(nativeFeedAdInfo.getTitle());
            }
            if (tvDesc != null) {
                // 广告详情
                tvDesc.setText(nativeFeedAdInfo.getDesc());
            }
            // 广告平台logo图标
            ivAdTarget.setImageResource(nativeFeedAdInfo.getPlatformIcon());
            // 注册关闭按钮，将关闭按钮点击事件交于SDK托管，以便于回调onAdClose
            nativeFeedAdInfo.registerCloseView(ivClose);

            // 注册广告交互, 必须调用
            // 注意：优量汇只会响应View...actionViews的点击事件，且这些View都应该是com.qq.e.ads.nativ.widget.NativeAdContainer的子View
            // 务必最后调用
            nativeFeedAdInfo.registerViewForInteraction((ViewGroup) itemView, rlAdContainer);
        }
    }

    protected abstract void setImageOrMediaData(Context context, ADSuyiNativeFeedAdInfo nativeFeedAdInfo);

    /**
     * 设置视频监听，无需求可不设置，视频监听回调因平台差异会有所不一，如：某些平台可能没有完成回调等
     */
    private static void setVideoListener(ADSuyiNativeAdInfo nativeAdInfo) {
        if (nativeAdInfo.isVideo()) {
            // 设置视频监听，监听回调因三方平台SDK差异有所差异，无需要可不设置
            nativeAdInfo.setVideoListener(new ADSuyiNativeVideoListener() {
                @Override
                public void onVideoLoad(ADSuyiNativeAdInfo adSuyiNativeAdInfo) {
                    Log.d(ADSuyiDemoConstant.TAG, "onVideoLoad.... ");
                }

                @Override
                public void onVideoStart(ADSuyiNativeAdInfo adSuyiNativeAdInfo) {
                    Log.d(ADSuyiDemoConstant.TAG, "onVideoStart.... ");
                }

                @Override
                public void onVideoPause(ADSuyiNativeAdInfo adSuyiNativeAdInfo) {
                    Log.d(ADSuyiDemoConstant.TAG, "onVideoPause.... ");
                }

                @Override
                public void onVideoComplete(ADSuyiNativeAdInfo adSuyiNativeAdInfo) {
                    Log.d(ADSuyiDemoConstant.TAG, "onVideoComplete.... ");
                }

                @Override
                public void onVideoError(ADSuyiNativeAdInfo adSuyiNativeAdInfo, ADSuyiError adSuyiError) {
                    Log.d(ADSuyiDemoConstant.TAG, "onVideoError.... " + adSuyiError.toString());
                }
            });
        }
    }
}
