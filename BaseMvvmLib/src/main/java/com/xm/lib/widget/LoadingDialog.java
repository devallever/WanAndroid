package com.xm.lib.widget;

import android.content.Context;
import android.view.Gravity;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;

import com.xm.lib.BR;
import com.xm.lib.R;
import com.xm.lib.base.config.DataBindingConfig;
import com.xm.lib.base.model.BaseDialogViewModel;
import com.xm.lib.base.ui.BaseDialog;


/**
 * @author Administrator
 */
public class LoadingDialog extends BaseDialog<LoadingDialog.LoadingDialogViewModel> {

    private String toastMessage;

    public LoadingDialog(@NonNull Context context, String toastMessage) {
        super(context,new DataBindingConfig(R.layout.dialog_loading_layout, BR.loadViewModel),R.style.LoadingTheme);
        this.toastMessage = toastMessage;
    }

    @Override
    protected LoadingDialogViewModel initViewModel() {
        return new LoadingDialogViewModel();
    }

    @Override
    protected void initView() {
        setCancelable(false);
        setCanceledOnTouchOutside(false);
        mViewModel.setToastMsg(toastMessage);
    }

    @Override
    protected void setLayoutAttributes(WindowManager.LayoutParams attributes, int widthPixels, int heightPixels) {
        attributes.width = (int) (widthPixels * 0.45);
        attributes.height = attributes.width;
        attributes.gravity = Gravity.CENTER;
    }

    public class LoadingDialogViewModel extends BaseDialogViewModel {

        public ObservableField<String> toastMsg = new ObservableField<>();

        public void setToastMsg(String toastMsg) {
            this.toastMsg.set(toastMsg);
        }

        @Override
        protected void destroyEvent() {

        }
    }
}
