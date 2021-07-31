package com.everdeng.android.app.wanandroid.ui.home

import com.everdeng.android.app.wanandroid.BR
import com.everdeng.android.app.wanandroid.R
import com.everdeng.android.app.wanandroid.base.BaseFragment2
import com.everdeng.android.app.wanandroid.databinding.FragmentHomeBinding
import com.everdeng.android.app.wanandroid.ui.home.model.HomeViewModel
import com.xm.lib.base.config.DataBindingConfig
import com.xm.lib.manager.engine.LoadGlide
import com.xm.lib.util.toast
import com.youth.banner.adapter.BannerImageAdapter
import com.youth.banner.holder.BannerImageHolder
import com.youth.banner.indicator.CircleIndicator

class HomeFragment : BaseFragment2<FragmentHomeBinding, HomeViewModel>() {
    override fun initDataBindingConfig() =
        DataBindingConfig(R.layout.fragment_home, BR.homeViewModel)

    override fun initDataAndEvent() {
        val imageList = listOf("http://dingyue.ws.126.net/2019/02/11/7d72ff294f114e6d86f1f6b3a6df83bb.jpeg", "http://img.article.pchome.net/game/00/20/19/54/pic_lib/s960x639/120-110330134506s960x639.jpg")
            mBinding.bannerView.setAdapter(object : BannerImageAdapter<String>(imageList) {
            override fun onBindView(holder: BannerImageHolder?, data: String?, position: Int, size: Int) {
                LoadGlide.from(context).centerCrop().load(data, holder?.imageView)
            }
        })
            .setIndicator(CircleIndicator(mCxt))
            .setOnBannerListener { data, position -> toast(data as? String) }
    }

    override fun destroyView() {
    }
}