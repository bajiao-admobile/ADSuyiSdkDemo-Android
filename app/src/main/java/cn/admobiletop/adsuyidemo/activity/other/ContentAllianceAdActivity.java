package cn.admobiletop.adsuyidemo.activity.other;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.ArrayList;
import java.util.List;

import cn.admobiletop.adsuyi.ad.ADSuyiContentAllianceAd;
import cn.admobiletop.adsuyi.ad.data.ADSuyiContentAllianceAdInfo;
import cn.admobiletop.adsuyi.ad.error.ADSuyiError;
import cn.admobiletop.adsuyi.ad.listener.ADSuyiContentAllianceAdListener;
import cn.admobiletop.adsuyi.util.ADSuyiToastUtil;
import cn.admobiletop.adsuyidemo.R;
import cn.admobiletop.adsuyidemo.adapter.ContentAllianceAdAdapter;
import cn.admobiletop.adsuyidemo.constant.ADSuyiDemoConstant;
import cn.admobiletop.adsuyidemo.entity.ContentAllianceAdSampleData;
import cn.admobiletop.adsuyidemo.widget.MySmartRefreshLayout;

/**
 * @author 草莓
 * @description 内容联盟广告示例
 * @date 2020/01/06
 */
public class ContentAllianceAdActivity extends AppCompatActivity implements OnRefreshLoadMoreListener {
    private MySmartRefreshLayout refreshLayout;
    private ContentAllianceAdAdapter contentAllianceAdAdapter;
    private ADSuyiContentAllianceAd adSuyiContentAllianceAd;
    private ADSuyiContentAllianceAdInfo contentAllianceAdInfo;
    private List<ContentAllianceAdSampleData> tempDataList = new ArrayList<>();
    private int refreshType;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content_alliance_ad);

        initView();
        initListener();
        initData();
    }

    private void initView() {
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        refreshLayout = findViewById(R.id.refreshLayout);

        contentAllianceAdAdapter = new ContentAllianceAdAdapter(this);
        recyclerView.setAdapter(contentAllianceAdAdapter);
    }

    private void initListener() {
        refreshLayout.setOnRefreshLoadMoreListener(this);

        findViewById(R.id.btnKSContent).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (contentAllianceAdInfo != null && "ksad".equals(contentAllianceAdInfo.getPlatform())) {
                    contentAllianceAdInfo.openKSContentPage(ContentAllianceAdActivity.this);
                } else {
                    ADSuyiToastUtil.show(ContentAllianceAdActivity.this, "快手内容还未准备好");
                }
            }
        });
    }

    private void initData() {
        // 创建信息流广告实例
        adSuyiContentAllianceAd = new ADSuyiContentAllianceAd(this);
        // 设置广告监听
        adSuyiContentAllianceAd.setListener(new ADSuyiContentAllianceAdListener() {


            @Override
            public void onAdReceive(ADSuyiContentAllianceAdInfo adSuyiContentAllianceAdInfo) {
                Log.d(ADSuyiDemoConstant.TAG, "onAdReceive: " + adSuyiContentAllianceAdInfo);
                if (adSuyiContentAllianceAdInfo != null) {
                    contentAllianceAdInfo = adSuyiContentAllianceAdInfo;
                    tempDataList.add(new ContentAllianceAdSampleData(adSuyiContentAllianceAdInfo));
                    contentAllianceAdAdapter.addData(tempDataList);
                }
                refreshLayout.finish(refreshType, true, false);
            }

            @Override
            public void onAdExpose(ADSuyiContentAllianceAdInfo adSuyiContentAllianceAdInfo) {
                Log.d(ADSuyiDemoConstant.TAG, "onAdExpose: " + adSuyiContentAllianceAdInfo);
            }

            @Override
            public void onAdClick(ADSuyiContentAllianceAdInfo adSuyiContentAllianceAdInfo) {
                Log.d(ADSuyiDemoConstant.TAG, "onAdClick: " + adSuyiContentAllianceAdInfo);
            }

            @Override
            public void onAdClose(ADSuyiContentAllianceAdInfo adSuyiContentAllianceAdInfo) {
                Log.d(ADSuyiDemoConstant.TAG, "onAdClose: " + adSuyiContentAllianceAdInfo);
            }

            @Override
            public void onAdFailed(ADSuyiError adSuyiError) {
                if (adSuyiError != null) {
                    Log.d(ADSuyiDemoConstant.TAG, "onAdFailed: " + adSuyiError.toString());
                }
                contentAllianceAdAdapter.addData(tempDataList);
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
        contentAllianceAdAdapter.clearData();
        loadData();
    }

    /**
     * 加载数据和广告
     */
    private void loadData() {
        tempDataList.clear();
        mockNormalDataRequest();
        // 内容联盟广告场景id（场景id非必选字段，如果需要可到开发者后台创建）
        adSuyiContentAllianceAd.setSceneId(ADSuyiDemoConstant.CONTENT_ALLIANCE_AD_SCENE_ID);
        // 请求广告数据，参数一广告位ID
        adSuyiContentAllianceAd.loadAd(ADSuyiDemoConstant.CONTENT_ALLIANCE_AD_POS_ID);
    }

    /**
     * 模拟普通数据请求
     */
    private void mockNormalDataRequest() {
        for (int i = 0; i < 20; i++) {
            tempDataList.add(new ContentAllianceAdSampleData("模拟的普通数据 : " + (contentAllianceAdAdapter == null ? 0 : contentAllianceAdAdapter.getItemCount() + i)));
        }
    }
}
