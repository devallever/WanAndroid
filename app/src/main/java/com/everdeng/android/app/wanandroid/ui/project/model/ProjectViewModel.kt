package com.everdeng.android.app.wanandroid.ui.project.model

import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.everdeng.android.app.wanandroid.function.network.NetRepository
import com.everdeng.android.app.wanandroid.ui.project.ProjectArticleListFragment
import com.xm.lib.base.inters.IBaseView
import com.xm.lib.base.model.BaseViewModelKt
import com.xm.lib.util.log
import com.xm.lib.widget.viewpager.ViewPagerAdapter
import com.youth.banner.util.LogUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

class ProjectViewModel : BaseViewModelKt<IBaseView>() {
    lateinit var vpAdapter: ViewPagerAdapter
    val fragmentList = mutableListOf<Fragment>()
    var titles = ArrayList<String>()

    var titleLiveData = MutableLiveData<MutableList<String>>()

    fun init() {
        viewModelScope.launch(Dispatchers.Main) {
            val data = NetRepository.getProjectSortData {

            }

            val titleArray = ArrayList<String>()
            data?.let { response->

                data.map { it
                    val fragment = ProjectArticleListFragment.newInstance(it.id)
                    fragmentList.add(fragment)
                    titleArray.add(it.name)
                    titles.add(it.name)
                }


                vpAdapter.notifyDataSetChanged()

                titleLiveData.value = titleArray

                log(data.toString())
            }
        }
    }

    override fun onCreated() {

    }
}