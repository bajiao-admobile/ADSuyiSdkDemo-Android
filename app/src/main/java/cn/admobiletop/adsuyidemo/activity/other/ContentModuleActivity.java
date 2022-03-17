package cn.admobiletop.adsuyidemo.activity.other;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import cn.admobiletop.adsuyi.ADSuyiSdk;
import cn.admobiletop.adsuyi.util.ADSuyiToastUtil;
import cn.admobiletop.adsuyidemo.BuildConfig;
import cn.admobiletop.adsuyidemo.R;
import cn.admobiletop.adsuyidemo.activity.base.BaseAdActivity;
import cn.admobiletop.adsuyidemo.constant.ADSuyiDemoConstant;
import cn.admobiletop.cookbook.support.CookbookSDKManger;
import cn.admobiletop.cookbook.support.config.ADSuyiAdIdConfig;
import cn.admobiletop.cookbook.support.config.ADSuyiAdRecipeConfig;


public class ContentModuleActivity extends BaseAdActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content_module);

        findViewById(R.id.btnRecipeEntry).setOnClickListener(this);
        findViewById(R.id.btnRecipeTab).setOnClickListener(this);

        // 初始化菜谱sdk
        CookbookSDKManger.getInstance().init(this, new ADSuyiAdRecipeConfig.Builder()
                .setAppId("100602")
                .setAppSecret("29e1eb7f0710e44be02694e66f4e9272")
                .build());

        CookbookSDKManger.getInstance()
                .setAdSuyiAdIdConfig(new ADSuyiAdIdConfig.Builder()
                        // 设置信息流广告位ID
                        .setNativeAdId(ADSuyiDemoConstant.NATIVE_AD_POS_ID)
                        // 设置横幅广告位ID
                        .setBannerAdId(ADSuyiDemoConstant.BANNER_AD_POS_ID)
                        // 设置插屏广告位ID
                        .setInterstitialAdId(ADSuyiDemoConstant.INTERSTITIAL_AD_POS_ID)
                        .debug(BuildConfig.DEBUG)
                        .build());

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnRecipeEntry:
                CookbookSDKManger.getInstance().setHomeImmersion(false);
                boolean openCookbookSuccess = CookbookSDKManger.getInstance().openCookbookActivity();
                ADSuyiToastUtil.show(getApplicationContext(), openCookbookSuccess ? "打开成功" : "打开失败");
                break;
            case R.id.btnRecipeTab:
                startActivity(FragmentAndCookbookActivity.class);
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
