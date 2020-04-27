package cn.admobiletop.adsuyidemo.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import cn.admobiletop.adsuyi.ADSuyiSdk;
import cn.admobiletop.adsuyi.ad.data.ADSuyiAdType;
import cn.admobiletop.adsuyidemo.R;

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
        findViewById(R.id.btnFragmentExamples).setOnClickListener(this);
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
            case R.id.btnFragmentExamples:
                startActivity(FragmentActivity.class);
                break;
            case R.id.ivSetting:
                showAdTypeCheckDialog();
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
                        switch (which) {
                            case 0:
                                adType = ADSuyiAdType.TYPE_SPLASH;
                                break;
                            case 1:
                                adType = ADSuyiAdType.TYPE_BANNER;
                                break;
                            case 2:
                                adType = ADSuyiAdType.TYPE_FLOW;
                                break;
                            case 3:
                                adType = ADSuyiAdType.TYPE_REWARD_VOD;
                                break;
                            case 4:
                                adType = ADSuyiAdType.TYPE_FULLSCREEN_VOD;
                                break;
                            case 5:
                                adType = ADSuyiAdType.TYPE_INTERSTITIAL;
                                break;
                            case 6:
                                adType = ADSuyiAdType.TYPE_DRAW_VOD;
                                break;
                            default:
                                break;
                        }
                        SettingActivity.start(MainActivity.this, adType);
                        dialog.dismiss();
                    }
                }).create().show();
    }

    private void startActivity(Class<? extends Activity> activityClass) {
        Intent intent = new Intent(this, activityClass);
        startActivity(intent);
    }
}
