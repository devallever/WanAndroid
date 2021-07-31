package com.everdeng.android.app.wanandroid.ui.adapter

import android.view.ViewGroup
import com.everdeng.android.app.wanandroid.ui.adapter.bean.ArticleItem
import com.everdeng.android.app.wanandroid.ui.adapter.view.ArticleItemImgView
import com.everdeng.android.app.wanandroid.ui.adapter.view.ArticleItemView
import com.xm.lib.base.adapter.recyclerview.BaseRecyclerAdapter
import com.xm.lib.base.adapter.recyclerview.BaseViewHolder

class ArticleItemAdapter : BaseRecyclerAdapter<ArticleItem, BaseViewHolder<ArticleItem>>() {
    override fun bindViewHolder(parent: ViewGroup?, viewType: Int): BaseViewHolder<ArticleItem> =
        BaseViewHolder(if (viewType == 0) {
            ArticleItemView(mContext)
        } else {
            ArticleItemImgView(mContext)
        })

    override fun registerViewType(data: ArticleItem?, position: Int): Int {
        return data?.type?:0
    }
}