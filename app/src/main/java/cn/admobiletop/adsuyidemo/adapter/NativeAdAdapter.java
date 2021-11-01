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
import cn.admobiletop.adsuyidemo.adapter.holder.BaseNativeFeedAdViewHolder;
import cn.admobiletop.adsuyidemo.adapter.holder.NativeExpressAdViewHolder;
import cn.admobiletop.adsuyidemo.adapter.holder.NativeFeedAdMediaViewHolder;
import cn.admobiletop.adsuyidemo.adapter.holder.NativeFeedAdViewHolder;
import cn.admobiletop.adsuyidemo.adapter.holder.NormalDataViewHolder;
import cn.admobiletop.adsuyidemo.constant.ADSuyiDemoConstant;
import cn.admobiletop.adsuyidemo.entity.NativeAdSampleData;

/**
 * @author ciba
 * @description 信息流广告Adapter
 * @date 2020/4/1
 */
public class NativeAdAdapter extends BaseNativeAdAdapter {
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

    private List<Object> dataList = new ArrayList<>();

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int itemViewType) {
        switch (itemViewType) {
            case ITEM_VIEW_TYPE_NATIVE_AD:
                return new NativeFeedAdViewHolder(viewGroup);
            case ITEM_VIEW_TYPE_NATIVE_AD_HAS_MEDIA_VIEW:
                return new NativeFeedAdMediaViewHolder(viewGroup);
            case ITEM_VIEW_TYPE_EXPRESS_AD:
                return new NativeExpressAdViewHolder(viewGroup);
            default:
                return new NormalDataViewHolder(viewGroup);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        Object item = dataList.get(position);
        if (viewHolder instanceof NormalDataViewHolder) {
            // 普通数据类型的
            ((NormalDataViewHolder) viewHolder).setData((String) item);
        } else if (viewHolder instanceof BaseNativeFeedAdViewHolder) {
            // 信息流原生广告类型 NativeAdViewHolder or NativeAdMediaViewHolder
            ((BaseNativeFeedAdViewHolder) viewHolder).setData((ADSuyiNativeFeedAdInfo) item);
        } else if (viewHolder instanceof NativeExpressAdViewHolder) {
            // 信息流模板广告类型
            ((NativeExpressAdViewHolder) viewHolder).setData((ADSuyiNativeExpressAdInfo) item);
        }
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    @Override
    public int getItemViewType(int position) {
        Object item = dataList.get(position);
        if (item instanceof String) {
            return ITEM_VIEW_TYPE_NORMAL_DATA;
        } else if (item instanceof ADSuyiNativeExpressAdInfo) {
            return ITEM_VIEW_TYPE_EXPRESS_AD;
        } else {
            // isNativeExpress() false 信息流原生广告，原生信息流广告还分为包含MediaView和不包含MediaView两种情况
            ADSuyiNativeFeedAdInfo nativeFeedAdInfo = (ADSuyiNativeFeedAdInfo) item;
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
    @Override
    public void removeData(ADSuyiNativeAdInfo adSuyiNativeAdInfo) {
        if (dataList.contains(adSuyiNativeAdInfo)) {
            // 释放广告Info对象
            adSuyiNativeAdInfo.release();
            // 从数据源中移除
            dataList.remove(adSuyiNativeAdInfo);
            // 通知刷新Adapter
            notifyDataSetChanged();
        }
    }

    /**
     * 刷新数据
     */
    @Override
    public void clearData() {
        dataList.clear();
        notifyDataSetChanged();
    }

    /**
     * 添加数据
     */
    @Override
    public void addData(List<Object> datas) {
        int startPosition = dataList.size();
        dataList.addAll(datas);
        if (startPosition <= 0) {
            notifyDataSetChanged();
        } else {
            notifyItemRangeInserted(startPosition + 1, dataList.size() - startPosition);
        }
    }

}
