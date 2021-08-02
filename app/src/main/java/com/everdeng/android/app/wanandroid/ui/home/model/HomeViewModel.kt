package com.everdeng.android.app.wanandroid.ui.home.model

import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.everdeng.android.app.wanandroid.ui.adapter.ArticleItemAdapter
import com.everdeng.android.app.wanandroid.ui.adapter.paging.ArticlePageDataSource
import com.xm.lib.base.inters.IBaseView
import com.xm.lib.base.model.BaseViewModelKt

class HomeViewModel : BaseViewModelKt<IBaseView>() {

    val homePageDataList = Pager(PagingConfig(pageSize = 20)) {
        ArticlePageDataSource()
    }.flow.cachedIn(viewModelScope)


    lateinit var adapter: ArticleItemAdapter
    override fun onCreated() {
//        layoutManager = LinearLayoutManager(mCxt)
//        adapter = ArticleItemAdapter()


//        viewModelScope.launch(Dispatchers.Main){
//            val response = NetRepository.getHomePageList(1) {
//
//            }
//
//            response?.datas?.map {
//                val articleItem = ArticleItem()
//                articleItem.title = it.title
//                articleItem.user = it.author
//                articleItem.time = it.publishTime.toString()
//                articleItem.sort = it.courseId.toString()
//                if (it.envelopePic.isEmpty()) {
//                    articleItem.type = 0
//                } else {
//                    articleItem.type = 1
//                    articleItem.cover = it.envelopePic
//                    articleItem.description = it.desc
//                }
//                mArticleItemList.add(articleItem)
//            }
//
//            articleItemListLiveData.value = mArticleItemList
//
//        }
    }
}