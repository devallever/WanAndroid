package com.everdeng.android.app.wanandroid.ui.adapter.paging

import com.everdeng.android.app.wanandroid.function.network.NetRepository
import com.everdeng.android.app.wanandroid.ui.adapter.bean.ArticleItem
import com.xm.lib.base.paging.BasePageSource
import com.xm.lib.util.TimeHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

class ArticlePageDataSource: BasePageSource<ArticleItem>() {

    private var pageCount = 1

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

    override suspend fun getData(pageNum: Int) = withContext(Dispatchers.IO) {
        val result = mutableListOf<ArticleItem>()
        val response = NetRepository.getHomePageList(pageNum) {

        }
        pageCount = response?.pageCount?:1
        val dataList = response?.datas
        if (response?.curPage == 1) {
            val headerItem = ArticleItem()
            headerItem.type = -1;
            result.add(headerItem)
        }
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
            articleItem.time = TimeHelper.setTime2Format("yyyy-MM-dd HH:mm", it.publishTime)
            articleItem.sort = "${it.superChapterName} - ${it.chapterName}"
            articleItem.articleUrl = when {
                it.link.isNotEmpty() -> {
                    it.link
                }
                it.projectLink.isNotEmpty() -> {
                    it.projectLink
                }
                else -> {
                    ""
                }
            }
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

    override fun getStartNum() = 0

    override fun getPageCount() = pageCount
}