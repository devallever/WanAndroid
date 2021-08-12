package com.everdeng.android.app.wanandroid.ui.project

import android.os.Bundle
import com.everdeng.android.app.wanandroid.databinding.FragmentArticleListBinding
import com.everdeng.android.app.wanandroid.ui.article.ArticleListFragment
import com.everdeng.android.app.wanandroid.ui.project.model.ProjectArticleListViewModel

class ProjectArticleListFragment: ArticleListFragment<FragmentArticleListBinding, ProjectArticleListViewModel>() {


    override fun initDataAndEvent() {
        super.initDataAndEvent()
        mViewModel.cid = arguments?.getInt(EXTRA_CID)?:0
    }

    companion object {

        private val EXTRA_CID = "EXTRA_CID"
        fun newInstance(cid: Int): ProjectArticleListFragment{
            val args = Bundle()
            args.putInt(EXTRA_CID, cid)
            val fragment = ProjectArticleListFragment()
            fragment.arguments = args
            return fragment
        }
    }

}