package cn.admobiletop.adsuyidemo.adapter.holder;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import cn.admobiletop.adsuyi.ad.data.ADSuyiNativeFeedAdInfo;
import cn.admobiletop.adsuyidemo.R;

/**
 * 没有MediaView的ViewHolder
 */
public class NativeFeedAdViewHolder extends BaseNativeFeedAdViewHolder {
    private final ImageView ivImage;

    public NativeFeedAdViewHolder(@NonNull ViewGroup viewGroup) {
        /**
         * 已预设4种样式，开发者可自行修改，关闭按钮大小等可根据产品需求自行修改
         * R.layout.item_native_ad_native_ad 双图双文
         * R.layout.item_native_ad_native_ad2 上图下文
         * R.layout.item_native_ad_native_ad3 单图
         * R.layout.item_native_ad_native_ad4 左图双文
         */
        super(viewGroup, R.layout.item_native_ad_native_ad2);
        ivImage = itemView.findViewById(R.id.ivImage);
    }

    @Override
    protected void setImageOrMediaData(Context context, ADSuyiNativeFeedAdInfo nativeFeedAdInfo) {
        Glide.with(context).load(nativeFeedAdInfo.getImageUrl()).into(ivImage);
    }
}
