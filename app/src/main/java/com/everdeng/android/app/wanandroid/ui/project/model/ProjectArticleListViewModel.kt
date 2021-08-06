package com.everdeng.android.app.wanandroid.ui.project.model

import com.everdeng.android.app.wanandroid.ui.article.adapter.bean.ArticleItem
import com.everdeng.android.app.wanandroid.ui.article.model.ArticleListViewModel
import com.everdeng.android.app.wanandroid.ui.project.paging.ProjectArticlePageDataSource
import com.xm.lib.base.paging.BasePageSource

class ProjectArticleListViewModel: ArticleListViewModel() {
    override fun getPageDataSource(): BasePageSource<ArticleItem> = ProjectArticlePageDataSource()
}