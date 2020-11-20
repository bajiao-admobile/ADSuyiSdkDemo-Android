package cn.admobiletop.adsuyidemo.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import cn.admobiletop.adsuyi.ad.data.ADSuyiNativeAdInfo;
import cn.admobiletop.adsuyi.ad.data.ADSuyiNativeExpressAdInfo;
import cn.admobiletop.adsuyi.ad.data.ADSuyiNativeFeedAdInfo;
import cn.admobiletop.adsuyi.ad.error.ADSuyiError;
import cn.admobiletop.adsuyi.ad.listener.ADSuyiNativeVideoListener;
import cn.admobiletop.adsuyi.util.ADSuyiAdUtil;
import cn.admobiletop.adsuyi.util.ADSuyiViewUtil;
import cn.admobiletop.adsuyidemo.R;
import cn.admobiletop.adsuyidemo.constant.ADSuyiDemoConstant;
import cn.admobiletop.adsuyidemo.entity.NativeAdSampleData;

/**
 * @author ciba
 * @description 信息流广告Adapter
 * @date 2020/4/1
 */
public class NativeAdAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    /**
     * 普通数据类型（模拟数据）
     */
    private static final int ITEM_VIEW_TYPE_NORMAL_DATA = 0;


    // 以下为三种信息流广告适配类型，模板一种，自渲染两种（没有MediaView）和（包含MediaView）。
    /**
     * 信息流原生广告类型（没有MediaView）
     */
    private static final int ITEM_VIEW_TYPE_NATIVE_AD = 1;
    /**
     * 信息流原生广告类型（包含MediaView）
     */
    private static final int ITEM_VIEW_TYPE_NATIVE_AD_HAS_MEDIA_VIEW = 2;
    /**
     * 信息流模板广告类型
     */
    private static final int ITEM_VIEW_TYPE_EXPRESS_AD = 3;


    private final Context context;

    private List<NativeAdSampleData> dataList = new ArrayList<>();

    public NativeAdAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int itemViewType) {
        switch (itemViewType) {
            case ITEM_VIEW_TYPE_NATIVE_AD:
                return new NativeAdViewHolder(viewGroup);
            case ITEM_VIEW_TYPE_NATIVE_AD_HAS_MEDIA_VIEW:
                return new NativeAdMediaViewHolder(viewGroup);
            case ITEM_VIEW_TYPE_EXPRESS_AD:
                return new NativeExpressAdViewHolder(viewGroup);
            default:
                return new NormalDataViewHolder(viewGroup);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        NativeAdSampleData nativeAdSampleData = dataList.get(position);
        if (viewHolder instanceof NormalDataViewHolder) {
            // 普通数据类型的
            ((NormalDataViewHolder) viewHolder).setData(nativeAdSampleData.getNormalData());
        } else if (viewHolder instanceof BaseNativeAdViewHolder) {
            // 信息流原生广告类型 NativeAdViewHolder or NativeAdMediaViewHolder
            ((BaseNativeAdViewHolder) viewHolder).setData(context, (ADSuyiNativeFeedAdInfo) nativeAdSampleData.getNativeAdInfo());
        } else if (viewHolder instanceof NativeExpressAdViewHolder) {
            // 信息流模板广告类型
            ((NativeExpressAdViewHolder) viewHolder).setData((ADSuyiNativeExpressAdInfo) nativeAdSampleData.getNativeAdInfo());
        }
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    @Override
    public int getItemViewType(int position) {
        ADSuyiNativeAdInfo nativeAdInfo = dataList.get(position).getNativeAdInfo();
        if (nativeAdInfo == null) {
            return ITEM_VIEW_TYPE_NORMAL_DATA;
        } else if (nativeAdInfo.isNativeExpress()) {
            // nativeAdInfo instanceof ADSuyiNativeExpressAdInfo
            // isNativeExpress() true 信息流模板广告
            return ITEM_VIEW_TYPE_EXPRESS_AD;
        } else {
            // nativeAdInfo instanceof ADSuyiNativeFeedAdInfo
            // isNativeExpress() false 信息流原生广告，原生信息流广告还分为包含MediaView和不包含MediaView两种情况
            ADSuyiNativeFeedAdInfo nativeFeedAdInfo = (ADSuyiNativeFeedAdInfo) nativeAdInfo;
            if (nativeFeedAdInfo.hasMediaView()) {
                // hasMediaView() true 包含MediaView的原生信息流广告
                return ITEM_VIEW_TYPE_NATIVE_AD_HAS_MEDIA_VIEW;
            }else {
                // hasMediaView() false 没有MediaView的原生信息流广告
                return ITEM_VIEW_TYPE_NATIVE_AD;
            }
        }
    }

    /**
     * 移除广告所在的对象，一般模板广告有可能会渲染失败
     */
    public void removeData(ADSuyiNativeAdInfo adSuyiNativeAdInfo) {
        for (int i = 0; i < dataList.size(); i++) {
            NativeAdSampleData nativeAdSampleData = dataList.get(i);
            if (nativeAdSampleData.getNativeAdInfo() == adSuyiNativeAdInfo) {
                // 释放广告Info对象
                adSuyiNativeAdInfo.release();
                // 从数据源中移除
                dataList.remove(nativeAdSampleData);
                // 通知刷新Adapter
                notifyDataSetChanged();
                break;
            }
        }
    }

    /**
     * 刷新数据
     */
    public void clearData() {
        dataList.clear();
        notifyDataSetChanged();
    }

    /**
     * 添加数据
     */
    public void addData(List<NativeAdSampleData> nativeAdSampleDataList) {
        int startPosition = dataList.size();
        dataList.addAll(nativeAdSampleDataList);
        if (startPosition <= 0) {
            notifyDataSetChanged();
        } else {
            notifyItemRangeInserted(startPosition + 1, dataList.size() - startPosition);
        }
    }

    /**
     * 普通数据ViewHolder
     */
    private static class NormalDataViewHolder extends RecyclerView.ViewHolder {

        private final TextView tvNormalData;

        NormalDataViewHolder(@NonNull ViewGroup viewGroup) {
            super(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_native_ad_normal_data, viewGroup, false));
            tvNormalData = itemView.findViewById(R.id.tvNormalData);
        }

        public void setData(String normalData) {
            tvNormalData.setText(normalData);
        }
    }

    /**
     * 包含MediaView的ViewHolder
     */
    private static class NativeAdMediaViewHolder extends BaseNativeAdViewHolder {
        private final FrameLayout flMediaContainer;

        NativeAdMediaViewHolder(@NonNull ViewGroup viewGroup) {
            /**
             * 已预设3种样式，开发者可自行修改，关闭按钮大小等可根据产品需求自行修改
             * R.layout.item_native_ad_native_ad_media 单图单视频双文
             * R.layout.item_native_ad_native_ad_media2 上视频下文
             * R.layout.item_native_ad_native_ad_media3 单视频
             */
            super(viewGroup, R.layout.item_native_ad_native_ad_media3);
            flMediaContainer = itemView.findViewById(R.id.flMediaContainer);
        }

        @Override
        protected void setImageOrMediaData(Context context, ADSuyiNativeFeedAdInfo nativeFeedAdInfo) {
            // 当前信息流原生广告，获取的是多媒体视图（可能是视频、或者图片之类的），mediaView不为空时强烈建议进行展示
            View mediaView = nativeFeedAdInfo.getMediaView(flMediaContainer);
            // 将广告视图添加到容器中的便捷方法，mediaView为空会移除flMediaContainer的所有子View
            ADSuyiViewUtil.addAdViewToAdContainer(flMediaContainer, mediaView);
        }
    }

    /**
     * 没有MediaView的ViewHolder
     */
    private static class NativeAdViewHolder extends BaseNativeAdViewHolder {
        private final ImageView ivImage;

        NativeAdViewHolder(@NonNull ViewGroup viewGroup) {
            /**
             * 已预设4种样式，开发者可自行修改，关闭按钮大小等可根据产品需求自行修改
             * R.layout.item_native_ad_native_ad 双图双文
             * R.layout.item_native_ad_native_ad2 上图下文
             * R.layout.item_native_ad_native_ad3 单图
             * R.layout.item_native_ad_native_ad4 左图双文
             */
            super(viewGroup, R.layout.item_native_ad_native_ad2);
            ivImage = itemView.findViewById(R.id.ivImage);
        }

        @Override
        protected void setImageOrMediaData(Context context, ADSuyiNativeFeedAdInfo nativeFeedAdInfo) {
            Glide.with(context).load(nativeFeedAdInfo.getImageUrl()).into(ivImage);
        }
    }

    /**
     * 信息流原生广告BaseViewHolder
     */
    private static abstract class BaseNativeAdViewHolder extends RecyclerView.ViewHolder {

        private final ImageView ivIcon;
        private final RelativeLayout rlAdContainer;
        private final ImageView ivAdTarget;
        private final TextView tvTitle;
        private final TextView tvDesc;
        private final TextView tvAdType;
        private final ImageView ivClose;

        BaseNativeAdViewHolder(@NonNull ViewGroup viewGroup, @LayoutRes int layoutRes) {
            super(LayoutInflater.from(viewGroup.getContext()).inflate(layoutRes, viewGroup, false));
            rlAdContainer = itemView.findViewById(R.id.rlAdContainer);
            ivIcon = itemView.findViewById(R.id.ivIcon);
            ivAdTarget = itemView.findViewById(R.id.ivAdTarget);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvDesc = itemView.findViewById(R.id.tvDesc);
            tvAdType = itemView.findViewById(R.id.tvAdType);
            ivClose = itemView.findViewById(R.id.ivClose);
        }

        void setData(Context context, ADSuyiNativeFeedAdInfo nativeFeedAdInfo) {
            // 判断广告Info对象是否被释放（调用过ADSuyiNativeAd的release()或ADSuyiNativeAdInfo的release()会释放广告Info对象）
            // 释放后的广告Info对象不能再次使用
            if (!ADSuyiAdUtil.adInfoIsRelease(nativeFeedAdInfo)) {
                NativeAdAdapter.setVideoListener(nativeFeedAdInfo);

                // 交由子类实现加载图片还是MediaView
                setImageOrMediaData(context, nativeFeedAdInfo);
                if (ivIcon != null) {
                    // 广告icon
                    Glide.with(context).load(nativeFeedAdInfo.getIconUrl()).into(ivIcon);
                }
                if (tvTitle != null) {
                    // 广告标题
                    tvTitle.setText(nativeFeedAdInfo.getTitle());
                }
                if (tvDesc != null) {
                    // 广告详情
                    tvDesc.setText(nativeFeedAdInfo.getDesc());
                }
                if (tvAdType != null) {
                    // 广告按钮描述
                    tvAdType.setText(nativeFeedAdInfo.getCtaText());
                }
                // 广告平台图标
                ivAdTarget.setImageResource(nativeFeedAdInfo.getPlatformIcon());
                // 注册关闭按钮，将关闭按钮点击事件交于SDK托管，以便于回调onAdClose
                nativeFeedAdInfo.registerCloseView(ivClose);

                // 注册广告交互, 必须调用
                // 注意：广点通只会响应View...actionViews的点击事件，且这些View都应该是com.qq.e.ads.nativ.widget.NativeAdContainer的子View
                // 务必最后调用
                nativeFeedAdInfo.registerViewForInteraction((ViewGroup) itemView, rlAdContainer, tvAdType);
            }
        }

        protected abstract void setImageOrMediaData(Context context, ADSuyiNativeFeedAdInfo nativeFeedAdInfo);
    }

    /**
     * 信息流模板广告ViewHolder
     */
    private static class NativeExpressAdViewHolder extends RecyclerView.ViewHolder {

        NativeExpressAdViewHolder(@NonNull ViewGroup viewGroup) {
            super(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_native_ad_express_ad, viewGroup, false));
        }

        public void setData(ADSuyiNativeExpressAdInfo nativeExpressAdInfo) {
            // 判断广告Info对象是否被释放（调用过ADSuyiNativeAd的release()或ADSuyiNativeAdInfo的release()会释放广告Info对象）
            // 释放后的广告Info对象不能再次使用
            if (!ADSuyiAdUtil.adInfoIsRelease(nativeExpressAdInfo)) {
                NativeAdAdapter.setVideoListener(nativeExpressAdInfo);
                // 当前是信息流模板广告，getNativeExpressAdView获取的是整个模板广告视图
                View nativeExpressAdView = nativeExpressAdInfo.getNativeExpressAdView((ViewGroup) itemView);
                // 将广告视图添加到容器中的便捷方法
                ADSuyiViewUtil.addAdViewToAdContainer((ViewGroup) itemView, nativeExpressAdView);

                // 渲染广告视图, 必须调用, 因为是模板广告, 所以传入ViewGroup和响应点击的控件可能并没有用
                // 务必在最后调用
                nativeExpressAdInfo.render((ViewGroup) itemView);
            }
        }
    }

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
