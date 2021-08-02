package com.everdeng.android.app.wanandroid.ui.home

import android.view.View
import androidx.lifecycle.viewModelScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.everdeng.android.app.wanandroid.BR
import com.everdeng.android.app.wanandroid.R
import com.everdeng.android.app.wanandroid.base.BaseFragment2
import com.everdeng.android.app.wanandroid.databinding.FragmentHomeBinding
import com.everdeng.android.app.wanandroid.ui.adapter.ArticleItemAdapter
import com.everdeng.android.app.wanandroid.ui.adapter.FooterAdapter
import com.everdeng.android.app.wanandroid.ui.adapter.bean.ArticleItem
import com.everdeng.android.app.wanandroid.ui.home.adapter.HomeHeaderAdapter
import com.everdeng.android.app.wanandroid.ui.home.model.HomeViewModel
import com.xm.lib.base.adapter.recyclerview.BaseRecyclerAdapter
import com.xm.lib.base.config.DataBindingConfig
import com.xm.lib.manager.engine.LoadGlide
import com.xm.lib.util.log
import com.xm.lib.util.toast
import com.youth.banner.adapter.BannerImageAdapter
import com.youth.banner.holder.BannerImageHolder
import com.youth.banner.indicator.CircleIndicator
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch

class HomeFragment : BaseFragment2<FragmentHomeBinding, HomeViewModel>() {
    override fun initDataBindingConfig() =
        DataBindingConfig(R.layout.fragment_home, BR.homeViewModel)

    override fun initDataAndEvent() {
        log("initDataAndEvent")
        initBanner()
        initRv()
        initFab()
        initRefresh();
    }

    override fun destroyView() {
    }

    private fun initRv() {
        mBinding.rvArticle.layoutManager = LinearLayoutManager(context)
        mViewModel.adapter = ArticleItemAdapter(DIFF_CALLBACK)
        mBinding.rvArticle.adapter = mViewModel.adapter.withLoadStateHeaderAndFooter(
            header = HomeHeaderAdapter(),
            footer = FooterAdapter()
        )
        mViewModel.adapter.setOnItemClickedListener { v, position, data ->
            val item = data as ArticleItem
            log("type = ${item.type}")
        }

        mViewModel.viewModelScope.launch {
            mViewModel.adapter.loadStateFlow.collectLatest { loadStates ->
                mBinding.refreshLayout.isRefreshing = loadStates.refresh is LoadState.Loading
            }
        }

        mViewModel.viewModelScope.launch {
            mViewModel.homePageDataList.collectLatest {
                mViewModel.adapter.submitData(it)
            }
        }

        mViewModel.viewModelScope.launch {
            mViewModel.adapter.loadStateFlow
                // Only emit when REFRESH LoadState for RemoteMediator changes.
                .distinctUntilChangedBy { it.refresh }
                // Only react to cases where Remote REFRESH completes i.e., NotLoading.
                .filter { it.refresh is LoadState.NotLoading }
                .collect { mBinding.rvArticle.scrollToPosition(0) }
        }
    }

    private fun initBanner() {
        val imageList = listOf("http://dingyue.ws.126.net/2019/02/11/7d72ff294f114e6d86f1f6b3a6df83bb.jpeg", "http://img.article.pchome.net/game/00/20/19/54/pic_lib/s960x639/120-110330134506s960x639.jpg")
        mBinding.bannerView.setAdapter(object : BannerImageAdapter<String>(imageList) {
            override fun onBindView(holder: BannerImageHolder?, data: String?, position: Int, size: Int) {
                LoadGlide.from(context).centerCrop().load(data, holder?.imageView)
            }
        })
            .setIndicator(CircleIndicator(mCxt))
            .setOnBannerListener { data, position -> toast(data as? String) }
    }

    private fun initFab() {
        mBinding.fabTop.setOnClickListener {
            mBinding.rvArticle.scrollToPosition(0)
        }
    }

    private fun initRefresh() {
        mBinding.refreshLayout.setOnRefreshListener { mViewModel.adapter.refresh() }
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