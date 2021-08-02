package com.everdeng.android.app.wanandroid.ui.adapter.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.everdeng.android.app.wanandroid.function.network.NetRepository
import com.everdeng.android.app.wanandroid.ui.adapter.bean.ArticleItem
import com.xm.lib.util.log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

class ArticlePageDataSource: PagingSource<Int, ArticleItem>() {

    private val START_NUM = 0
    private var pageCount = 1

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ArticleItem> {
        val currentLoadingPageKey = params.key ?: START_NUM
        log("load $currentLoadingPageKey")

        val prevKey = if (currentLoadingPageKey == START_NUM) null else currentLoadingPageKey - 1

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
        return START_NUM
    }

    private suspend fun getTestData(page: Int) = withContext(Dispatchers.IO) {
        delay(2000)
        pageCount = 100
        val result = mutableListOf<ArticleItem>()
        for (i in 0..20) {
            val articleItem = ArticleItem()
            articleItem.title = "标题$i"
            articleItem.user = "作者$i"
            articleItem.time = "时间$i"
            articleItem.sort = "分类$i"
            if (i % 3 == 0) {
                articleItem.type = 0
            } else {
                articleItem.type = 1
                articleItem.cover = ""
                articleItem.description = "描述$i"
            }
            result.add(articleItem)
        }
        return@withContext result
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
            articleItem.user = when {
                it.author.isNotEmpty() -> {
                    it.author
                }
                it.shareUser.isNotEmpty() -> {
                    it.shareUser
                }
                else -> {
                    ""
                }
            }
            articleItem.time = it.publishTime.toString()
            articleItem.sort = "${it.superChapterName} - ${it.chapterName}"
            if (it.envelopePic.isEmpty()) {
                articleItem.type = 0
            } else {
                articleItem.type = 1
                articleItem.cover = it.envelopePic
                articleItem.description = it.desc
            }
//            log("${it.title} - cover: ${it.envelopePic}")
            result.add(articleItem)
        }
        return@withContext result
    }
}