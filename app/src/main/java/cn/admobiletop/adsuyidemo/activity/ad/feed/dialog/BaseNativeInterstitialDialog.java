package cn.admobiletop.adsuyidemo.activity.ad.feed.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;

import cn.admobiletop.adsuyi.ad.data.ADSuyiNativeAdInfo;

/**
 * @author : 草莓
 * @date : 2022/02/07
 * @description :
 */
public class BaseNativeInterstitialDialog extends Dialog {
    protected String TAG = "BaseNativeInterstitialDialog";

    public BaseNativeInterstitialDialog(@NonNull Context context) {
        super(context);
    }

    public void render(ADSuyiNativeAdInfo adSuyiNativeAdInfo) {

    }
}
