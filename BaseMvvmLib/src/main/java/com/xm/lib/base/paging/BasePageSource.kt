package com.xm.lib.base.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.xm.lib.util.log

abstract class BasePageSource<T: Any> : PagingSource<Int, T>() {

    override fun getRefreshKey(state: PagingState<Int, T>): Int? = getStartNum()

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, T> {
        val currentLoadingPageKey = params.key ?: getStartNum()
        log("load $currentLoadingPageKey")

        val prevKey = if (currentLoadingPageKey == getStartNum()) null else currentLoadingPageKey - 1

        val dataList = getData(currentLoadingPageKey)
        //如果没有了数据， next传null就好了
        if (currentLoadingPageKey.plus(1) == getPageCount()) {
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

    abstract fun getStartNum(): Int
    abstract fun getPageCount(): Int
    abstract suspend fun getData(currentPage: Int): MutableList<T>
}