package cn.admobiletop.adsuyidemo.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

import cn.admobiletop.adsuyi.ADSuyiSdk;
import cn.admobiletop.adsuyi.ad.data.ADSuyiAdType;
import cn.admobiletop.adsuyi.util.ADSuyiToastUtil;
import cn.admobiletop.adsuyidemo.R;
import cn.admobiletop.adsuyidemo.constant.ADSuyiDemoConstant;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ((TextView) findViewById(R.id.tvVersion)).setText("V" + ADSuyiSdk.getInstance().getSdkVersion());

        findViewById(R.id.btnSplashAd).setOnClickListener(this);
        findViewById(R.id.btnBannerAd).setOnClickListener(this);
        findViewById(R.id.btnNativeAd).setOnClickListener(this);
        findViewById(R.id.btnRewardVodAd).setOnClickListener(this);
        findViewById(R.id.btnFullScreenAd).setOnClickListener(this);
        findViewById(R.id.btnInterstitialAd).setOnClickListener(this);
        findViewById(R.id.btnDrawVodAd).setOnClickListener(this);
        findViewById(R.id.btnNovel).setOnClickListener(this);
        findViewById(R.id.btnFragmentExamples).setOnClickListener(this);
        findViewById(R.id.btnAdmobileDlAd).setOnClickListener(this);
        findViewById(R.id.btnRewardAdSwitch).setOnClickListener(this);
        findViewById(R.id.ivSetting).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnSplashAd:
                startActivity(SplashAdActivity.class);
                break;
            case R.id.btnBannerAd:
                startActivity(BannerAdActivity.class);
                break;
            case R.id.btnNativeAd:
                startActivity(NativeAdActivity.class);
                break;
            case R.id.btnRewardVodAd:
                startActivity(RewardVodAdActivity.class);
                break;
            case R.id.btnFullScreenAd:
                startActivity(FullScreenVodAdActivity.class);
                break;
            case R.id.btnInterstitialAd:
                startActivity(InterstitialAdActivity.class);
                break;
            case R.id.btnDrawVodAd:
                startActivity(DrawVodActivity.class);
                break;
            case R.id.btnNovel:
                // 设置字体
//                NovelSDKManger.setFontPath("字体路径");
                boolean openSuccess = ADSuyiSdk.getInstance().openNovelActivity();
                ADSuyiToastUtil.show(getApplicationContext(), openSuccess ? "打开成功" : "打开失败");
                break;
            case R.id.btnFragmentExamples:
                startActivity(FragmentActivity.class);
                break;
            case R.id.btnAdmobileDlAd:
                startActivity(DlAdActivity.class);
                break;
            case R.id.ivSetting:
                showAdTypeCheckDialog();
                break;
            case R.id.btnRewardAdSwitch:
                startActivity(RewardAdSwitchActivity.class);
                break;
            default:
                break;
        }
    }

    private void showAdTypeCheckDialog() {
        new AlertDialog.Builder(this)
                .setItems(R.array.ad_type_items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String adType = null;
                        ArrayList<String> posIdList = new ArrayList<>();
                        switch (which) {
                            case 0:
                                adType = ADSuyiAdType.TYPE_SPLASH;
                                posIdList.add(ADSuyiDemoConstant.SPLASH_AD_POS_ID1);
                                break;
                            case 1:
                                adType = ADSuyiAdType.TYPE_BANNER;
                                posIdList.add(ADSuyiDemoConstant.BANNER_AD_POS_ID1);
                                posIdList.add(ADSuyiDemoConstant.BANNER_AD_POS_ID2);
                                posIdList.add(ADSuyiDemoConstant.BANNER_AD_POS_ID3);
                                break;
                            case 2:
                                adType = ADSuyiAdType.TYPE_FLOW;
                                posIdList.add(ADSuyiDemoConstant.NATIVE_AD_POS_ID1);
                                posIdList.add(ADSuyiDemoConstant.NATIVE_AD_POS_ID2);
                                posIdList.add(ADSuyiDemoConstant.NATIVE_AD_POS_ID3);
                                break;
                            case 3:
                                adType = ADSuyiAdType.TYPE_REWARD_VOD;
                                posIdList.add(ADSuyiDemoConstant.REWARD_VOD_AD_POS_ID1);
                                posIdList.add(ADSuyiDemoConstant.REWARD_VOD_AD_POS_ID2);
                                break;
                            case 4:
                                adType = ADSuyiAdType.TYPE_FULLSCREEN_VOD;
                                posIdList.add(ADSuyiDemoConstant.FULL_SCREEN_VOD_AD_POS_ID1);
                                break;
                            case 5:
                                adType = ADSuyiAdType.TYPE_INTERSTITIAL;
                                posIdList.add(ADSuyiDemoConstant.INTERSTITIAL_AD_POS_ID1);
                                break;
                            case 6:
                                adType = ADSuyiAdType.TYPE_DRAW_VOD;
                                posIdList.add(ADSuyiDemoConstant.DRAW_VOD_AD_POS_ID1);
                                break;
                            case 7:
                                FloatingAdSettingActivity.start(MainActivity.this);
                                break;
                            default:
                                break;
                        }
                        if (!TextUtils.isEmpty(adType)) {
                            SettingActivity.start(MainActivity.this, adType, posIdList);
                        }
                        dialog.dismiss();
                    }
                }).create().show();
    }

    private void startActivity(Class<? extends Activity> activityClass) {
        Intent intent = new Intent(this, activityClass);
        startActivity(intent);
    }
}
