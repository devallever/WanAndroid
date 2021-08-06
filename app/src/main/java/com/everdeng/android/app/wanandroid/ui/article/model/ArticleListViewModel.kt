package com.everdeng.android.app.wanandroid.ui.article.model

import android.view.ViewGroup
import androidx.databinding.ObservableField
import androidx.lifecycle.viewModelScope
import androidx.paging.LoadState
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.everdeng.android.app.wanandroid.R
import com.everdeng.android.app.wanandroid.ui.adapter.FooterAdapter
import com.everdeng.android.app.wanandroid.ui.article.ArticleActivity
import com.everdeng.android.app.wanandroid.ui.article.adapter.ArticleItemAdapter
import com.everdeng.android.app.wanandroid.ui.article.adapter.bean.ArticleItem
import com.everdeng.android.app.wanandroid.ui.article.adapter.paging.ArticlePageDataSource
import com.everdeng.android.app.wanandroid.ui.project.paging.ProjectArticlePageDataSource
import com.xm.lib.base.inters.IBaseView
import com.xm.lib.base.model.BaseViewModelKt
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch

class ArticleListViewModel: BaseViewModelKt<IBaseView>() {
    private val pageDataList by lazy {
        //pageSize根据接口每页数量而定
        Pager(PagingConfig(pageSize = 20)) {
            ProjectArticlePageDataSource()
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
        //解决recyclerview嵌套recyclerview显示不全
        //https://blog.csdn.net/suyimin2010/article/details/82312560
        layoutManager = object : LinearLayoutManager(mCxt) {
            override fun generateDefaultLayoutParams(): RecyclerView.LayoutParams {
                return RecyclerView.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
            }
        }
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        adapter = ArticleItemAdapter(DIFF_CALLBACK)
        footerAdapter = FooterAdapter()
        adapter.setOnItemClickedListener { v, position, data ->
            ArticleActivity.start(mCxt, data)
        }
        adapter.setOnItemChildViewClickedListener { v, position, ittem ->
            when(v.id){
                R.id.ivLike -> {
                    toast("like")
                    adapter.notifyItemChanged(position, position)
                }
            }
        }

        viewModelScope.launch {
            pageDataList.collectLatest {
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