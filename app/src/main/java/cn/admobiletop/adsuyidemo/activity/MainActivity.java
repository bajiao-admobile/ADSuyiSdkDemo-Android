package cn.admobiletop.adsuyidemo.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

import cn.admobiletop.adsuyi.ADSuyiSdk;
import cn.admobiletop.adsuyi.ad.data.ADSuyiAdType;
import cn.admobiletop.adsuyidemo.R;
import cn.admobiletop.adsuyidemo.constant.ADSuyiDemoConstant;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

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
        findViewById(R.id.btnNativeAd).setOnClickListener(this);
        findViewById(R.id.btnRewardVodAd).setOnClickListener(this);
        findViewById(R.id.btnFullScreenAd).setOnClickListener(this);
        findViewById(R.id.btnInterstitialAd).setOnClickListener(this);
        findViewById(R.id.btnDrawVodAd).setOnClickListener(this);
        findViewById(R.id.btnContentMoudle).setOnClickListener(this);
        findViewById(R.id.btnAdmobileDlAdModule).setOnClickListener(this);
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
            case R.id.btnContentMoudle:
                startActivity(ContentModuleActivity.class);
                break;
            case R.id.btnAdmobileDlAdModule:
                startActivity(DlModuleActivity.class);
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
