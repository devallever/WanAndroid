package com.everdeng.android.app.wanandroid.function.network

import com.everdeng.android.app.wanandroid.function.network.response.BannerData
import com.everdeng.android.app.wanandroid.function.network.response.PageData
import com.xm.netmodel.impl.HttpRequestImpl
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface Api {

    companion object {
        private val api by lazy {
            HttpRequestImpl.getRequest().createApi(Api::class.java)
        }

        fun getApi(vararg classes: Class<*>?): Api = if (classes.isEmpty()) {
            api
        } else {
            HttpRequestImpl.getRequest().createApi(Api::class.java, *classes)
        }
    }

    @GET("article/list/{page}/json")
    suspend fun getHomePageList(
        @Path("page") page: Int
    ): BaseResponse<PageData>?

    @GET("project/list/{page}/json")
    suspend fun getProjectPageList(
        @Path("page") page: Int,
        @Query("cid") cid: Int
    ): BaseResponse<PageData>?


    @GET("banner/json")
    suspend fun getBanner(): BaseResponse<List<BannerData>>?

    @GET("project/tree/json")
    suspend fun getProjectSort(): BaseResponse<List<ProjectSortData>>?

//
//    @GET("project/tree/json")
//    suspend fun getSortData1(): Response<BaseResponse<List<SortData>>>?

    @GET("project/tree/json")
    suspend fun getSortData3(): BaseResponse<List<ProjectSortData>>?
}