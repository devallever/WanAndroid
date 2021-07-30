package com.xm.lib.manager.engine;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.widget.ImageView;

import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;

import java.io.File;
import java.lang.ref.WeakReference;
import java.lang.reflect.Method;

/**
 * Created by BaseMvpLibs.
 * Author: Jerry.
 * Date: 2020/9/22:18:15.
 * Desc:
 */
public class LoadGlide {

    private static LoadGlide loadGlide;
    private Builder builder;

    private LoadGlide() {
    }

    public static LoadGlide from(Context context) {
        if (null == loadGlide) {
            synchronized (LoadGlide.class) {
                if (null == loadGlide) {
                    loadGlide = new LoadGlide();
                }
            }
        }
        loadGlide.init(context);
        return loadGlide;
    }

    private void init(Context mContext) {
        builder = new Builder(mContext);
    }

    public LoadGlide corner(float radius) {
        builder.corner(radius);
        return this;
    }

    public LoadGlide asGif() {
        builder.asGif();
        return this;
    }

    public LoadGlide asFile() {
        builder.asFile();
        return this;
    }

    public LoadGlide asThumbnail() {
        builder.asThumbnail();
        return this;
    }

    public LoadGlide placeholderColorId(@ColorRes int colorId) {
        builder.placeholderColorId(colorId);
        return this;
    }

    public LoadGlide placeholderColor(@ColorInt int color) {
        builder.placeholderColorId(color);
        return this;
    }

    public LoadGlide error(@DrawableRes int drawableId) {
        builder.error(drawableId);
        return this;
    }

    public LoadGlide error(Drawable drawable) {
        builder.error(drawable);
        return this;
    }

    public LoadGlide circleCrop() {
        builder.circleCrop();
        return this;
    }

    public LoadGlide centerCrop() {
        builder.centerCrop();
        return this;
    }

    public LoadGlide size(int cropWidth, int cropHeight) {
        builder.size(cropWidth, cropHeight);
        return this;
    }

    public LoadGlide size(int size) {
        builder.size(size);
        return this;
    }

    public void load(String uri, ImageView view) {
        loadImage(uri, view);
    }

    public void load(Uri uri, ImageView view) {
        loadImage(uri, view);
    }

    public void load(Object uri, ImageView view) {
        loadImage(uri, view);
    }

    public void load(String uri, OnTargetCallBack target) {
        loadImage(uri, target);
    }

    public void load(Uri uri, OnTargetCallBack target) {
        loadImage(uri, target);
    }

    public void load(Object uri, OnTargetCallBack target) {
        loadImage(uri, target);
    }

    private void loadImage(Object uri, ImageView view) {
        if (builder.isThumbnail) {
            RequestBuilder<Drawable> load = Glide.with(builder.mContext).load(uri);
            Glide.with(builder.mContext).load(uri).apply(builder.options).thumbnail(load).into(view);
            return;
        }
        if (builder.isGif) {
            Glide.with(builder.mContext).asGif().load(uri).apply(builder.options).into(view);
            return;
        }
        Glide.with(builder.mContext).load(uri).apply(builder.options).into(view);
    }

    private void loadImage(Object uri, final OnTargetCallBack target) {
        if (builder.isFile) {
            Glide.with(builder.mContext).asFile().load(uri).apply(builder.options).into(new CustomTarget<File>() {
                @Override
                public void onResourceReady(@NonNull File resource, @Nullable Transition<? super File> transition) {
                    if (null != target) {
                        callback(target, "onLoaded", resource);
                    }
                }

                @Override
                public void onLoadCleared(@Nullable Drawable placeholder) {
                    if (null != target) {
                        callback(target, "onClean", null);
                    }
                }
            });
        } else {
            Glide.with(builder.mContext).asBitmap().load(uri).apply(builder.options).into(new CustomTarget<Bitmap>() {
                @Override
                public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                    if (null != target) {
                        callback(target, "onLoaded", resource);
//                        callback(target, "onClean", null);
                    }
                }

                @Override
                public void onLoadCleared(@Nullable Drawable placeholder) {
                    if (null != target) {
                        callback(target, "onClean", null);
                    }
                }
            });
        }
    }

    private void callback(OnTargetCallBack target, String methodName, Object resource) {
        try {
            Method[] methods = target.getClass().getDeclaredMethods();
            for (int i = 0; i < methods.length; i++) {
                Method method = methods[i];
                if (methodName.equals(method.getName())) {
                    method.setAccessible(true);
                    if (null == resource) {
                        method.invoke(target);
                    } else {
                        method.invoke(target, resource);
                    }
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    private class Builder {
        public Context mContext;
        public RequestOptions options;
        public boolean isGif = false;
        public boolean isFile = false;
        public boolean isThumbnail = false;

        public Builder(Context mContext) {
            this.mContext = new WeakReference<>(mContext).get();
            options = new RequestOptions();
        }

        public void corner(float radius) {
            options.centerCrop().transform(new CornerTransform(mContext, radius));
        }

        public void asGif() {
            this.isGif = true;
        }

        public void asFile() {
            this.isFile = true;
        }

        public void asThumbnail() {
            this.isThumbnail = true;
        }

        public void placeholderColorId(@ColorRes int colorId) {
            options.placeholder(new ColorDrawable(mContext.getResources().getColor(colorId)));
        }

        public void placeholderColor(@ColorInt int color) {
            options.placeholder(new ColorDrawable(color));
        }

        public void error(@DrawableRes int drawableId) {
            options.error(drawableId);
        }

        public void error(Drawable drawable) {
            options.error(drawable);
        }

        public void circleCrop() {
            options.circleCrop();
        }

        public void centerCrop() {
            options.centerCrop();
        }

        public void size(int cropWidth, int cropHeight) {
            options.override(cropWidth, cropHeight);
        }

        public void size(int size) {
            options.override(size);
        }
    }

    public abstract static class OnTargetCallBack<T> {
        protected abstract void onLoaded(T obj);

        protected void onClean() {
        }
    }

}
