package com.everdeng.android.app.wanandroid.ui.adapter.view

import android.content.Context
import android.util.AttributeSet
import com.everdeng.android.app.wanandroid.BR
import com.everdeng.android.app.wanandroid.R
import com.everdeng.android.app.wanandroid.ui.adapter.bean.ArticleItem
import com.everdeng.android.app.wanandroid.ui.adapter.model.ArticleItemViewModel
import com.xm.lib.base.adapter.recyclerview.BaseRecyclerAdapter
import com.xm.lib.base.adapter.recyclerview.BaseRvCustomViewKt
import com.xm.lib.base.adapter.recyclerview.BaseViewHolder
import com.xm.lib.base.config.DataBindingConfig

class ArticleItemView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : BaseRvCustomViewKt<ArticleItem, ArticleItemViewModel>(context, attrs, defStyleAttr) {
    override fun getViewModel() = ArticleItemViewModel()

    override fun getDataBindingConfig() = DataBindingConfig(R.layout.rv_article_item, BR.articleItemViewModel)
    override fun convert(
        adapter: BaseRecyclerAdapter<*, out BaseViewHolder<*>>?,
        data: ArticleItem?,
        position: Int
    ) {
        mViewModel.sort.set(data?.sort)
        mViewModel.time.set(data?.time)
        mViewModel.title.set(data?.title)
        mViewModel.username.set(data?.user)
    }
}