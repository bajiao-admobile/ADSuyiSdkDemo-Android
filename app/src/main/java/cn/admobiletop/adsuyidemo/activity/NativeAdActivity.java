package cn.admobiletop.adsuyidemo.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

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
 * @description 信息流广告示例
 * @date 2020/4/1
 */
public class NativeAdActivity extends AppCompatActivity implements OnRefreshLoadMoreListener {
    private MySmartRefreshLayout refreshLayout;
    private NativeAdAdapter nativeAdAdapter;
    private ADSuyiNativeAd adSuyiNativeAd;
    private int refreshType;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_native_ad);

        initView();
        initListener();
        initData();
    }

    private void initView() {
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        refreshLayout = findViewById(R.id.refreshLayout);

        nativeAdAdapter = new NativeAdAdapter(this);
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
        ADSuyiExtraParams extraParams = new ADSuyiExtraParams();
        // 设置额外参数中的广告预期宽高，单位为px，高度如果小于等于0则高度自适应（Inmobi平台的信息流广告高度均为自适应）
        extraParams.setAdSize(new ADSuyiAdSize(widthPixels, 0));
        // 设置一些额外参数，有些平台的广告可能需要传入一些额外参数，如果有接入头条、Inmobi平台，该参数必须设置
        adSuyiNativeAd.setLocalExtraParams(extraParams);
        // 设置仅支持的广告平台，设置了这个值，获取广告时只会去获取该平台的广告，null或空字符串为不限制，默认为null
        adSuyiNativeAd.setOnlySupportPlatform(ADSuyiDemoConstant.NATIVE_AD_ONLY_SUPPORT_PLATFORM);
        // 设置广告监听
        adSuyiNativeAd.setListener(new ADSuyiNativeAdListener() {
            @Override
            public void onRenderFailed(ADSuyiNativeAdInfo adSuyiNativeAdInfo, ADSuyiError adSuyiError) {
                Log.d(ADSuyiDemoConstant.TAG, "onRenderFailed: " + adSuyiError.toString());
                // 广告渲染失败，释放和移除ADSuyiNativeAdInfo
                nativeAdAdapter.removeData(adSuyiNativeAdInfo);
            }

            @Override
            public void onRenderSuccess(ADSuyiNativeAdInfo adSuyiNativeAdInfo) {
                Log.d(ADSuyiDemoConstant.TAG, "onRenderSuccess: " + adSuyiNativeAdInfo.hashCode());
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
                // 广告被关闭，释放和移除ADSuyiNativeAdInfo
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
