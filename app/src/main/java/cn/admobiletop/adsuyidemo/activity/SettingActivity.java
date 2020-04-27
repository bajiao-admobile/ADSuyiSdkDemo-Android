package cn.admobiletop.adsuyidemo.activity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.admobiletop.adsuyi.ad.data.ADSuyiAdType;
import cn.admobiletop.adsuyi.util.ADSuyiToastUtil;
import cn.admobiletop.adsuyidemo.R;
import cn.admobiletop.adsuyidemo.constant.ADSuyiDemoConstant;

/**
 * @author ciba
 * @description 设置界面
 * @date 2020/4/7
 */
public class SettingActivity extends AppCompatActivity {
    private static final String AD_TYPE = "AD_TYPE";
    private static final String POS_ID_LIST = "POS_ID_LIST";
    private EditText etPosId;
    private TextView tvCount;
    private EditText etCount;
    private TextView tvAutoRefreshInterval;
    private EditText etAutoRefreshInterval;
    private String adType;
    private EditText etOnlySupportPlatform;
    private List<String> posIdList;

    public static void start(Context context, String adType, ArrayList<String> posIdList) {
        Intent intent = new Intent(context, SettingActivity.class);
        intent.putExtra(AD_TYPE, adType);
        intent.putStringArrayListExtra(POS_ID_LIST, posIdList);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        initView();
        initListener();
        initData();
    }

    private void initView() {
        etPosId = findViewById(R.id.etPosId);

        tvCount = findViewById(R.id.tvCount);
        etCount = findViewById(R.id.etCount);

        etOnlySupportPlatform = findViewById(R.id.etOnlySupportPlatform);

        tvAutoRefreshInterval = findViewById(R.id.tvAutoRefreshInterval);
        etAutoRefreshInterval = findViewById(R.id.etAutoRefreshInterval);
    }

