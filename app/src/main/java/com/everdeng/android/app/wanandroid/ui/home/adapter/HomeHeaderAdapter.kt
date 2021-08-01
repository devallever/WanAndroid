package com.everdeng.android.app.wanandroid.ui.home.adapter

import android.opengl.Visibility
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.everdeng.android.app.wanandroid.R
import com.xm.lib.util.log

/**
 * 文章加载状态适配器
 *
 * @author yidong
 * @date 2020/7/23
 */
class HomeHeaderAdapter() : LoadStateAdapter<HomeHeaderAdapter.ViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): ViewHolder {
        val root = LayoutInflater.from(parent.context).inflate(R.layout.item_home_header, parent, false)
        return ViewHolder(root)
    }

    override fun onBindViewHolder(holder: ViewHolder, loadState: LoadState) {
        log("HomeHeaderAdapter onBindViewHolder")
        holder.view.visibility = View.VISIBLE
    }

    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view)
}