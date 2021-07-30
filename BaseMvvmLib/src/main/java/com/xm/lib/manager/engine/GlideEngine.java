package com.xm.lib.manager.engine;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;

/**
 * Glide加载图片工具
 *
 * @Author Jerry
 * @create at 2020/3/4 16:18
 */
public class GlideEngine {

    /**
     * 加载图片,当加载失败显示默认的图片
     *
     * @param mContext
     * @param loadUri  加载的图片资源路劲
     * @param view     显示图片的View
     */
    public static void loadImage(Context mContext, Object loadUri, ImageView view) {
        Glide.with(mContext).load(loadUri).into(view);
    }

    /**
     * 加载图片,当加载失败显示默认的图片
     *
     * @param mContext
     * @param loadUri  加载的图片资源路劲
     * @param errorUri 失败的默认图片资源
     * @param view     显示图片的View
     */
    public static void loadImage(Context mContext, Object loadUri, Drawable errorUri, ImageView view) {
        RequestOptions options = new RequestOptions();
        if (null != errorUri) {
            options.error(errorUri);
        }
        Glide.with(mContext).load(loadUri).apply(options).into(view);
    }

    /**
     * 加载图片,当加载失败显示默认的图片
     *
     * @param mContext
     * @param loadUri  加载的图片资源路劲
     * @param errorUri 失败的默认图片资源
     * @param width    裁剪图片的宽度
     * @param height   裁剪图片的高度
     * @param view     显示图片的View
     */
    public static void loadImage(Context mContext, Object loadUri, Drawable errorUri, int width, int height, ImageView view) {
        RequestOptions options = new RequestOptions();
        if (null != errorUri) {
            options.error(errorUri);
        }
        if (0 != width && 0 != height) {
            options.override(width, height);
        }
        Glide.with(mContext).load(loadUri).apply(options).into(view);
    }

    /**
     * 加载Gif图片,当加载失败显示默认的图片
     *
     * @param mContext
     * @param loadUri  加载的图片资源路劲
     * @param errorUri 失败的默认图片资源
     * @param view     显示图片的View
     */
    public static void loadGif(Context mContext, Object loadUri, ImageView view) {
        Glide.with(mContext).asGif().load(loadUri).into(view);
    }

    /**
     * 加载Gif图片,当加载失败显示默认的图片
     *
     * @param mContext
     * @param loadUri  加载的图片资源路劲
     * @param errorUri 失败的默认图片资源
     * @param view     显示图片的View
     */
    public static void loadGif(Context mContext, Object loadUri, Drawable errorUri, ImageView view) {
        RequestOptions options = new RequestOptions();
        if (null != errorUri) {
            options.error(errorUri);
        }
        Glide.with(mContext).asGif().load(loadUri).apply(options).into(view);
    }

