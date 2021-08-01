package com.everdeng.android.app.wanandroid.ui.adapter.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.everdeng.android.app.wanandroid.function.network.NetRepository
import com.everdeng.android.app.wanandroid.ui.adapter.bean.ArticleItem
import com.xm.lib.util.log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ArticlePageDataSourde: PagingSource<Int, ArticleItem>() {

    private var pageCount = 1

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ArticleItem> {
        log("load ${params.key}")
        val currentLoadingPageKey = params.key ?: 1

        val prevKey = if (currentLoadingPageKey == 1) null else currentLoadingPageKey - 1

        val dataList = getData(currentLoadingPageKey)
        //如果没有了数据， next传null就好了
        if (currentLoadingPageKey.plus(1) == pageCount) {
            return LoadResult.Page(
                data = mutableListOf(),
                prevKey = prevKey,
                nextKey = null
            )
        }

        return LoadResult.Page(
            data = dataList,
            prevKey = prevKey,
            nextKey = currentLoadingPageKey.plus(1)
        )
    }

    override fun getRefreshKey(state: PagingState<Int, ArticleItem>): Int? {
        //调动 adapter.refresh() 之后
        log("getRefreshKey")
        return 1
    }

    private suspend fun getData(pageNum: Int) = withContext(Dispatchers.IO) {
        val result = mutableListOf<ArticleItem>()
        val response = NetRepository.getHomePageList(pageNum) {

        }
        pageCount = response?.pageCount?:1
        val dataList = response?.datas
        dataList?.map {
            val articleItem = ArticleItem()
            articleItem.title = it.title
            articleItem.user = it.author
            articleItem.time = it.publishTime.toString()
            articleItem.sort = it.courseId.toString()
            if (it.envelopePic.isEmpty()) {
                articleItem.type = 0
            } else {
                articleItem.type = 1
                articleItem.cover = it.envelopePic
                articleItem.description = it.desc
            }
            result.add(articleItem)
        }
        return@withContext result
    }
}