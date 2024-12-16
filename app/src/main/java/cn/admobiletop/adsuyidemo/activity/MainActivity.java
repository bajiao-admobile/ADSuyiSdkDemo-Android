package cn.admobiletop.adsuyidemo.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import cn.admobiletop.adsuyi.ADSuyiSdk;
import cn.admobiletop.adsuyidemo.R;
import cn.admobiletop.adsuyidemo.activity.ad.BannerAdActivity;
import cn.admobiletop.adsuyidemo.activity.ad.feed.NativeExpressActivity;
import cn.admobiletop.adsuyidemo.activity.ad.feed.NativeSelfRenderActivity;
import cn.admobiletop.adsuyidemo.activity.ad.interstitial.InterstitialAdActivity;
import cn.admobiletop.adsuyidemo.activity.ad.RewardVodAdActivity;
import cn.admobiletop.adsuyidemo.activity.ad.splash.SplashAdSettingActivity;
import cn.admobiletop.adsuyidemo.activity.setting.SettingActivity;
import cn.admobiletop.adsuyidemo.util.SPUtil;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Switch switchPersonalized;
    private Switch switchCgq;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
        ((TextView) findViewById(R.id.tvVersion)).setText("V" + ADSuyiSdk.getInstance().getSdkVersion());

        findViewById(R.id.btnSplashAd).setOnClickListener(this);
        findViewById(R.id.btnBannerAd).setOnClickListener(this);
        findViewById(R.id.btnNativeExpressLayout).setOnClickListener(this);
        findViewById(R.id.btnNativeSelfRenderLayout).setOnClickListener(this);
        findViewById(R.id.btnRewardVodAd).setOnClickListener(this);
        findViewById(R.id.btnFullScreenAd).setOnClickListener(this);
        findViewById(R.id.btnInterstitialAd).setOnClickListener(this);
        findViewById(R.id.btnDrawVodAd).setOnClickListener(this);
        findViewById(R.id.btnInnerNotice).setOnClickListener(this);


        boolean iscgq = SPUtil.getBoolean(MainActivity.this, "cgq");
        switchCgq = findViewById(R.id.switchCgq);
        switchCgq.setChecked(iscgq);

        ADSuyiSdk.setPersonalizedAdEnabled(false);
        switchPersonalized = findViewById(R.id.switchPersonalized);

        boolean personalized = ADSuyiSdk.getInstance().getPersonalizedAdEnabled();
        switchPersonalized.setChecked(personalized);

        switchPersonalized.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                ADSuyiSdk.setPersonalizedAdEnabled(isChecked);
            }
        });

        switchCgq.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SPUtil.putBoolean(MainActivity.this, "cgq", isChecked);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnSplashAd:
                startActivity(SplashAdSettingActivity.class);
                break;
            case R.id.btnBannerAd:
                startActivity(BannerAdActivity.class);
                break;
            case R.id.btnNativeExpressLayout:
                startActivity(NativeExpressActivity.class);
                break;
            case R.id.btnNativeSelfRenderLayout:
                startActivity(NativeSelfRenderActivity.class);
                break;
            case R.id.btnRewardVodAd:
                startActivity(RewardVodAdActivity.class);
                break;
            case R.id.btnInterstitialAd:
                startActivity(InterstitialAdActivity.class);
                break;
            default:
                break;
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
                startActivity(SettingActivity.class);
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }


    private void startActivity(Class<? extends Activity> activityClass) {
        Intent intent = new Intent(this, activityClass);
        startActivity(intent);
    }
}
