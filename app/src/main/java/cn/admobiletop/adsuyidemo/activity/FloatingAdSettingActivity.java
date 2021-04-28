package cn.admobiletop.adsuyidemo.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SwitchCompat;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RadioButton;

import cn.admobiletop.adsuyi.ADSuyiSdk;
import cn.admobiletop.adsuyi.util.ADSuyiToastUtil;
import cn.admobiletop.adsuyidemo.R;
import cn.admobiletop.adsuyidemo.constant.ADSuyiDemoConstant;

/**
 * @author ciba
 * @description 浮窗广告设置界面
 * @date 2020/4/7
 */
public class FloatingAdSettingActivity extends AppCompatActivity {
    private SwitchCompat cbDarkMode;
    private RadioButton rbPause;
    private RadioButton rbRestart;

    public static void start(Context context) {
        Intent intent = new Intent(context, FloatingAdSettingActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_floating_ad_setting);

        initView();
        initListener();
    }

    private void initView() {
        cbDarkMode = findViewById(R.id.cbDarkMode);
        rbPause = findViewById(R.id.rbPause);
        rbRestart = findViewById(R.id.rbRestart);

        cbDarkMode.setChecked(ADSuyiDemoConstant.FLOATING_AD_DARK_MODE);
        if (ADSuyiDemoConstant.FLOATING_AD_IS_PAUSED) {
            rbPause.setChecked(true);
        } else {
            rbRestart.setChecked(true);
        }
    }

    private void initListener() {
        findViewById(R.id.btnDefine).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateData();
            }
        });
    }

    private void updateData() {
        // 设置是否是暗黑模式，目前仅适用于浮窗广告
        ADSuyiSdk.getInstance().setDarkMode(cbDarkMode.isChecked());
        ADSuyiDemoConstant.FLOATING_AD_DARK_MODE = cbDarkMode.isChecked();

        if (rbPause.isChecked()) {
            // 暂停浮窗广告
            ADSuyiSdk.getInstance().pauseFloatingAd();
            ADSuyiDemoConstant.FLOATING_AD_IS_PAUSED = true;
        } else if (rbRestart.isChecked()) {
            // 重新开启浮窗广告
            ADSuyiSdk.getInstance().restartFloatingAd();
            ADSuyiDemoConstant.FLOATING_AD_IS_PAUSED = false;
        }

        ADSuyiToastUtil.show(this, "修改成功");
        finish();
    }
}
