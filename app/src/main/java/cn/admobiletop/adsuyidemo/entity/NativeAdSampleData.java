package cn.admobiletop.adsuyidemo.entity;

import cn.admobiletop.adsuyi.ad.data.ADSuyiNativeAdInfo;

/**
 * @author ciba
 * @description 信息流广告示例数据
 * @date 2020/4/1
 */
public class NativeAdSampleData {
    /**
     * 普通数据
     */
    private String normalData;
    /**
     * 信息流广告对象
     */
    private ADSuyiNativeAdInfo nativeAdInfo;

    public NativeAdSampleData(String normalData) {
        this.normalData = normalData;
    }

    public NativeAdSampleData(ADSuyiNativeAdInfo nativeAdInfo) {
        this.nativeAdInfo = nativeAdInfo;
    }

    public String getNormalData() {
        return normalData;
    }

    public ADSuyiNativeAdInfo getNativeAdInfo() {
        return nativeAdInfo;
    }
}
