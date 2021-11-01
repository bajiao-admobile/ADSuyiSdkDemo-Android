package cn.admobiletop.adsuyidemo.adapter.holder;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import cn.admobiletop.adsuyi.ad.data.ADSuyiNativeFeedAdInfo;
import cn.admobiletop.adsuyi.util.ADSuyiViewUtil;
import cn.admobiletop.adsuyidemo.R;

/**
 * 包含MediaView的ViewHolder
 */
public class NativeFeedAdMediaViewHolder extends BaseNativeFeedAdViewHolder {
    private final FrameLayout flMediaContainer;

    public NativeFeedAdMediaViewHolder(@NonNull ViewGroup viewGroup) {
        /**
         * 已预设3种样式，开发者可自行修改，关闭按钮大小等可根据产品需求自行修改
         * R.layout.item_native_ad_native_ad_media 单图单视频双文
         * R.layout.item_native_ad_native_ad_media2 上视频下文
         * R.layout.item_native_ad_native_ad_media3 单视频
         */
        super(viewGroup, R.layout.item_native_ad_native_ad_media3);
        flMediaContainer = itemView.findViewById(R.id.flMediaContainer);
    }

    @Override
    protected void setImageOrMediaData(Context context, ADSuyiNativeFeedAdInfo nativeFeedAdInfo) {
        // 当前信息流原生广告，获取的是多媒体视图（可能是视频、或者图片之类的），mediaView不为空时强烈建议进行展示
        View mediaView = nativeFeedAdInfo.getMediaView(flMediaContainer);
        // 将广告视图添加到容器中的便捷方法，mediaView为空会移除flMediaContainer的所有子View
        ADSuyiViewUtil.addAdViewToAdContainer(flMediaContainer, mediaView);
    }
}
