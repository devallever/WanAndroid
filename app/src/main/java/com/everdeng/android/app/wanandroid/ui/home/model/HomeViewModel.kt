package com.everdeng.android.app.wanandroid.ui.home.model

import com.everdeng.android.app.wanandroid.ui.article.adapter.bean.ArticleItem
import com.everdeng.android.app.wanandroid.ui.article.model.ArticleListViewModel
import com.everdeng.android.app.wanandroid.ui.home.paging.HomePageDataSource
import com.xm.lib.base.paging.BasePageSource

class HomeViewModel : ArticleListViewModel() {
    override fun getPageDataSource(): BasePageSource<ArticleItem> = HomePageDataSource()
}