    /**
     * 图片资源变成Bitmap
     *
     * @param mContext
     * @param loadUri          加载的图片资源路劲
     * @param onBitmapCallBack bitmap对象回调
     */
    public static void imageToBitmap(Context mContext, Object loadUri, final OnBitmapCallBack onBitmapCallBack) {
        Glide.with(mContext).asBitmap().load(loadUri)
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        if (null != onBitmapCallBack) {
                            onBitmapCallBack.onLoading(resource);
                        }
                    }
                });
    }

    /**
     * 图片资源变成Bitmap
     *
     * @param mContext
     * @param loadUri          加载的图片资源路劲
     * @param onBitmapCallBack bitmap对象回调
     */
    public static void imageCenterCropToBitmap(Context mContext, Object loadUri, final OnBitmapCallBack onBitmapCallBack) {
        RequestOptions options = new RequestOptions();
        options.centerCrop();
        Glide.with(mContext).asBitmap().load(loadUri)
                .apply(options)
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        if (null != onBitmapCallBack) {
                            onBitmapCallBack.onLoading(resource);
                        }
                    }
                });
    }


    /**
     * 根据大小从图片中减裁剪图片
     *
     * @param mContext
     * @param loadUri    加载的图片资源路劲
     * @param cropWidth  裁剪图片的宽度
     * @param cropHeight 裁剪图片的高度
     * @param view       显示图片的View
     */
    public static void centerCropImageLoading(Context mContext, Object loadUri
            , int cropWidth, int cropHeight, ImageView view) {
        RequestOptions options = new RequestOptions();
        options.centerCrop();
        if (0 != cropWidth && 0 != cropHeight) {
            options.override(cropWidth, cropHeight);
        }
        Glide.with(mContext).load(loadUri).apply(options).into(view);

    }

    /**
     * 根据大小从图片中减裁剪图片
     *
     * @param mContext
     * @param loadUri    加载的图片资源路劲
     * @param errorUri   失败的默认图片资源
     * @param cropWidth  裁剪图片的宽度
     * @param cropHeight 裁剪图片的高度
     * @param view       显示图片的View
     */
    public static void centerCropImageLoading(Context mContext, Object loadUri, Drawable errorUri
            , int cropWidth, int cropHeight, ImageView view) {
        RequestOptions options = new RequestOptions();
        if (null != errorUri) {
            options.error(errorUri);
        }
        options.centerCrop();
        if (0 != cropWidth && 0 != cropHeight) {
            options.override(cropWidth, cropHeight);
        }
        Glide.with(mContext).load(loadUri).apply(options).into(view);

    }

    /**
     * 根据大小从图片中减裁剪图片
     *
     * @param mContext
     * @param loadUri    加载的图片资源路劲
     * @param errorUri   失败的默认图片资源
     * @param cropWidth  裁剪图片的宽度
     * @param cropHeight 裁剪图片的高度
     * @param view       显示图片的View
     */
    public static void centerCropImageLoading(Context mContext, Object loadUri
            , int cropWidth, int cropHeight, final OnBitmapCallBack onBitmapCallBack) {
        RequestOptions options = new RequestOptions();
        options.centerCrop();
        if (0 != cropWidth && 0 != cropHeight) {
            options.override(cropWidth, cropHeight);
        }
        Glide.with(mContext).asBitmap().load(loadUri).apply(options).into(new CustomTarget<Bitmap>() {
            @Override
            public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                if (null != onBitmapCallBack) {
                    onBitmapCallBack.onLoading(resource);
                }
            }

            @Override
            public void onLoadCleared(@Nullable Drawable placeholder) {

            }
        });

    }

    /**
     * 根据大小从图片中减裁剪图片
     *
     * @param mContext
     * @param loadUri    加载的图片资源路劲
     * @param errorUri   失败的默认图片资源
     * @param cropWidth  裁剪图片的宽度
     * @param cropHeight 裁剪图片的高度
     * @param view       显示图片的View
     */
    public static void loadImage(Context mContext, Object loadUri
            , final OnBitmapCallBack onBitmapCallBack) {
        Glide.with(mContext).asBitmap().load(loadUri).into(new CustomTarget<Bitmap>() {
            @Override
            public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                if (null != onBitmapCallBack && null != resource && !resource.isRecycled()) {
                    onBitmapCallBack.onLoading(resource);
                }
            }

            @Override
            public void onLoadCleared(@Nullable Drawable placeholder) {

            }
        });

    }

    /**
     * 根据大小圆形裁剪图片
     *
     * @param mContext
     * @param loadUri    加载的图片资源路劲
     * @param errorUri   失败的默认图片资源
     * @param cropWidth  裁剪图片的宽度
     * @param cropHeight 裁剪图片的高度
     * @param view       显示图片的View
     */
    public static void circleCropImageLoading(Context mContext, Object loadUri, ImageView view) {
        RequestOptions options = new RequestOptions();
        options.circleCrop();
        Glide.with(mContext).load(loadUri).apply(options).into(view);
    }

    /**
     * 根据大小圆形裁剪图片
     *
     * @param mContext
     * @param loadUri    加载的图片资源路劲
     * @param errorUri   失败的默认图片资源
     * @param cropWidth  裁剪图片的宽度
     * @param cropHeight 裁剪图片的高度
     * @param view       显示图片的View
     */
    public static void circleCropImageLoading(Context mContext, Object loadUri
            , int cropWidth, int cropHeight, ImageView view) {
        RequestOptions options = new RequestOptions();
        options.circleCrop();
        if (0 != cropWidth && 0 != cropHeight) {
            options.override(cropWidth, cropHeight);
        }
        Glide.with(mContext).load(loadUri).apply(options).into(view);
    }

    /**
     * 根据大小圆形裁剪图片
     *
     * @param mContext
     * @param loadUri    加载的图片资源路劲
     * @param errorUri   失败的默认图片资源
     * @param cropWidth  裁剪图片的宽度
     * @param cropHeight 裁剪图片的高度
     * @param view       显示图片的View
     */
    public static void circleCropImageLoading(Context mContext, Object loadUri, Drawable errorUri
            , int cropWidth, int cropHeight, ImageView view) {
        RequestOptions options = new RequestOptions();
        if (null != errorUri) {
            options.error(errorUri);
        }
        options.circleCrop();
        if (0 != cropWidth && 0 != cropHeight) {
            options.override(cropWidth, cropHeight);
        }
        Glide.with(mContext).load(loadUri).apply(options).into(view);
    }

    /**
     * 根据大小裁剪圆角图片
     *
     * @param mContext
     * @param loadUri    加载的图片资源路劲
     * @param errorUri   失败的默认图片资源
     * @param cropWidth  裁剪图片的宽度
     * @param cropHeight 裁剪图片的高度
     * @param radius     圆角角度
     * @param view       显示图片的View
     */
    public static void circleRadiusImageLoading(Context mContext, Object loadUri, Drawable errorUri, int cropWidth, int cropHeight
            , int radius, ImageView view) {
        RequestOptions options = new RequestOptions();
        if (null != errorUri) {
            options.error(errorUri);
        }
        if (0 != cropWidth && 0 != cropHeight) {
            options.override(cropWidth, cropHeight);
        }
        options.circleCropTransform();
        options.transform(new RoundedCorners(radius));
        Glide.with(mContext).load(loadUri).apply(options).into(view);
    }

    /**
     * 加载缩略图
     *
     * @param mContext
     * @param loadUri    加载的图片资源路劲
     * @param errorUri   失败的默认图片资源
     * @param cropWidth  裁剪图片的宽度
     * @param cropHeight 裁剪图片的高度
     * @param view       显示图片的View
     */
    public static void loadThumbnail(Context mContext, Object loadUri, Drawable errorUri, int cropWidth, int cropHeight, ImageView view) {
        RequestOptions options = new RequestOptions();
        if (null != errorUri) {
            options.error(errorUri);
        }
        if (0 != cropWidth && 0 != cropHeight) {
            options.override(cropWidth, cropHeight);
        }
        RequestBuilder<Drawable> load = Glide.with(mContext).load(loadUri);
        Glide.with(mContext).load(loadUri).apply(options).thumbnail(load).into(view);
    }

    /**
     * 加载圆角图片
     *
     * @param mContext
     * @param imageView 显示图片的View
     * @param filePath  图片地址
     * @param radius    圆角角度
     * @param color     背景颜色
     */
    public static void loadCornerImage(Context mContext, ImageView imageView, Object filePath, float radius, int color) {
        CornerTransform transform = new CornerTransform(mContext, radius);
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .transform(transform);
        if (0 != color) {
            ColorDrawable drawable = new ColorDrawable(color);
            options.placeholder(drawable);
        }
        Glide.with(mContext)
                .load(filePath)
                .apply(options)
                .into(imageView);
    }


    public interface OnBitmapCallBack {
        void onLoading(Bitmap resource);
    }
}
