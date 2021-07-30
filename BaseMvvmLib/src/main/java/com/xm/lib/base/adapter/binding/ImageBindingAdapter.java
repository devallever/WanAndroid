package com.xm.lib.base.adapter.binding;

import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

/**
 * Created by Android Studio.
 *
 * @author Jerry
 * Date: 2020/11/5 21:24
 * @description:
 */
public class ImageBindingAdapter {

    @BindingAdapter(
            value = {"srcRes", "uri", "isThumbnail", "isGif"
                    , "image_width", "image_height", "center_crop", "corner"}
            , requireAll = false)
    public static void loadImage(ImageView imageView, Object uri, String url, boolean isThumbnail, boolean isGif
            , int imageWidth, int imageHeight, boolean isCenterCrop, float corner) {
        BindingImageAdapter.loadImage(imageView, uri, url, isThumbnail, isGif, imageWidth, imageHeight, isCenterCrop, corner);
    }


//    @BindingAdapter("images")
//    public static void setAvatars(AvatarListLayout avatarLayout, List<String> images){
//        if (null == images || images.isEmpty()) {
//            return;
//        }
//        avatarLayout.setAvatars(images);
//    }
}
