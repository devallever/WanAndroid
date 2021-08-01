package com.everdeng.android.app.wanandroid.function.network

import com.everdeng.android.app.wanandroid.function.network.response.HomePageData
import com.xm.netmodel.impl.HttpRequestImpl
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

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
    ): BaseResponse<HomePageData>?

    @GET("project/tree/json")
    fun getSortData(): Observable<SortResponse>

    @GET("project/tree/json")
    suspend fun getSortData1(): Response<BaseResponse<List<SortData>>>?

    @GET("project/tree/json")
    suspend fun getSortData2(): SortResponse?

    @GET("project/tree/json")
    suspend fun getSortData3(): BaseResponse<List<SortData>>?
}