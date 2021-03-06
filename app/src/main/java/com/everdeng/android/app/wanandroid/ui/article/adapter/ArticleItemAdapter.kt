package com.everdeng.android.app.wanandroid.ui.article.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.everdeng.android.app.wanandroid.ui.article.adapter.bean.ArticleItem
import com.everdeng.android.app.wanandroid.ui.article.adapter.view.ArticleItemImgView
import com.everdeng.android.app.wanandroid.ui.article.adapter.view.ArticleItemView
import com.everdeng.android.app.wanandroid.ui.article.adapter.view.BannerItemView
import com.xm.lib.base.adapter.recyclerview.BaseRecyclerAdapter
import com.xm.lib.base.adapter.recyclerview.BaseViewHolder

class ArticleItemAdapter(diffCallback: DiffUtil.ItemCallback<ArticleItem>) :
    BaseRecyclerAdapter<ArticleItem, BaseViewHolder<ArticleItem>>(diffCallback) {
    override fun bindViewHolder(parent: ViewGroup?, viewType: Int): BaseViewHolder<ArticleItem> =
        BaseViewHolder(
            when (viewType) {
                -1 -> {
                    BannerItemView(mContext)
                }
                0 -> {
//                    log("bindViewHolder: viewType = $viewType")
                    ArticleItemView(mContext)
                }
                else -> {
//                    log("bindViewHolder: viewType = $viewType")
                    ArticleItemImgView(mContext)
                }
            }
        )

    override fun registerViewType(data: ArticleItem?, position: Int): Int {
        val itemType = data?.type ?: 0
//        log("registerViewType: itemType = $itemType")
        return itemType
    }
}