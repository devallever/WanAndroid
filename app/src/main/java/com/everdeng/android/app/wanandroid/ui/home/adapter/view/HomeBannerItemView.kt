package com.everdeng.android.app.wanandroid.ui.home.adapter.view

import android.content.Context
import android.util.AttributeSet
import com.everdeng.android.app.wanandroid.BR
import com.everdeng.android.app.wanandroid.R
import com.everdeng.android.app.wanandroid.databinding.ItemHomeBannerBinding
import com.everdeng.android.app.wanandroid.ui.article.adapter.bean.ArticleItem
import com.everdeng.android.app.wanandroid.ui.home.adapter.bean.HomeBannerItem
import com.everdeng.android.app.wanandroid.ui.home.adapter.model.HomeBannerItemViewModel
import com.xm.lib.base.adapter.recyclerview.BaseRecyclerAdapter
import com.xm.lib.base.adapter.recyclerview.BaseRvCustomView
import com.xm.lib.base.adapter.recyclerview.BaseViewHolder
import com.xm.lib.base.config.DataBindingConfig
import com.xm.lib.util.toast
import com.youth.banner.indicator.CircleIndicator

class HomeBannerItemView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : BaseRvCustomView<ArticleItem, HomeBannerItemViewModel>(context, attrs, defStyleAttr) {


    override fun convert(
        adapter: BaseRecyclerAdapter<*, out BaseViewHolder<*>>?,
        data: ArticleItem?,
        position: Int
    ) {
        val binding = getDataBinding<ItemHomeBannerBinding>()
        if (binding.bannerView.adapter == null) {
            binding.bannerView.setAdapter(mViewModel.bannerAdapter)
                .setIndicator(CircleIndicator(mCxt))
                .setOnBannerListener { data, position -> toast((data as HomeBannerItem).url)
                }
        }
        if (mViewModel.bannerItemList.isEmpty()) {
            mViewModel.loadBanner()
        }
    }

    override fun getViewModel() = HomeBannerItemViewModel()

    override fun getDataBindingConfig()= DataBindingConfig(R.layout.item_home_banner, BR.homeBannerViewModel)
}