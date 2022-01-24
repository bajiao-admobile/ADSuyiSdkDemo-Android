package cn.admobiletop.adsuyidemo.activity.other;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import cn.admobiletop.adsuyidemo.R;
import cn.admobiletop.adsuyidemo.activity.ad.feed.NativeAdRecyclerViewActivity;
import cn.admobiletop.adsuyidemo.activity.ad.feed.NativeExpressActivity;
import cn.admobiletop.adsuyidemo.activity.ad.feed.NativeSlideshowActivity;
import cn.admobiletop.adsuyidemo.activity.ad.feed.NativeSplashActivity;

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

        findViewById(R.id.btnNativeRecyclerView).setOnClickListener(this);
        findViewById(R.id.btnNativeRelativeLayout).setOnClickListener(this);
        findViewById(R.id.btnNativeSlideshow).setOnClickListener(this);
        findViewById(R.id.btnNativeSplash).setOnClickListener(this);
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
            default:
                break;
        }
    }

    private void startActivity(Class<? extends Activity> activityClass) {
        Intent intent = new Intent(this, activityClass);
        startActivity(intent);
    }
}
