package com.xm.lib.base.adapter.binding;

import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.BindingAdapter;

import com.xm.lib.manager.LogPrint;
import com.xm.lib.manager.MeasureManager;

/**
 * Created by Juzhengyuan
 *
 * @Author: Jerry.
 * @Date: 2020/12/17 14
 * @Desc:
 */
public class SizeDataBindingAdapter {

    @BindingAdapter(value = {"view_margin_top", "view_margin_left"
            , "view_margin_bottom", "view_margin_right", "view_width", "view_height"}, requireAll = false)
    public static void setMarginSize(View view, float marginTop, float marginLeft
            , float marginBottom, float marginRight, int viewWidth, int viewHeight) {
        if (null == view.getLayoutParams()) {
            return;
        }
        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
        params.width = 0 == viewWidth ? params.width : MeasureManager.dip2px(view.getContext(), viewWidth);
        params.height = 0 == viewHeight ? params.height : MeasureManager.dip2px(view.getContext(), viewHeight);
        params.topMargin = 0 == marginTop ? params.topMargin : (int) marginTop;
        params.leftMargin = 0 == marginLeft ? params.leftMargin : (int) marginLeft;
        params.bottomMargin = 0 == marginBottom ? params.bottomMargin : (int) marginBottom;
        params.rightMargin = 0 == marginRight ? params.rightMargin : (int) marginRight;
        view.setLayoutParams(params);
    }


    @BindingAdapter(value = "view_minHeight")
    public static void setMinHeight(View view, float minHeight) {
        if (null == view.getLayoutParams()) {
            return;
        }
        ViewGroup.LayoutParams params = view.getLayoutParams();
        params.height = params.height < minHeight ? (int) minHeight : params.height;
        LogPrint.e("params.height -> " + params.height + "===min:" + minHeight);
        LogPrint.e("view.getHeight() -> " + view.getHeight());
        view.setLayoutParams(params);
    }
}
