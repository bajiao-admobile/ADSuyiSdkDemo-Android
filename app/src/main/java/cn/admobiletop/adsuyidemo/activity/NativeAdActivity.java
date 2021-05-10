package cn.admobiletop.adsuyidemo.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.ArrayList;
import java.util.List;

import cn.admobiletop.adsuyi.ad.ADSuyiNativeAd;
import cn.admobiletop.adsuyi.ad.data.ADSuyiAdType;
import cn.admobiletop.adsuyi.ad.data.ADSuyiNativeAdInfo;
import cn.admobiletop.adsuyi.ad.entity.ADSuyiAdNativeStyle;
import cn.admobiletop.adsuyi.ad.entity.ADSuyiAdSize;
import cn.admobiletop.adsuyi.ad.entity.ADSuyiExtraParams;
import cn.admobiletop.adsuyi.ad.error.ADSuyiError;
import cn.admobiletop.adsuyi.ad.listener.ADSuyiNativeAdListener;
import cn.admobiletop.adsuyi.util.ADSuyiDisplayUtil;
import cn.admobiletop.adsuyi.util.ADSuyiToastUtil;
import cn.admobiletop.adsuyidemo.R;
import cn.admobiletop.adsuyidemo.adapter.BaseNativeAdAdapter;
import cn.admobiletop.adsuyidemo.adapter.NativeAdAdapter;
import cn.admobiletop.adsuyidemo.adapter.NativeExpressAdAdapter;
import cn.admobiletop.adsuyidemo.adapter.NativeFeedAdAdapter;
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
    private RecyclerView recyclerView;
    private BaseNativeAdAdapter nativeAdAdapter;
    private ADSuyiNativeAd adSuyiNativeAd;
    private List<Object> tempDataList = new ArrayList<>();
    private int refreshType;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_native_ad);
        Toolbar toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);

        initView();
        initNativeAdapter();
        initListener();
        initData();
    }

    private void initView() {
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        refreshLayout = findViewById(R.id.refreshLayout);
    }

    private void initListener() {
        refreshLayout.setOnRefreshLoadMoreListener(this);
    }

    private void initData() {
        // 模版广告容器宽度
        int widthPixels = getResources().getDisplayMetrics().widthPixels;
        // 创建信息流广告实例
        adSuyiNativeAd = new ADSuyiNativeAd(this);
        // 创建额外参数实例
        ADSuyiExtraParams extraParams = new ADSuyiExtraParams.Builder()
                // 设置整个广告视图预期宽高(目前仅头条、艾狄墨搏平台需要，没有接入头条、艾狄墨搏可不设置)，单位为px，高度如果小于等于0则高度自适应
                .adSize(new ADSuyiAdSize(widthPixels, 0))
                // 设置广告视图中MediaView的预期宽高(目前仅Inmobi平台需要,Inmobi的MediaView高度为自适应，没有接入Inmobi平台可不设置)，单位为px
                .nativeAdMediaViewSize(new ADSuyiAdSize((int) (widthPixels - 24 * getResources().getDisplayMetrics().density)))
                // 设置模版广告文字、内边距特殊样式（目前仅艾狄墨搏平台需要，没有特殊需求可不设置）
                .nativeStyle(getAdmobileNativeStyle())
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
                nativeAdAdapter.removeData(adSuyiNativeAdInfo);
            }

            @Override
            public void onAdReceive(List<ADSuyiNativeAdInfo> adInfoList) {
                Log.d(ADSuyiDemoConstant.TAG, "onAdReceive: " + adInfoList.size());
                for (int i = 0; i < adInfoList.size(); i++) {
                    int index = i * 5;
                    ADSuyiNativeAdInfo nativeAdInfo = adInfoList.get(i);
                    if (index >= tempDataList.size()) {
                        tempDataList.add(nativeAdInfo);
                    } else {
                        tempDataList.add(index, nativeAdInfo);
                    }
                }
                nativeAdAdapter.addData(tempDataList);
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
                // 注意，信息流广告点击关闭时，开发者需要在onAdClose回调中将广告容器隐藏或移除，避免如头条渠道点击关闭后视图依旧存在
                nativeAdAdapter.removeData(adSuyiNativeAdInfo);
            }

            @Override
            public void onAdFailed(ADSuyiError adSuyiError) {
                if (adSuyiError != null) {
                    Log.d(ADSuyiDemoConstant.TAG, "onAdFailed: " + adSuyiError.toString());
                }
                nativeAdAdapter.addData(tempDataList);
                refreshLayout.finish(refreshType, false, false);
            }
        });

        // 触发刷新
        refreshLayout.autoRefresh();
    }

    /**
     * 获取admobile模版广告样式
     */
    private ADSuyiAdNativeStyle getAdmobileNativeStyle() {
        // 艾狄墨搏模版广告上下左右内边距
        int paddingLeft = ADSuyiDisplayUtil.dp2px(10);
        int paddingTop = ADSuyiDisplayUtil.dp2px(10);
        int paddingRight = ADSuyiDisplayUtil.dp2px(10);
        int paddingBottom = ADSuyiDisplayUtil.dp2px(10);
        // 模版广告样式，目前仅艾狄墨搏平台需要，如果有特殊需求可以传入
        ADSuyiAdNativeStyle nativeStyle = new ADSuyiAdNativeStyle(paddingLeft, paddingTop, paddingRight, paddingBottom);
        // 设置标题文字大小
        nativeStyle.setTitleSize(12);
        // 设置内容文字大小
        nativeStyle.setDescSize(18);
        return nativeStyle;
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
        tempDataList.clear();
        mockNormalDataRequest();

        // 信息流广告场景id（场景id非必选字段，如果需要可到开发者后台创建）
        adSuyiNativeAd.setSceneId(ADSuyiDemoConstant.NATIVE_AD_SCENE_ID);
        // 请求广告数据，参数一广告位ID，参数二请求数量[1,3]
        adSuyiNativeAd.loadAd(ADSuyiDemoConstant.NATIVE_AD_POS_ID, ADSuyiDemoConstant.NATIVE_AD_COUNT);
    }

    /**
     * 模拟普通数据请求
     */
    private void mockNormalDataRequest() {
        for (int i = 0; i < 20; i++) {
            tempDataList.add("模拟的普通数据 : " + (nativeAdAdapter == null ? 0 : nativeAdAdapter.getItemCount() + i));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.toolbar_setting:
                showAdTypeCheckDialog();
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }

    private void showAdTypeCheckDialog() {
        new AlertDialog.Builder(this)
                .setItems(R.array.native_ad_list, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String toastContent = "设置完成，生效中";
                        switch (which) {
                            case 0:
                                ADSuyiDemoConstant.NATIVE_AD_POS_ID = ADSuyiDemoConstant.NATIVE_AD_POS_ID1;
                                break;
                            case 1:
                                ADSuyiDemoConstant.NATIVE_AD_POS_ID = ADSuyiDemoConstant.NATIVE_AD_POS_ID2;;
                                break;
                            case 2:
                                ADSuyiDemoConstant.NATIVE_AD_POS_ID = ADSuyiDemoConstant.NATIVE_AD_POS_ID4;
                            default:
                                break;
                        }
                        ADSuyiToastUtil.show(NativeAdActivity.this, toastContent);
                        dialog.dismiss();
                        initNativeAdapter();
                        loadData();
                    }
                }).create().show();
    }

    /**
     * 初始化信息流广告适配器，根据需求挑选
     */
    private void initNativeAdapter() {
        switch (ADSuyiDemoConstant.NATIVE_AD_POS_ID) {
            case ADSuyiDemoConstant.NATIVE_AD_POS_ID1:
                // 自渲染
                nativeAdAdapter = new NativeFeedAdAdapter();
                break;
            case ADSuyiDemoConstant.NATIVE_AD_POS_ID2:
                // 模板自渲染混合
                nativeAdAdapter = new NativeAdAdapter();
                break;
            case ADSuyiDemoConstant.NATIVE_AD_POS_ID4:
                // 模版
                nativeAdAdapter = new NativeExpressAdAdapter();
                break;
        }

        recyclerView.setAdapter(nativeAdAdapter);
        nativeAdAdapter.clearData();
    }
}
