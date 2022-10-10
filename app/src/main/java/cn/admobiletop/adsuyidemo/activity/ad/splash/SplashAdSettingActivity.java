package cn.admobiletop.adsuyidemo.activity.ad.splash;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;

import cn.admobiletop.adsuyidemo.R;

/**
 * @Description:
 * @Author: 草莓
 * @CreateDate: 7/15/22 10:39 AM
 */
public class SplashAdSettingActivity extends AppCompatActivity {

    private RadioGroup rgLoadSplashAdType;
    private LinearLayout llHalfEnterLogoHeightSize;
    private EditText etLogoHeight;

    private int splashType = SplashAdActivity.HALF_SCREEN;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_setting);

        rgLoadSplashAdType = findViewById(R.id.rgLoadSplashAdType);
        llHalfEnterLogoHeightSize = findViewById(R.id.llHalfEnterLogoHeightSize);
        etLogoHeight = findViewById(R.id.etLogoHeight);

        rgLoadSplashAdType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.rbHalf) {
                    splashType = SplashAdActivity.HALF_SCREEN;
                    llHalfEnterLogoHeightSize.setVisibility(View.VISIBLE);
                } else if (checkedId == R.id.rbFullScreen) {
                    splashType = SplashAdActivity.FULL_SCREEN;
                    llHalfEnterLogoHeightSize.setVisibility(View.GONE);
                } else if (checkedId == R.id.rbImmersiveAndFullScreen) {
                    splashType = SplashAdActivity.IMMERSIVE_AND_FULLSCREEN;
                    llHalfEnterLogoHeightSize.setVisibility(View.GONE);
                }
            }
        });

        findViewById(R.id.btnLoadSplashAd).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SplashAdSettingActivity.this, SplashAdActivity.class);
                String etLogoHeightString = etLogoHeight.getText().toString().trim();
                intent.putExtra("splashType", splashType);
                intent.putExtra("logoHeightPx", TextUtils.isEmpty(etLogoHeightString) ? 0 : Integer.valueOf(etLogoHeightString));
                startActivity(intent);
            }
        });
    }

}
