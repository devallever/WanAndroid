package com.xm.lib.base.ui

import android.graphics.PorterDuff
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.xm.lib.BR
import com.xm.lib.R
import com.xm.lib.base.config.DataBindingConfig
import com.xm.lib.databinding.ActivityBaseTabBinding
import com.xm.lib.manager.MeasureManager.dip2px
import com.xm.lib.widget.tab.TabLayout
import com.xm.lib.widget.viewpager.ViewPagerAdapter

abstract class BaseTabActivity<DB: ViewDataBinding, VM: BaseTabViewModel> : BaseDataActivityKt<ActivityBaseTabBinding, VM>(), TabLayout.OnTabSelectedListener, View.OnClickListener {

    private val tabModel = TabViewModel()

    private lateinit var mViewPagerAdapter: ViewPagerAdapter
    private var mainTabHighlight = 0
    private var mainTabUnSelectColor = 0

    private var mFragmentList = mutableListOf<Fragment>()

    override fun initDataBindingConfig() = DataBindingConfig(R.layout.activity_base_tab, BR.baseTabViewModel)

    override fun initDataAndEvent() {
        initView()
    }

    override fun destroyView() {
    }

    private fun initView() {
        mBinding.ivRight.visibility = View.VISIBLE
        mBinding.ivRight.setOnClickListener(this)

        mainTabHighlight = resources.getColor(R.color.colorPrimary)
        mainTabUnSelectColor = resources.getColor(R.color.colorPrimaryDark)

        initViewPagerData()
        initViewPager()
        initTab()
    }

    private fun initViewPagerData() {
        tabModel.initTab(getTabModel())

        mFragmentList.addAll(getFragmentList())
        mViewPagerAdapter = ViewPagerAdapter(supportFragmentManager, this, mFragmentList)
    }

    private fun initViewPager() {
        mBinding.viewPager.offscreenPageLimit = tabModel.tabCount
        mBinding.viewPager.adapter = mViewPagerAdapter
        mBinding.viewPager.setScanScroll(enableScroll())

        mBinding.viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
            }

            override fun onPageSelected(position: Int) {
                val title = if (position == 0) {
                    getStringRes(R.string.app_name)
                } else {
                    getStringRes(tabModel.getTab(position).labelResId)
                }
                mBinding.tvTitle.text = title
            }

            override fun onPageScrollStateChanged(state: Int) {}
        })
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.iv_right -> {

            }
        }
    }

    private fun initTab() {
        //tab
        mBinding.viewPager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(mBinding.tabLayout))
        mBinding.tabLayout.setOnTabSelectedListener(this)

        val tabCount = tabModel.tabCount
        for (i in 0 until tabCount) {
            val tabModel = tabModel.getTab(i)
            val labelId = tabModel.labelResId
            val tab = mBinding.tabLayout.newTab()
                .setTag(tabModel)
                .setCustomView(getTabView(i))
                .setContentDescription(labelId)
            val drawable = tabModel.drawable
            if (drawable != null) {
                tab.icon = drawable
            } else {
                tab.setIcon(tabModel.iconResId)
            }

            tab.setText(labelId)
            val imageView = tab.customView?.findViewById<ImageView>(R.id.icon)
            imageView?.colorFilter = null
            //解决首次tab文字颜色异常
//            val textView = tab.customView?.findViewById<TextView>(R.id.text1)
//            textView?.setTextColor(mTab.tabTextColors)
            mBinding.tabLayout.addTab(tab)
        }

        mBinding.tabLayout.setSelectedTabIndicatorWidth(dip2px(this, 0f))
        mBinding.tabLayout.setSelectedTabIndicatorHeight(dip2px(this, 0f))
        mBinding.tabLayout.setSelectedTabIndicatorColor(mainTabHighlight)
    }


    override fun onTabSelected(tab: TabLayout.Tab) {
        mBinding.viewPager.setCurrentItem(tab.position, false)

        tabModel.selectedTab = (tab.tag as Tab)
        for (i in 0 until mBinding.tabLayout.tabCount) {
            val aTab = mBinding.tabLayout.getTabAt(i)
            if (aTab != null) {
                val imageView = aTab.customView?.findViewById<ImageView>(R.id.icon)
                val textView = aTab.customView?.findViewById<TextView>(R.id.text1)
                if (aTab === tab) {
                    imageView?.setColorFilter(mainTabHighlight, PorterDuff.Mode.SRC_IN)
                    textView?.setTextColor(mainTabHighlight)
                } else {
                    imageView?.colorFilter = null
                    textView?.setTextColor(mainTabUnSelectColor)
                }
            }
        }
    }

    override fun onTabUnselected(tab: TabLayout.Tab) {}

    override fun onTabReselected(tab: TabLayout.Tab) {}

    private fun getTabView(position: Int): View {
        val view = LayoutInflater.from(this).inflate(R.layout.layout_bottom_tab, null)
        val imageView = view.findViewById<ImageView>(R.id.icon)
        val textView = view.findViewById<TextView>(R.id.text1)
        val tab = tabModel.getTab(position)
        textView.setText(tab.labelResId)
        imageView.setImageResource(tab.iconResId)
        return view
    }


    abstract fun getTabModel(): MutableList<Tab>
    abstract fun getFragmentList(): MutableList<Fragment>

    protected open fun enableScroll(): Boolean = true

 }