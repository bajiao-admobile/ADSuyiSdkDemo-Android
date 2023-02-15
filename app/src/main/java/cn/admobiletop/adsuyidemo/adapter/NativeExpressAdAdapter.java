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
import cn.admobiletop.adsuyidemo.adapter.holder.NativeExpressAdViewHolder;
import cn.admobiletop.adsuyidemo.adapter.holder.NormalDataViewHolder;
import cn.admobiletop.adsuyidemo.constant.ADSuyiDemoConstant;
import cn.admobiletop.adsuyidemo.entity.NativeAdSampleData;

/**
 * @author ciba
 * @description 信息流模板广告Adapter
 * @date 2020/4/1
 */
public class NativeExpressAdAdapter extends BaseNativeAdAdapter {
    /**
     * 普通数据类型（模拟数据）
     */
    private static final int ITEM_VIEW_TYPE_NORMAL_DATA = 0;
    /**
     * 信息流模板广告类型
     */
    private static final int ITEM_VIEW_TYPE_EXPRESS_AD = 3;

    private List<Object> dataList = new ArrayList<>();

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int itemViewType) {
        switch (itemViewType) {
            case ITEM_VIEW_TYPE_EXPRESS_AD:
                return new NativeExpressAdViewHolder(viewGroup);
            default:
                return new NormalDataViewHolder(viewGroup);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        Object item = dataList.get(position);
        if (viewHolder instanceof NativeExpressAdViewHolder) {
            // 信息流模板广告类型
            ((NativeExpressAdViewHolder) viewHolder).setData((ADSuyiNativeExpressAdInfo) item);
        } else {
            // 普通数据类型的
            try {
                ((NormalDataViewHolder) viewHolder).setData((String) item);
            } catch (Exception e) {
                ((NormalDataViewHolder) viewHolder).setData("广告类型异常");
            }
        }
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    @Override
    public int getItemViewType(int position) {
        Object item = dataList.get(position);
        if  (item instanceof ADSuyiNativeExpressAdInfo) {
            // item 为信息流模板广告数据
            return ITEM_VIEW_TYPE_EXPRESS_AD;
        } else {
            // item 为模拟数据
            return ITEM_VIEW_TYPE_NORMAL_DATA;
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
