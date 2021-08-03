package com.everdeng.android.app.wanandroid.ui.article

import android.content.Context
import android.os.Bundle
import com.everdeng.android.app.wanandroid.BR
import com.everdeng.android.app.wanandroid.R
import com.everdeng.android.app.wanandroid.base.BaseDataActivity2
import com.everdeng.android.app.wanandroid.databinding.ActivityArticleBinding
import com.everdeng.android.app.wanandroid.ui.adapter.bean.ArticleItem
import com.everdeng.android.app.wanandroid.ui.article.model.ArticleViewModel
import com.xm.lib.base.config.DataBindingConfig
import com.xm.lib.manager.IntentManager
import com.xm.lib.util.WebViewConfig
import com.xm.lib.util.log

class ArticleActivity : BaseDataActivity2<ActivityArticleBinding, ArticleViewModel>() {

    companion object {
        private const val EXTRA_ARTICLE_ITEM = "EXTRA_ARTICLE_ITEM"
        fun start(context: Context, articleItem: ArticleItem) {
            log("articleUrl = ${articleItem.articleUrl}")
            val bundle = Bundle()
            bundle.putParcelable(EXTRA_ARTICLE_ITEM, articleItem)
            IntentManager.startActivity(context, ArticleActivity::class.java, bundle)
        }
    }

    override fun initDataAndEvent() {
        val articleItem = intent?.getParcelableExtra<ArticleItem>(EXTRA_ARTICLE_ITEM) ?: return
//        mBinding.webView.loadUrl(articleItem.articleUrl)
        val webViewConfig = WebViewConfig()
        webViewConfig.loadData(mBinding.webView, articleItem.articleUrl)
    }

    override fun initDataBindingConfig() =
        DataBindingConfig(R.layout.activity_article, BR.articleViewModel)

    override fun destroyView() {
    }
}