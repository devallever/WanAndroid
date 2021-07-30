package com.everdeng.android.app.wanandroid.ui.adapter.model

import androidx.databinding.ObservableField
import com.xm.lib.base.adapter.recyclerview.BaseRvCustomViewModelKt

class ArticleItemViewModel: BaseRvCustomViewModelKt() {

    val username = ObservableField<String>()
    val title = ObservableField<String>()
    val time = ObservableField<String>()
    val sort = ObservableField<String>()

}