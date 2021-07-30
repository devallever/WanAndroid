package com.xm.lib.base.adapter.binding;

import android.graphics.Bitmap;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.xm.lib.manager.engine.LoadGlide;


/**
 * Created by BaseMvpLibs.
 * @Author: Jerry.
 * Date: 2020/10/12:10:08.
 * Desc:
 */
public class BindingImageAdapter {

    /**
     * 加载显示图片
     * @param imageView
     * @param uri
     * @param url
     * @param isThumbnail
     * @param isGif
     * @param imageWidth
     * @param imageHeight
     * @param isCenterCrop
     * @param corner
     */
    public static void loadImage(ImageView imageView, Object uri, String url, boolean isThumbnail, boolean isGif
            , int imageWidth, int imageHeight, boolean isCenterCrop, float corner) {
        Object path = TextUtils.isEmpty(url) ? uri : url;
        if (null == path) {
            return;
        }
        LoadGlide glide = isGif ? LoadGlide.from(imageView.getContext()).asGif()
                : isThumbnail ? LoadGlide.from(imageView.getContext()).asThumbnail()
                : LoadGlide.from(imageView.getContext());
        if (0 < imageWidth && 0 < imageHeight) {
            glide.size(imageWidth, imageHeight);
        }
        if (isCenterCrop) {
            glide.centerCrop();
        }
        if (0 < corner) {
            glide.corner(corner);
        }
        glide.load(path, imageView);
    }

    /**
     * 等比例加载显示图片
     * @param imageView
     * @param uri
     * @param isCenterCrop
     * @param corner
     * @param maxWidth
     * @param maxHeight
     */
    public static void loadSizeImage(ImageView imageView, String uri, boolean isCenterCrop, float corner, int maxWidth, int maxHeight) {
        if (null == uri) {
            return;
        }
        if (0 == maxWidth && 0 == maxHeight) {
            return;
        }
        LoadGlide.from(imageView.getContext()).load(uri, new LoadGlide.OnTargetCallBack<Bitmap>() {
            @Override
            protected void onLoaded(Bitmap bitmap) {
                int width = bitmap.getWidth();
                int height = bitmap.getHeight();
                int screenWidth;
                int screenHeight;
                if (0 == maxWidth) {
                    screenHeight = maxHeight;
                    screenWidth = screenHeight * width / height;
                } else {
                    screenWidth = maxWidth;
                    screenHeight = screenWidth * height / width;
                }
                ViewGroup.LayoutParams p;
                if (imageView.getLayoutParams() instanceof LinearLayout.LayoutParams) {
                    LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) imageView.getLayoutParams();
                    params.width = screenWidth;
                    params.height = screenHeight;
                    params.gravity = Gravity.CENTER;
                    p = params;
                }else if(imageView.getLayoutParams() instanceof RelativeLayout.LayoutParams){
                    RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) imageView.getLayoutParams();
                    params.width = screenWidth;
                    params.height = screenHeight;
                    params.addRule(RelativeLayout.CENTER_IN_PARENT);
                    p = params;
                }else if(imageView.getLayoutParams() instanceof FrameLayout.LayoutParams) {
                    FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) imageView.getLayoutParams();
                    params.width = screenWidth;
                    params.height = screenHeight;
                    params.gravity = Gravity.CENTER;
                    p = params;
                }else{
                    p = imageView.getLayoutParams();
                    p.width = screenWidth;
                    p.height = screenHeight;
                }
                imageView.setLayoutParams(p);

                LoadGlide glide = LoadGlide.from(imageView.getContext());
                if (isCenterCrop) {
                    glide.centerCrop();
                }
                if (0 < corner) {
                    glide.corner(corner);
                }
                glide.load(uri, imageView);
            }
        });
    }


}
