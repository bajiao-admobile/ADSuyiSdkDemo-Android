package cn.admobiletop.adsuyidemo.activity.other;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import cn.admobiletop.adsuyidemo.R;
import cn.admobiletop.adsuyidemo.activity.ad.NativeAdRecyclerViewActivity;
import cn.admobiletop.adsuyidemo.activity.ad.NativeExpressActivity;

/**
 * @author : maipian
 * @date : 2021/11/01
 * @description : 信息流广告案例
 */
public class NativeAdActivity extends AppCompatActivity implements View.OnClickListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_native);

        findViewById(R.id.btnNativeRecyclerView).setOnClickListener(this);
        findViewById(R.id.btnNativeRelativeLayout).setOnClickListener(this);
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
            default:
                break;
        }
    }

    private void startActivity(Class<? extends Activity> activityClass) {
        Intent intent = new Intent(this, activityClass);
        startActivity(intent);
    }
}
