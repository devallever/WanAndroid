package com.everdeng.android.app.wanandroid.ui.adapter

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
class FooterAdapter() : LoadStateAdapter<FooterAdapter.ViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): ViewHolder {
        val root = LayoutInflater.from(parent.context).inflate(R.layout.item_footer, parent, false)
        return ViewHolder(root)
    }

    override fun onBindViewHolder(holder: ViewHolder, loadState: LoadState) {
        if (loadState == LoadState.Loading) {
            //show progress viewe
            holder.view.visibility = View.VISIBLE
        } else {
            holder.view.visibility = View.GONE
            log("加载完成")
            //hide the view
        }
    }

    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view)
}