    private void initListener() {
        findViewById(R.id.btnDefine).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateData();
            }
        });
        etOnlySupportPlatform.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPlatformSelectDialog();
            }
        });
        etPosId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPosIdSelectDialog();
            }
        });
    }

    private void showPosIdSelectDialog() {
        String[] posIds = new String[posIdList.size()];
        posIdList.toArray(posIds);

        new AlertDialog.Builder(this)
                .setItems(posIds, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        etPosId.setText(posIdList.get(which));
                    }
                })
                .create()
                .show();
    }

    private void showPlatformSelectDialog() {
        new AlertDialog.Builder(this)
                .setItems(R.array.platforms, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        etOnlySupportPlatform.setText(getResources().getStringArray(R.array.platforms)[which]);
                    }
                })
                .create()
                .show();
    }

    @SuppressLint("SetTextI18n")
    private void initData() {
        adType = getIntent().getStringExtra(AD_TYPE);
        adType = adType == null ? "" : adType;
        posIdList = getIntent().getStringArrayListExtra(POS_ID_LIST);
        posIdList = posIdList == null ? new ArrayList<String>() : posIdList;

        switch (adType) {
            case ADSuyiAdType.TYPE_SPLASH:
                etPosId.setText(ADSuyiDemoConstant.SPLASH_AD_POS_ID);
                etOnlySupportPlatform.setText(ADSuyiDemoConstant.SPLASH_AD_ONLY_SUPPORT_PLATFORM);
                break;
            case ADSuyiAdType.TYPE_BANNER:
                etPosId.setText(ADSuyiDemoConstant.BANNER_AD_POS_ID);
                etOnlySupportPlatform.setText(ADSuyiDemoConstant.BANNER_AD_ONLY_SUPPORT_PLATFORM);
                etAutoRefreshInterval.setText(String.valueOf(ADSuyiDemoConstant.BANNER_AD_AUTO_REFRESH_INTERVAL));
                tvAutoRefreshInterval.setVisibility(View.VISIBLE);
                etAutoRefreshInterval.setVisibility(View.VISIBLE);
                break;
            case ADSuyiAdType.TYPE_FLOW:
                etPosId.setText(ADSuyiDemoConstant.NATIVE_AD_POS_ID);
                etOnlySupportPlatform.setText(ADSuyiDemoConstant.NATIVE_AD_ONLY_SUPPORT_PLATFORM);
                etCount.setText(String.valueOf(ADSuyiDemoConstant.NATIVE_AD_COUNT));
                etCount.setVisibility(View.VISIBLE);
                tvCount.setVisibility(View.VISIBLE);
                break;
            case ADSuyiAdType.TYPE_REWARD_VOD:
                etPosId.setText(ADSuyiDemoConstant.REWARD_VOD_AD_POS_ID);
                etOnlySupportPlatform.setText(ADSuyiDemoConstant.REWARD_VOD_AD_ONLY_SUPPORT_PLATFORM);
                break;
            case ADSuyiAdType.TYPE_FULLSCREEN_VOD:
                etPosId.setText(ADSuyiDemoConstant.FULL_SCREEN_VOD_AD_POS_ID);
                etOnlySupportPlatform.setText(ADSuyiDemoConstant.FULL_SCREEN_VOD_AD_ONLY_SUPPORT_PLATFORM);
                break;
            case ADSuyiAdType.TYPE_INTERSTITIAL:
                etPosId.setText(ADSuyiDemoConstant.INTERSTITIAL_AD_POS_ID);
                etOnlySupportPlatform.setText(ADSuyiDemoConstant.INTERSTITIAL_AD_ONLY_SUPPORT_PLATFORM);
                break;
            case ADSuyiAdType.TYPE_DRAW_VOD:
                etPosId.setText(ADSuyiDemoConstant.DRAW_VOD_AD_POS_ID);
                etCount.setText(String.valueOf(ADSuyiDemoConstant.NATIVE_AD_COUNT));
                etOnlySupportPlatform.setText(ADSuyiDemoConstant.DRAW_VOD_AD_ONLY_SUPPORT_PLATFORM);
                etCount.setVisibility(View.VISIBLE);
                tvCount.setVisibility(View.VISIBLE);
                break;
            default:
                ADSuyiToastUtil.show(this, "非法广告类型");
                finish();
                break;
        }
    }

    private void updateData() {
        String posId = etPosId.getText().toString().trim();
        String onlySupportPlatform = etOnlySupportPlatform.getText().toString().trim();

        switch (adType) {
            case ADSuyiAdType.TYPE_SPLASH:
                ADSuyiDemoConstant.SPLASH_AD_POS_ID = posId;
                ADSuyiDemoConstant.SPLASH_AD_ONLY_SUPPORT_PLATFORM = onlySupportPlatform;
                break;
            case ADSuyiAdType.TYPE_BANNER:
                ADSuyiDemoConstant.BANNER_AD_POS_ID = posId;
                ADSuyiDemoConstant.BANNER_AD_AUTO_REFRESH_INTERVAL = getAutoRefreshInterval();
                ADSuyiDemoConstant.BANNER_AD_ONLY_SUPPORT_PLATFORM = onlySupportPlatform;
                break;
            case ADSuyiAdType.TYPE_FLOW:
                ADSuyiDemoConstant.NATIVE_AD_POS_ID = posId;
                ADSuyiDemoConstant.NATIVE_AD_COUNT = getAdCount();
                ADSuyiDemoConstant.NATIVE_AD_ONLY_SUPPORT_PLATFORM = onlySupportPlatform;
                break;
            case ADSuyiAdType.TYPE_REWARD_VOD:
                ADSuyiDemoConstant.REWARD_VOD_AD_POS_ID = posId;
                ADSuyiDemoConstant.REWARD_VOD_AD_ONLY_SUPPORT_PLATFORM = onlySupportPlatform;
                break;
            case ADSuyiAdType.TYPE_FULLSCREEN_VOD:
                ADSuyiDemoConstant.FULL_SCREEN_VOD_AD_POS_ID = posId;
                ADSuyiDemoConstant.FULL_SCREEN_VOD_AD_ONLY_SUPPORT_PLATFORM = onlySupportPlatform;
                break;
            case ADSuyiAdType.TYPE_INTERSTITIAL:
                ADSuyiDemoConstant.INTERSTITIAL_AD_POS_ID = posId;
                ADSuyiDemoConstant.INTERSTITIAL_AD_ONLY_SUPPORT_PLATFORM = onlySupportPlatform;
                break;
            case ADSuyiAdType.TYPE_DRAW_VOD:
                ADSuyiDemoConstant.DRAW_VOD_AD_POS_ID = posId;
                ADSuyiDemoConstant.NATIVE_AD_COUNT = getAdCount();
                ADSuyiDemoConstant.DRAW_VOD_AD_ONLY_SUPPORT_PLATFORM = onlySupportPlatform;
                break;
            default:
                break;
        }
        ADSuyiToastUtil.show(this, "修改成功");
        finish();
    }

    private int getAutoRefreshInterval() {
        String autoRefreshIntervalStr = etAutoRefreshInterval.getText().toString().trim();
        try {
            int autoRefreshInterval = Integer.parseInt(autoRefreshIntervalStr);
            return autoRefreshInterval <= 0 ? 0 : autoRefreshInterval < 30 ? 30 : autoRefreshInterval > 120 ? 120 : autoRefreshInterval;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    private int getAdCount() {
        String countStr = etCount.getText().toString().trim();
        try {
            int count = Integer.parseInt(countStr);
            return count <= 0 ? 1 : count > 3 ? 3 : count;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 1;
    }
}
