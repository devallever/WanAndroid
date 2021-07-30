package com.everdeng.android.app.wanandroid.ui.home.model

import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import com.everdeng.android.app.wanandroid.ui.adapter.ArticleItemAdapter
import com.everdeng.android.app.wanandroid.ui.adapter.bean.ArticleItem
import com.xm.lib.base.inters.IBaseView
import com.xm.lib.base.model.BaseViewModelKt

class HomeViewModel : BaseViewModelKt<IBaseView>() {

    private val mArticleItemList = mutableListOf<ArticleItem>()

    val articleItemListLiveData = MutableLiveData<List<ArticleItem>>()
    lateinit var layoutManager: LinearLayoutManager
    lateinit var adapter: ArticleItemAdapter
    override fun onCreated() {
        layoutManager = LinearLayoutManager(mCxt)
        adapter = ArticleItemAdapter()
        for (i in 0..10) {
            val articleItem = ArticleItem()
            articleItem.title = "标题: $i"
            articleItem.user = "user: $i"
            articleItem.time = System.currentTimeMillis().toString()
            articleItem.sort = "分类:$i"
            mArticleItemList.add(articleItem)
        }
        articleItemListLiveData.value = mArticleItemList
    }
}