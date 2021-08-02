package com.everdeng.android.app.wanandroid.ui.home.model

import androidx.databinding.ObservableField
import androidx.lifecycle.viewModelScope
import androidx.paging.LoadState
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.everdeng.android.app.wanandroid.ui.adapter.ArticleItemAdapter
import com.everdeng.android.app.wanandroid.ui.adapter.FooterAdapter
import com.everdeng.android.app.wanandroid.ui.adapter.bean.ArticleItem
import com.everdeng.android.app.wanandroid.ui.adapter.paging.ArticlePageDataSource
import com.xm.lib.base.inters.IBaseView
import com.xm.lib.base.model.BaseViewModelKt
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch

class HomeViewModel : BaseViewModelKt<IBaseView>() {

    private val homePageDataList by lazy {
        Pager(PagingConfig(pageSize = 20)) {
            ArticlePageDataSource()
        }.flow.cachedIn(viewModelScope)
    }

    lateinit var layoutManager: LinearLayoutManager
    lateinit var adapter: ArticleItemAdapter
    lateinit var footerAdapter: FooterAdapter
    val scrollToPosition = ObservableField<Int>()
    val usePaging = ObservableField<Boolean>()
    val refreshStatus by lazy {
        ObservableField<Boolean>()
    }

    override fun onCreated() {

        initRv()
    }

    private fun initRv() {
        refreshStatus.set(false)
        usePaging.set(true)
        layoutManager = LinearLayoutManager(mCxt)
        adapter = ArticleItemAdapter(DIFF_CALLBACK)
        footerAdapter = FooterAdapter()
        adapter.setOnItemClickedListener { v, position, data ->
            toast(data.title)
        }

        viewModelScope.launch {
            homePageDataList.collectLatest {
                adapter.submitData(it)
            }
        }

        viewModelScope.launch {
            adapter.loadStateFlow.collectLatest { loadStates ->
                refreshStatus.set(loadStates.refresh is LoadState.Loading)
            }
        }

        viewModelScope.launch {
            adapter.loadStateFlow
                // Only emit when REFRESH LoadState for RemoteMediator changes.
                .distinctUntilChangedBy { it.refresh }
                // Only react to cases where Remote REFRESH completes i.e., NotLoading.
                .filter { it.refresh is LoadState.NotLoading }
                .collect { scrollToPosition.set(0) }
        }
    }

    /**
     * 回到顶部
     */
    fun onClickScrollTop() {
        scrollToPosition.set(0)
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ArticleItem>() {
            override fun areItemsTheSame(oldItem: ArticleItem, newItem: ArticleItem): Boolean {
                return oldItem.time == newItem.title
            }

            override fun areContentsTheSame(oldItem: ArticleItem, newItem: ArticleItem): Boolean {
                return oldItem.title == newItem.title
            }
        }
    }
}