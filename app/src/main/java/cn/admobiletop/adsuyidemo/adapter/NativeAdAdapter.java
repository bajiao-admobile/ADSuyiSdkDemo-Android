package cn.admobiletop.adsuyidemo.adapter;

import android.content.Context;
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
     * 普通数据类型
     */
    private static final int ITEM_VIEW_TYPE_NORMAL_DATA = 0;
    /**
     * 信息流原生广告类型
     */
    private static final int ITEM_VIEW_TYPE_NATIVE_AD = 1;
    /**
     * 信息流模板广告类型
     */
    private static final int ITEM_VIEW_TYPE_EXPRESS_AD = 2;
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
            ((NormalDataViewHolder) viewHolder).setData(nativeAdSampleData.getNormalData());
        } else if (viewHolder instanceof NativeAdViewHolder) {
            ((NativeAdViewHolder) viewHolder).setData(context, nativeAdSampleData.getNativeAdInfo());
        } else if (viewHolder instanceof NativeExpressAdViewHolder) {
            ((NativeExpressAdViewHolder) viewHolder).setData(nativeAdSampleData.getNativeAdInfo());
        }
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    @Override
    public int getItemViewType(int position) {
        NativeAdSampleData nativeAdSampleData = dataList.get(position);
        if (nativeAdSampleData.getNativeAdInfo() == null) {
            return ITEM_VIEW_TYPE_NORMAL_DATA;
        } else if (nativeAdSampleData.getNativeAdInfo().isNativeExpress()) {
            return ITEM_VIEW_TYPE_EXPRESS_AD;
        } else {
            return ITEM_VIEW_TYPE_NATIVE_AD;
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

        public NormalDataViewHolder(@NonNull ViewGroup viewGroup) {
            super(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_native_ad_normal_data, viewGroup, false));
            tvNormalData = itemView.findViewById(R.id.tvNormalData);
        }

        public void setData(String normalData) {
            tvNormalData.setText(normalData);
        }
    }

    /**
     * 信息流原生广告ViewHolder
     */
    private static class NativeAdViewHolder extends RecyclerView.ViewHolder {

        private final ImageView ivImage;
        private final ImageView ivIcon;
        private final FrameLayout flMediaContainer;
        private final RelativeLayout rlAdContainer;
        private final ImageView ivAdTarget;
        private final TextView tvTitle;
        private final TextView tvDesc;
        private final TextView tvAdType;
        private final ImageView ivClose;

        public NativeAdViewHolder(@NonNull ViewGroup viewGroup) {
            super(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_native_ad_native_ad, viewGroup, false));
            ivImage = itemView.findViewById(R.id.ivImage);
            rlAdContainer = itemView.findViewById(R.id.rlAdContainer);
            ivIcon = itemView.findViewById(R.id.ivIcon);
            flMediaContainer = itemView.findViewById(R.id.flMediaContainer);
            ivAdTarget = itemView.findViewById(R.id.ivAdTarget);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvDesc = itemView.findViewById(R.id.tvDesc);
            tvAdType = itemView.findViewById(R.id.tvAdType);
            ivClose = itemView.findViewById(R.id.ivClose);
        }

        public void setData(Context context, ADSuyiNativeAdInfo nativeAdInfo) {
            // 判断广告Info对象是否被释放（调用过ADSuyiNativeAd的release()或ADSuyiNativeAdInfo的release()会释放广告Info对象）
            // 释放后的广告Info对象不能再次使用
            if (!ADSuyiAdUtil.adInfoIsRelease(nativeAdInfo)) {
                NativeAdAdapter.setVideoListener(nativeAdInfo);

                // 当前信息流原生广告，获取的是多媒体视图（可能是视频、或者图片之类的）
                View mediaView = nativeAdInfo.getMediaView(flMediaContainer);
                // 将广告视图添加到容器中的便捷方法
                ADSuyiViewUtil.addAdViewToAdContainer(flMediaContainer, mediaView);

                Glide.with(context).load(nativeAdInfo.getIconUrl()).into(ivIcon);
                Glide.with(context).load(nativeAdInfo.getImageUrl()).into(ivImage);
                ivAdTarget.setImageResource(nativeAdInfo.getPlatformIcon(true));
                tvTitle.setText(nativeAdInfo.getTitle());
                tvDesc.setText(nativeAdInfo.getDesc());
                tvAdType.setText(nativeAdInfo.getCtaText());

                ivImage.setVisibility(mediaView == null ? View.VISIBLE : View.GONE);

                // 广点通的广告容器会自带角标，如果不希望出现两个角标，可以通过平台判断来隐藏自己设置的角标
                boolean isGdtPlatform = cn.admobiletop.adsuyi.adapter.gdt.ADSuyiIniter.PLATFORM.equals(nativeAdInfo.getPlatform());
                ivAdTarget.setVisibility(isGdtPlatform ? View.GONE : View.VISIBLE);

                // 注册关闭按钮，将关闭按钮点击事件交于SDK托管，以便于回调onAdClose
                nativeAdInfo.registerCloseView(ivClose);

                // 注册广告交互, 必须调用，注意：广点通只会响应View...actionViews的点击事件，且这些View都应该是com.qq.e.ads.nativ.widget.NativeAdContainer的子View
                nativeAdInfo.registerViewForInteraction((ViewGroup) itemView, rlAdContainer, tvAdType);
            }
        }
    }

    /**
     * 信息流模板广告ViewHolder
     */
    private static class NativeExpressAdViewHolder extends RecyclerView.ViewHolder {

        public NativeExpressAdViewHolder(@NonNull ViewGroup viewGroup) {
            super(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_native_ad_express_ad, viewGroup, false));
        }

        public void setData(ADSuyiNativeAdInfo nativeAdInfo) {
            // 判断广告Info对象是否被释放（调用过ADSuyiNativeAd的release()或ADSuyiNativeAdInfo的release()会释放广告Info对象）
            // 释放后的广告Info对象不能再次使用
            if (!ADSuyiAdUtil.adInfoIsRelease(nativeAdInfo)) {
                NativeAdAdapter.setVideoListener(nativeAdInfo);
                // 当前是信息流模板广告，getMediaView获取的是模板广告视图
                View mediaView = nativeAdInfo.getMediaView((ViewGroup) itemView);
                // 将广告视图添加到容器中的便捷方法
                ADSuyiViewUtil.addAdViewToAdContainer((ViewGroup) itemView, mediaView);
                // 渲染广告视图, 必须调用, 因为是模板广告, 所以传入ViewGroup和响应点击的控件可能并没有用
                nativeAdInfo.renderNativeExpress((ViewGroup) itemView);
            }
        }
    }

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
