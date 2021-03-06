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
     * ?????????????????????
     */
    protected void initConfig() {

    }


    private void createRootView() {
        //2020.11.06 jerry ????????????ImmersionBar??????????????????????????????
        setContentView(R.layout.activity_basic_layout);
        mTitleLayout = findViewById(R.id.rl_base_title_bar);
        mContentLayout = findViewById(R.id.rl_base_content_view);
        mBgLayout = findViewById(R.id.rl_base_bg_layout);
        initConfig();
    }

    /**
     * ????????????????????????margin StatusBarHeight ?????????false
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
     * ???????????????????????????
     *
     * @param netWorkState true ???????????????
     */
    public void netChanged(boolean netWorkState) {

    }

    /**
     * ?????????????????????
     */
    protected void initBaseRootView() {
        //???????????????????????????
        ViewGroup.LayoutParams params = mTitleLayout.getLayoutParams();
        params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        mTitleLayout.setLayoutParams(params);
        mTitleLayout = null;

        //???????????????
        View contentViewGroup = LayoutInflater.from(this).inflate(getLayoutId(), (ViewGroup) mContentLayout, true);
        mContentLayout.setBackground(contentViewGroup.getBackground());
        mBgLayout.setBackground(contentViewGroup.getBackground());
    }


    //=======================?????????DataBinding????????????view?????? end==============//

    //=======================???????????????????????? start==============//

    /**
     * ?????????ImmersionBar
     */
    @SuppressLint("ResourceType")
    private void initImmersionBar() {
//        //????????????????????????????????????????????????
        mImmersionBar = ImmersionBar.with(this)
                .statusBarDarkFont(isStatusBarDark())
//                //????????????????????????????????????????????????
                .navigationBarDarkIcon(isNavigationBarDark())
                //?????????????????????????????????????????????????????????false???????????????????????????????????????????????????mode
                .keyboardEnable(isKeyboardEnable())
                //??????ActionBar??????
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
     * ???????????????????????????
     *
     * @return
     */
    protected boolean isNavigationBarDark() {
        return false;
    }

    /**
     * ?????????????????????????????????????????????????????????false???????????????????????????????????????????????????mode
     */
    protected boolean isKeyboardEnable() {
        return false;
    }

    /**
     * ????????????????????????&????????????????????????????????????????????????
     */
    protected boolean isStatusBarDark() {
        return false;
    }

    /**
     * ?????????????????????
     *
     * @param isPopup        ?????????????????????
     * @param keyboardHeight ???????????????
     */
    protected void isKeyboardPopup(boolean isPopup, int keyboardHeight) {
    }

    /**
     * ????????????ImmersionBar
     * ??????????????????
     */
    protected boolean isUseImmersionBar() {
        return true;
    }

    /**
     * ???????????????????????????vi????????????????????????????????????
     * ????????????view???id
     *
     * @return
     */
    @IdRes
    protected int initTopView() {
        return 0;
    }

    //=======================???????????????????????? end==============//

    //=======================????????????????????????????????? start==============//

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
    //=======================????????????????????????????????? end==============//

    //=======================????????????Activity?????? start==============//

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

    //=======================????????????Activity?????? end==============//

    /**
     * ????????????????????????
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
     * view??????????????????
     *
     * @param v
     * @param event
     */
    protected void onTouchDownEvent(View v, MotionEvent event) {

    }

    /**
     * ???????????????
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
     * ?????????????????????????????????
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
