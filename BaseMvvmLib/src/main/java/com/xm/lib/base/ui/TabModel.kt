package com.xm.lib.base.ui

import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import java.util.*

class TabViewModel {

    val tabList: MutableList<Tab> = mutableListOf()

    fun initTab(tabList: MutableList<Tab>) {
        this.tabList.addAll(tabList)
    }

    /**
     * The listeners to notify when the selected tab is changed.
     */
    private val mTabListeners = ArrayList<TabListener>()

    /**
     * The listeners to notify when the vertical scroll state of the selected tab is changed.
     */
    private val mTabScrollListeners = ArrayList<TabScrollListener>()

    /**
     * The scrolled-to-top state of each tab.
     */
    private val mTabScrolledToTop = BooleanArray(tabList.size)

    /**
     * An enumerated value indicating the currently selected tab.
     */
    private var mSelectedTab: Tab? = if (tabList.isEmpty()) {
        null
    } else {
        tabList[0]
    }

    /**
     * @return the number of tabs
     */
    val tabCount: Int
        get() = tabList.size

    /**
     * @return an enumerated value indicating the currently selected primary tab
     */
    /**
     * @param tab an enumerated value indicating the newly selected primary tab
     */
    // Notify of the tab change.
    // Notify of the vertical scroll position change if there is one.
    var selectedTab: Tab?
        get() {
            return mSelectedTab
        }
        set(tab) {
            val oldSelectedTab = selectedTab
            oldSelectedTab?.let {
                if (oldSelectedTab != tab) {
                    mSelectedTab = tab
                    for (tl in mTabListeners) {
                        tl.selectedTabChanged(oldSelectedTab, tab)
                    }
                    val tabScrolledToTop =
                        isTabScrolledToTop(tab)
                    if (isTabScrolledToTop(
                            oldSelectedTab!!
                        ) != tabScrolledToTop
                    ) {
                        for (tsl in mTabScrollListeners) {
                            tsl.selectedTabScrollToTopChanged(tab, tabScrolledToTop)
                        }
                    }
                }
            }

        }


    init {
        Arrays.fill(mTabScrolledToTop, true)
    }

    //
    // Selected tab
    //

    /**
     * @param tabListener to be notified when the selected tab changes
     */
    fun addTabListener(tabListener: TabListener) {
        mTabListeners.add(tabListener)
    }

    /**
     * @param tabListener to no longer be notified when the selected tab changes
     */
    fun removeTabListener(tabListener: TabListener) {
        mTabListeners.remove(tabListener)
    }

    /**
     * @param ordinal the ordinal (left-to-right index) of the tab
     * @return the tab at the given `ordinal`
     */
    fun getTab(ordinal: Int): Tab {
        return tabList[ordinal]
    }

    /**
     * @param position the position of the tab in the user interface
     * @return the tab at the given `ordinal`
     */
    fun getTabAt(position: Int): Tab {
        val ordinal: Int
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1 && TextUtils.getLayoutDirectionFromLocale(
                Locale.getDefault()
            ) == View.LAYOUT_DIRECTION_RTL
        ) {
            ordinal = tabCount - position - 1
        } else {
            ordinal = position
        }
        return getTab(ordinal)
    }

    //
    // Tab scrolling
    //

    /**
     * @param tabScrollListener to be notified when the scroll position of the selected tab changes
     */
    fun addTabScrollListener(tabScrollListener: TabScrollListener) {
        mTabScrollListeners.add(tabScrollListener)
    }

    /**
     * @param tabScrollListener to be notified when the scroll position of the selected tab changes
     */
    fun removeTabScrollListener(tabScrollListener: TabScrollListener) {
        mTabScrollListeners.remove(tabScrollListener)
    }

    /**
     * Updates the scrolling state in the for this tab.
     *
     * @param tab           an enumerated value indicating the tab reporting its vertical scroll position
     * @param scrolledToTop `true` iff the vertical scroll position of this tab is at the top
     */
    fun setTabScrolledToTop(tab: Tab, scrolledToTop: Boolean) {
        if (isTabScrolledToTop(tab) != scrolledToTop) {
            mTabScrolledToTop[tabList.indexOf(tab)] = scrolledToTop
            if (tab == selectedTab) {
                for (tsl in mTabScrollListeners) {
                    tsl.selectedTabScrollToTopChanged(tab, scrolledToTop)
                }
            }
        }
    }

    /**
     * @param tab identifies the tab
     * @return `true` iff the content in the given `tab` is currently scrolled to top
     */
    fun isTabScrolledToTop(tab: Tab?): Boolean {
        return mTabScrolledToTop[tabList.indexOf(tab)]
    }


    interface TabScrollListener {

        /**
         * @param selectedTab   an enumerated value indicating the current selected tab
         * @param scrolledToTop indicates whether the current selected tab is scrolled to its top
         */
        fun selectedTabScrollToTopChanged(selectedTab: Tab?, scrolledToTop: Boolean)
    }

    interface TabListener {

        /**
         * @param oldSelectedTab an enumerated value indicating the prior selected tab
         * @param newSelectedTab an enumerated value indicating the newly selected tab
         */
        fun selectedTabChanged(oldSelectedTab: Tab?, newSelectedTab: Tab?)
    }

}

data class Tab(val fragmentClass: Class<*>,
                   @param:DrawableRes @field:DrawableRes @get:DrawableRes val iconResId: Int,
                   @param:StringRes @field:StringRes @get:StringRes
                   val labelResId: Int,
                   val bundle: Bundle? = null
) {

    val fragmentClassName: String
    var drawable: Drawable? = null

    init {
        fragmentClassName = fragmentClass.name

//            if (labelResId == R.string.tab_name_history) {
//                bundle?.putIntegerArrayList(HistoryFragment.EXTRA_HISTORY_ITEM_TYPE, arrayListOf(HistoryFragment.HISTORY_TYPE_SCAN, HistoryFragment.HISTORY_TYPE_GENERATE))
//                bundle?.putBoolean(HistoryFragment.EXTRA_HISTORY_SHOW_TAGS, true)
//            }
    }
}