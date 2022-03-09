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
        findViewById(R.id.btnNovelEntry).setOnClickListener(this);
        findViewById(R.id.btnNovelTab).setOnClickListener(this);

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

        // 初始化小说sdk（目前小说已在ADSuyiSdk中初始化，所以开发者不需要单独初始化）
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
            case R.id.btnNovelEntry:
                boolean openNovelSuccess = ADSuyiSdk.getInstance().openNovelActivity();
                ADSuyiToastUtil.show(getApplicationContext(), openNovelSuccess ? "打开成功" : "打开失败");
                break;
            case R.id.btnNovelTab:
                startActivity(FragmentAndNovelActivity.class);
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
