package cn.admobiletop.adsuyidemo.activity.other;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import cn.admobiletop.adsuyidemo.R;
import cn.admobiletop.adsuyidemo.activity.base.BaseAdActivity;

public class DlModuleActivity extends BaseAdActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dl_module);

        findViewById(R.id.btnDlNormal).setOnClickListener(this);
        findViewById(R.id.btnDlSwitch).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnDlNormal:
                startActivity(DlAdActivity.class);
                break;
            case R.id.btnDlSwitch:
                startActivity(RewardAdSwitchActivity.class);
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
