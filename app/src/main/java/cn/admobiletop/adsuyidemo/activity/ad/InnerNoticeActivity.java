package cn.admobiletop.adsuyidemo.activity.ad;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import cn.admobiletop.adsuyi.ad.inner.ADSuyiInnerNoticeListener;
import cn.admobiletop.adsuyi.ad.inner.ADSuyiInnerNoticeManager;
import cn.admobiletop.adsuyi.util.ADSuyiToastUtil;
import cn.admobiletop.adsuyidemo.R;
import cn.admobiletop.adsuyidemo.activity.base.BaseAdActivity;
import cn.admobiletop.adsuyidemo.activity.setting.SettingActivity;
import cn.admobiletop.adsuyidemo.util.SPUtil;

/**
 * @Description:
 * @Author: 草莓
 * @CreateDate: 6/27/22 7:44 PM
 */
public class InnerNoticeActivity extends BaseAdActivity {

    private Switch switchInnerNotice;
    private Button btnLoadAd;
    private TextView tvMessage;
    private StringBuffer stringBuffer = new StringBuffer();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inner_notice);

        switchInnerNotice = findViewById(R.id.switchInnerNotice);
        btnLoadAd = findViewById(R.id.btnLoadAd);
        tvMessage = findViewById(R.id.tvMessage);

        boolean isOpenFloatingAd = SPUtil.getBoolean(this, SettingActivity.KEY_OPEN_FLOATING_AD, true);
        switchInnerNotice.setChecked(isOpenFloatingAd);
        if (isOpenFloatingAd) {
            btnLoadAd.setVisibility(View.GONE);
        } else {
            btnLoadAd.setVisibility(View.VISIBLE);
        }


        switchInnerNotice.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    btnLoadAd.setVisibility(View.GONE);
                } else {
                    btnLoadAd.setVisibility(View.VISIBLE);
                }
                SPUtil.putBoolean(InnerNoticeActivity.this, SettingActivity.KEY_OPEN_FLOATING_AD, isChecked);
                ADSuyiToastUtil.show(InnerNoticeActivity.this, "请重启应用");
            }
        });

        btnLoadAd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stringBuffer = new StringBuffer();
                // 获取并展示广告
                ADSuyiInnerNoticeManager.getInstance().loadInnerNoticeAd(InnerNoticeActivity.this, new ADSuyiInnerNoticeListener() {
                    @Override
                    public void onAdFailed(String errorMessage) {
                        // 浮窗广告获取失败回调
                        setTvMessage(errorMessage);
                    }

                    @Override
                    public void onAdSuccess() {
                        // 浮窗广告展示成功回调
                        setTvMessage("广告获取成功");
                    }

                    @Override
                    public void onAdDelay(int i) {
                        // 浮窗广告距离下一次可展示剩余时长
                        setTvMessage("广告可展示剩余倒计时：" + i + "s时长");
                    }
                });
            }
        });
    }

    public void setTvMessage(String msg) {
        stringBuffer.append(msg);
        tvMessage.setText(stringBuffer.toString());
    }

}
