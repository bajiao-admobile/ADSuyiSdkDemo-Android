package cn.admobiletop.adsuyidemo.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.ArrayList;
import java.util.List;

import cn.admobiletop.adsuyi.ad.ADSuyiNativeAd;
import cn.admobiletop.adsuyi.ad.data.ADSuyiNativeAdInfo;
import cn.admobiletop.adsuyi.ad.entity.ADSuyiAdSize;
import cn.admobiletop.adsuyi.ad.entity.ADSuyiExtraParams;
import cn.admobiletop.adsuyi.ad.error.ADSuyiError;
import cn.admobiletop.adsuyi.ad.listener.ADSuyiNativeAdListener;
import cn.admobiletop.adsuyidemo.R;
import cn.admobiletop.adsuyidemo.adapter.NativeAdAdapter;
import cn.admobiletop.adsuyidemo.constant.ADSuyiDemoConstant;
import cn.admobiletop.adsuyidemo.entity.NativeAdSampleData;
import cn.admobiletop.adsuyidemo.widget.MySmartRefreshLayout;

/**
 * @author ciba
 * @description 信息流广告Fragment示例
 * @date 2020/4/20
 */
public class NativeAdFragment extends BaseFragment implements OnRefreshLoadMoreListener {
    private MySmartRefreshLayout refreshLayout;
    private NativeAdAdapter nativeAdAdapter;
    private ADSuyiNativeAd adSuyiNativeAd;
    private int refreshType;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.activity_native_ad, null);

        initView(inflate);
        initListener();
        initData();

        return inflate;
    }

    @Override
    public String getTitle() {
        return "信息流广告";
    }

    private void initView(View inflate) {
        RecyclerView recyclerView = inflate.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(inflate.getContext()));
        refreshLayout = inflate.findViewById(R.id.refreshLayout);

        nativeAdAdapter = new NativeAdAdapter(inflate.getContext());
        recyclerView.setAdapter(nativeAdAdapter);
    }

    private void initListener() {
        refreshLayout.setOnRefreshLoadMoreListener(this);
    }

    private void initData() {
        // 创建信息流广告实例
        adSuyiNativeAd = new ADSuyiNativeAd(this);
        int widthPixels = getResources().getDisplayMetrics().widthPixels;
        // 创建额外参数实例
        ADSuyiExtraParams extraParams = new ADSuyiExtraParams.Builder()
                // 设置整个广告视图预期宽高(目前仅头条平台需要，没有接入头条可不设置)，单位为px，高度如果小于等于0则高度自适应
                .adSize(new ADSuyiAdSize(widthPixels, 0))
                // 设置广告视图中的MediaView的预期宽高(目前仅Inmobi平台需要,Inmobi的MediaView高度为自适应，没有接入Inmobi平台可不设置)，单位为px
                .nativeAdMediaViewSize(new ADSuyiAdSize(widthPixels))
                .build();
        // 设置一些额外参数，有些平台的广告可能需要传入一些额外参数，如果有接入头条、Inmobi平台，该参数必须设置
        adSuyiNativeAd.setLocalExtraParams(extraParams);

        // 设置仅支持的广告平台，设置了这个值，获取广告时只会去获取该平台的广告，null或空字符串为不限制，默认为null，方便调试使用，上线时建议不设置
        adSuyiNativeAd.setOnlySupportPlatform(ADSuyiDemoConstant.NATIVE_AD_ONLY_SUPPORT_PLATFORM);
        // 设置广告监听
        adSuyiNativeAd.setListener(new ADSuyiNativeAdListener() {
            @Override
            public void onRenderFailed(ADSuyiNativeAdInfo adSuyiNativeAdInfo, ADSuyiError adSuyiError) {
                Log.d(ADSuyiDemoConstant.TAG, "onRenderFailed: " + adSuyiError.toString());
                nativeAdAdapter.removeData(adSuyiNativeAdInfo);
            }

            @Override
            public void onAdReceive(List<ADSuyiNativeAdInfo> adInfoList) {
                Log.d(ADSuyiDemoConstant.TAG, "onAdReceive: " + adInfoList.size());
                List<NativeAdSampleData> nativeAdSampleDataList = new ArrayList<>();
                for (int i = 0; i < adInfoList.size(); i++) {
                    ADSuyiNativeAdInfo nativeAdInfo = adInfoList.get(i);
                    nativeAdSampleDataList.add(new NativeAdSampleData(nativeAdInfo));
                }
                nativeAdAdapter.addData(nativeAdSampleDataList);
                refreshLayout.finish(refreshType, true, false);
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
                nativeAdAdapter.removeData(adSuyiNativeAdInfo);
            }

            @Override
            public void onAdFailed(ADSuyiError adSuyiError) {
                if (adSuyiError != null) {
                    Log.d(ADSuyiDemoConstant.TAG, "onAdFailed: " + adSuyiError.toString());
                }
                refreshLayout.finish(refreshType, false, false);
            }
        });

        // 触发刷新
        refreshLayout.autoRefresh();
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        refreshType = MySmartRefreshLayout.TYPE_LOAD_MORE;
        loadData();
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        refreshType = MySmartRefreshLayout.TYPE_FRESH;
        nativeAdAdapter.clearData();
        loadData();
    }

    /**
     * 加载数据和广告
     */
    private void loadData() {
        List<NativeAdSampleData> normalDataList = mockNormalDataRequest();
        nativeAdAdapter.addData(normalDataList);

        // 请求广告数据，参数一广告位ID，参数二请求数量[1,3]
        adSuyiNativeAd.loadAd(ADSuyiDemoConstant.NATIVE_AD_POS_ID, ADSuyiDemoConstant.NATIVE_AD_COUNT);
    }

    /**
     * 模拟普通数据请求
     *
     * @return : 普通数据列表
     */
    private List<NativeAdSampleData> mockNormalDataRequest() {
        List<NativeAdSampleData> normalDataList = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            normalDataList.add(new NativeAdSampleData("模拟的普通数据 : " + (nativeAdAdapter.getItemCount() + i)));
        }
        return normalDataList;
    }
}
