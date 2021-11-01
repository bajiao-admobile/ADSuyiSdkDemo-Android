package cn.admobiletop.adsuyidemo.activity.other;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import cn.admobiletop.adsuyi.ADSuyiSdk;
import cn.admobiletop.adsuyi.util.ADSuyiToastUtil;
import cn.admobiletop.adsuyidemo.R;
import cn.admobiletop.adsuyidemo.activity.base.BaseAdActivity;


public class ContentModuleActivity extends BaseAdActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content_module);

        findViewById(R.id.btnRecipeEntry).setOnClickListener(this);
        findViewById(R.id.btnRecipeTab).setOnClickListener(this);
        findViewById(R.id.btnNovelEntry).setOnClickListener(this);
        findViewById(R.id.btnNovelTab).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnRecipeEntry:
                Toast.makeText(this, "菜谱内容 待补充", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btnRecipeTab:
                Toast.makeText(this, "菜谱Tab 待补充", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btnNovelEntry:
                boolean openSuccess = ADSuyiSdk.getInstance().openNovelActivity();
                ADSuyiToastUtil.show(getApplicationContext(), openSuccess ? "打开成功" : "打开失败");
                break;
            case R.id.btnNovelTab:
                startActivity(FragmentActivity.class);
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
