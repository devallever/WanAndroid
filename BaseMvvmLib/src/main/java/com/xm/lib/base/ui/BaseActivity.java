package com.xm.lib.base.ui;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RelativeLayout;

import androidx.annotation.ColorRes;
import androidx.annotation.DrawableRes;
import androidx.annotation.IdRes;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LifecycleObserver;

import com.gyf.immersionbar.ImmersionBar;
import com.xm.lib.R;
import com.xm.lib.base.inters.IBaseView;
import com.xm.lib.manager.ActivityResult;
import com.xm.lib.manager.LifecycleManager;
import com.xm.lib.manager.LogPrint;
import com.xm.lib.manager.MeasureManager;
import com.xm.lib.manager.OpenManager;
import com.xm.lib.manager.PermissionsManager;

/**
 * Created by BaseMvpLibs.
 *
 * @Author: Jerry.
 * Date: 2020/10/9:14:05.
 * Desc:
 */
public abstract class BaseActivity extends AppCompatActivity implements IBaseView {

    protected View mTitleLayout;
    protected View mContentLayout;
    protected RelativeLayout mBgLayout;
    protected ImmersionBar mImmersionBar;

    @LayoutRes
    protected abstract int getLayoutId();

    protected abstract void initDataAndEvent();

    protected abstract void destroyView();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createRootView();
        initBaseRootView();
        if (isUseImmersionBar()) {
            setStatusBar();
        }
        initDataAndEvent();
        buildInit(savedInstanceState);
    }

    protected void buildInit(Bundle savedInstanceState){

    }

    protected final void setStatusBar() {
        if (isPaddingTop()) {
            int barHeight = MeasureManager.getStatusBarHeight(this);
            View mTopView = getChildView(initTopView());
            if (null != mTopView) {
                ViewGroup.MarginLayoutParams marginParams = (ViewGroup.MarginLayoutParams) mTopView.getLayoutParams();
                marginParams.topMargin = barHeight + marginParams.topMargin - dp2px(5);
                mTopView.setLayoutParams(marginParams);
            } else {
                mBgLayout.setPadding(0, barHeight, 0, 0);
            }
        }
        initImmersionBar();
    }

    /**
     * 初始化一些配置
     */
    protected void initConfig() {

    }


    private void createRootView() {
        //2020.11.06 jerry 修复使用ImmersionBar导致状态栏拉伸的问题
        setContentView(R.layout.activity_basic_layout);
        mTitleLayout = findViewById(R.id.rl_base_title_bar);
        mContentLayout = findViewById(R.id.rl_base_content_view);
        mBgLayout = findViewById(R.id.rl_base_bg_layout);
        initConfig();
    }

    /**
     * 设置内容布局是否margin StatusBarHeight 默认是false
     *
     * @return
     */
    public boolean isPaddingTop() {
        return null != mTitleLayout || 0 != initTopView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        LifecycleManager.getManager().putOwner(getClass().getSimpleName(),this);
        onHideOrDisplay(true);
    }

    @Override
    protected void onPause() {
        super.onPause();
        onHideOrDisplay(false);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        destroyView();
        LifecycleManager.getManager().remove(getClass().getSimpleName());
    }

    /**
     * 当前是否有网络连接
     *
     * @param netWorkState true 有网络连接
     */
    public void netChanged(boolean netWorkState) {

    }

    /**
     * 初始化布局文件
     */
    protected void initBaseRootView() {
        //初始化自定义标题栏
        ViewGroup.LayoutParams params = mTitleLayout.getLayoutParams();
        params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        mTitleLayout.setLayoutParams(params);
        mTitleLayout = null;

        //初始化内容
        View contentViewGroup = LayoutInflater.from(this).inflate(getLayoutId(), (ViewGroup) mContentLayout, true);
        mContentLayout.setBackground(contentViewGroup.getBackground());
        mBgLayout.setBackground(contentViewGroup.getBackground());
    }


    //=======================初始化DataBinding和初始化view方法 end==============//

    //=======================初始化状态栏方法 start==============//

    /**
     * 初始化ImmersionBar
     */
    @SuppressLint("ResourceType")
    private void initImmersionBar() {
//        //状态栏字体是深色，不写默认为亮色
        mImmersionBar = ImmersionBar.with(this)
                .statusBarDarkFont(isStatusBarDark())
//                //导航栏图标是深色，不写默认为亮色
                .navigationBarDarkIcon(isNavigationBarDark())
                //解决软键盘与底部输入框冲突问题，默认为false，还有一个重载方法，可以指定软键盘mode
                .keyboardEnable(isKeyboardEnable())
                //支持ActionBar使用
                .supportActionBar(false)
                .setOnKeyboardListener((isPopup, keyboardHeight) -> isKeyboardPopup(isPopup, keyboardHeight));

        int statusColor = statusColor();
        if (0 != statusColor) {
            mImmersionBar.statusBarColorInt(getColorRes(statusColor));
        }
        mImmersionBar.init();
    }

    @ColorRes
    protected int statusColor() {
        return 0;
    }

    /**
     * 导航栏是否设置深色
     *
     * @return
     */
    protected boolean isNavigationBarDark() {
        return false;
    }

    /**
     * 解决软键盘与底部输入框冲突问题，默认为false，还有一个重载方法，可以指定软键盘mode
     */
    protected boolean isKeyboardEnable() {
        return false;
    }

    /**
     * 状态栏字体是深色&导航栏图标是深色，不写默认为亮色
     */
    protected boolean isStatusBarDark() {
        return false;
    }

    /**
     * 输入法弹出回调
     *
     * @param isPopup        是否弹出输入法
     * @param keyboardHeight 输入法高度
     */
    protected void isKeyboardPopup(boolean isPopup, int keyboardHeight) {
    }

    /**
     * 是否使用ImmersionBar
     * 默认开启使用
     */
    protected boolean isUseImmersionBar() {
        return true;
    }

    /**
     * 防止沉浸式状态栏时vi布局整体往上顶的情况出现
     * 返回顶部view的id
     *
     * @return
     */
    @IdRes
    protected int initTopView() {
        return 0;
    }

    //=======================初始化状态栏方法 end==============//

    //=======================资源获取和测量尺寸方法 start==============//

    @Override
    public String getStringRes(@StringRes int stringId) {
        return getResources().getString(stringId);
    }

    @Override
    public Drawable getDrawableRes(@DrawableRes int resId, Resources.Theme theme) {
        return getResources().getDrawable(resId, theme);
    }

    @Override
    public int getColorRes(@ColorRes int colorId) {
        return getResources().getColor(colorId);
    }

    @Override
    public int dp2px(int value) {
        return MeasureManager.dip2px(this, value);
    }

    @Override
    public int px2dp(int value) {
        return MeasureManager.px2dp(this, value);
    }

    @Override
    public int sp2px(int value) {
        return MeasureManager.sp2px(this, value);
    }

    @Override
    public int getResultCode() {
        return OpenManager.getOpenManager().getResultCode(this);
    }
    //=======================资源获取和测量尺寸方法 end==============//

    //=======================调用启动Activity方法 start==============//

    @Override
    public void open(Class<? extends Activity> clz, boolean isCloseActivity) {
        OpenManager.getOpenManager().open(this, clz, isCloseActivity);
    }

    @Override
    public void open(Intent intent, boolean isCloseActivity) {
        OpenManager.getOpenManager().open(this, intent, isCloseActivity);
    }

    @Override
    public void open(int resultCode, Intent intent) {
        OpenManager.getOpenManager().open(this, intent, resultCode);
    }

    @Override
    public <V extends View> V getChildView(int viewId) {
        return findViewById(viewId);
    }

    //=======================调用启动Activity方法 end==============//

    /**
     * 设置权限请求返回
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionsManager.getInstance().onRequestPermissionsResult(this, requestCode, permissions, grantResults);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ActivityResult.getInstance().setActivityResult(this, requestCode, resultCode, data);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            hintInputKey(v, event);
            onTouchDownEvent(v, event);
        }
        return super.dispatchTouchEvent(event);
    }

    /**
     * view触摸事件监听
     *
     * @param v
     * @param event
     */
    protected void onTouchDownEvent(View v, MotionEvent event) {

    }

    /**
     * 隐藏输入法
     *
     * @param v
     * @param event
     */
    private void hintInputKey(View v, MotionEvent event) {
        if (v instanceof EditText) {
            Rect outRect = new Rect();
            v.getGlobalVisibleRect(outRect);
            if (!outRect.contains((int) event.getRawX(), (int) event.getRawY())) {
                v.clearFocus();
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
            }
        }
    }

    /**
     * 当前页面是否在交互状态
     *
     * @param isDisplay
     */
    protected void onHideOrDisplay(boolean isDisplay) {

    }


    @Override
    public void registerLifecycleObserver(LifecycleObserver observer) {
        if (null == observer) {
            return;
        }
        getLifecycle().addObserver(observer);
    }

    @Override
    public void unregisterLifecycleObserver(LifecycleObserver observer) {
        if (null == observer) {
            return;
        }
        getLifecycle().removeObserver(observer);
    }

    @Override
    public <V extends IBaseView> V getBaseView() {
        return (V) this;
    }
}
