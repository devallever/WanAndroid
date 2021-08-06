package com.everdeng.android.app.wanandroid.ui.article.adapter.model

import androidx.lifecycle.viewModelScope
import com.everdeng.android.app.wanandroid.function.network.NetRepository
import com.everdeng.android.app.wanandroid.ui.article.adapter.bean.BannerItem
import com.xm.lib.base.adapter.recyclerview.BaseRvCustomViewModelKt
import com.xm.lib.manager.engine.LoadGlide
import com.xm.lib.util.log
import com.xm.lib.util.loge
import com.youth.banner.adapter.BannerImageAdapter
import com.youth.banner.holder.BannerImageHolder
import kotlinx.coroutines.launch

class BannerItemViewModel: BaseRvCustomViewModelKt() {
    val bannerItemList = mutableListOf<BannerItem>()

    val bannerAdapter by lazy {
        object : BannerImageAdapter<BannerItem>(bannerItemList) {
            override fun onBindView(
                holder: BannerImageHolder?,
                data: BannerItem?,
                position: Int,
                size: Int
            ) {
//                log(data?.url?:"")
                LoadGlide.from(mCxt).centerCrop().load(data?.url, holder?.imageView)
            }
        }
    }

    override fun initEvent() {
        super.initEvent()
        loadBanner()
    }

    fun loadBanner() {
        viewModelScope.launch {
            val response = NetRepository.getBanner {
                loge("获取banner失败：$it" )
            }
            bannerItemList.clear()
            response?.map {
                val item = BannerItem()
                item.url = it.imagePath
                bannerItemList.add(item)
            }

            log("imageSize = " + bannerItemList.size)
            bannerAdapter.notifyDataSetChanged()
        }
    }
}