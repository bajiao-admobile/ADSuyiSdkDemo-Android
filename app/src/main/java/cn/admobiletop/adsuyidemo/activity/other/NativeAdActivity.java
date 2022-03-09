package cn.admobiletop.adsuyidemo.activity.other;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import cn.admobiletop.adsuyi.util.ADSuyiToastUtil;
import cn.admobiletop.adsuyidemo.R;
import cn.admobiletop.adsuyidemo.activity.ad.feed.NativeAdRecyclerViewActivity;
import cn.admobiletop.adsuyidemo.activity.ad.feed.NativeExpressActivity;
import cn.admobiletop.adsuyidemo.activity.ad.feed.NativeInterstitialActivity;
import cn.admobiletop.adsuyidemo.activity.ad.feed.NativeSlideshowActivity;
import cn.admobiletop.adsuyidemo.activity.ad.feed.NativeSplashActivity;
import cn.admobiletop.adsuyidemo.constant.ADSuyiDemoConstant;

/**
 * @author : maipian
 * @date : 2021/11/01
 * @description : 信息流广告案例
 */
public class NativeAdActivity extends AppCompatActivity implements View.OnClickListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_native);
        Toolbar toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);

        findViewById(R.id.btnNativeRecyclerView).setOnClickListener(this);
        findViewById(R.id.btnNativeRelativeLayout).setOnClickListener(this);
        findViewById(R.id.btnNativeSlideshow).setOnClickListener(this);
        findViewById(R.id.btnNativeSplash).setOnClickListener(this);
        findViewById(R.id.btnNativeInterstitial).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnNativeRecyclerView:
                startActivity(NativeAdRecyclerViewActivity.class);
                break;
            case R.id.btnNativeRelativeLayout:
                startActivity(NativeExpressActivity.class);
                break;
            case R.id.btnNativeSlideshow:
                startActivity(NativeSlideshowActivity.class);
                break;
            case R.id.btnNativeSplash:
                startActivity(NativeSplashActivity.class);
                break;
            case R.id.btnNativeInterstitial:
                startActivity(NativeInterstitialActivity.class);
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
                    }
                }).create().show();
    }

    private void startActivity(Class<? extends Activity> activityClass) {
        Intent intent = new Intent(this, activityClass);
        startActivity(intent);
    }
}
