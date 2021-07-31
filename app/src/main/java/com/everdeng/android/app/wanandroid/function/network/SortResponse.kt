package com.everdeng.android.app.wanandroid.function.network


class SortResponse() : BaseResponse<List<SortData>>() {

}

data class SortData(
    val children: List<Any>,
    val courseId: Int,
    val id: Int,
    val name: String,
    val order: Int,
    val parentChapterId: Int,
    val userControlSetTop: Boolean,
    val visible: Int
)