package com.everdeng.android.app.wanandroid.ui.article

import com.everdeng.android.app.wanandroid.BR
import com.everdeng.android.app.wanandroid.R
import com.everdeng.android.app.wanandroid.base.BaseFragment2
import com.everdeng.android.app.wanandroid.databinding.FragmentArticleListBinding
import com.everdeng.android.app.wanandroid.ui.article.model.ArticleListViewModel
import com.xm.lib.base.config.DataBindingConfig
import com.xm.lib.base.ui.BaseFragment

class ArticleListFragment: BaseFragment2<FragmentArticleListBinding, ArticleListViewModel>() {
    override fun initDataBindingConfig() = DataBindingConfig(R.layout.fragment_article_list, BR.articleListViewModel)

    override fun initDataAndEvent() {
        initRefresh()
    }

    override fun destroyView() {
    }

    private fun initRefresh() {
        mBinding.refreshLayout.setOnRefreshListener { mViewModel.adapter.refresh() }
    }

}