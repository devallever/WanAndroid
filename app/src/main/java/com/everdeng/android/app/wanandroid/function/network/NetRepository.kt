package com.everdeng.android.app.wanandroid.function.network

import com.everdeng.android.app.wanandroid.function.network.response.BannerData
import com.everdeng.android.app.wanandroid.function.network.response.PageData
import com.xm.lib.util.log
import com.xm.lib.util.loge
import retrofit2.Response

object NetRepository {

    private val apiService by lazy {
        ServiceCreator.create(Api::class.java)
    }

    suspend fun getSortData(): SortResponse? {
        return try {
            val sortResponse = apiService.getSortData2()
            sortResponse?.data ?: return null
            if (sortResponse.errorCode != 0) {
                loge(sortResponse.errorMsg)
                return null
            } else {
                sortResponse
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    suspend fun getSortData1(): Response<BaseResponse<List<SortData>>>? {
        return apiService.getSortData1()
    }

    suspend fun getHomePageList(pageNum: Int, failureTask: (errorMsg: String) -> Unit): PageData? {
        return getData(failureTask, "获取首页文章列表成功") {
            apiService.getHomePageList(pageNum)
        }
    }

    suspend fun getProjectPageList(pageNum: Int, failureTask: (errorMsg: String) -> Unit): PageData? {
        return getData(failureTask, "获取项目文章列表成功") {
            apiService.getProjectPageList(pageNum)
        }
    }

    suspend fun getBanner(failureTask: (errorMsg: String) -> Unit): List<BannerData>? {
        return getData(failureTask, "获取首页banner成功") {
            apiService.getBanner()
        }
    }



    suspend fun getSortData4(failureTask: (errorMsg: String) -> Unit): List<SortData>? {
        return getData(failureTask, "获取分类数据成功") {
            apiService.getSortData2()
        }
    }

    private suspend fun <T> getData(failureTask: (errorMsg: String) -> Unit, successMsg: String = "获取数据成功", block: suspend () -> BaseResponse<T>?) : T? {
        return try {
            val baseResponse = block()
            val result = getResponseData(baseResponse) {
                failureTask(it)
            }
            if (result != null) {
                log(successMsg)
            }
            return result
        } catch (e: Exception) {
            //失败
            e.printStackTrace()
            failureTask("${e.message}")
            null
        }
    }

    suspend fun getSortData2(failureTask:(errorMsg: String) -> Unit): List<SortData>? {
        return try {
            val baseResponse = apiService.getSortData2()
            val result = getResponseData(baseResponse) {
                failureTask(it)
            }
            if (result != null) {
                log("获取分类成功")
            }
            return result
        } catch (e: Exception) {
            //失败
            e.printStackTrace()
            failureTask("${e.message}")
            null
        }
    }

    suspend fun getSortData3(failureTask:(errorMsg: String) -> Unit): List<SortData>? {
        return try {
            val baseResponse = apiService.getSortData3()
            val result = getResponseData(baseResponse) {
                failureTask(it)
            }
            if (result != null) {
                log("获取分类成功")
            }
            return result
        } catch (e: Exception) {
            //失败
            e.printStackTrace()
            failureTask("${e.message}")
            null
        }
    }

    private fun <T> getResponseData(baseResponse: BaseResponse<T>?): T? {
        baseResponse?.data ?: return null
        return if (baseResponse.errorCode != 0) {
            //成功-数据异常
            loge(baseResponse.errorMsg)
            null
        } else {
            //成功
            baseResponse.data
        }
    }

    private fun <T> getResponseData(baseResponse: BaseResponse<T>?, failureTask: (errorMsg: String) -> Unit): T? {
        baseResponse?.data ?: return null
        return if (baseResponse.errorCode != 0) {
            //成功-数据异常
//            loge(baseResponse.errorMsg)
            failureTask(baseResponse.errorMsg)
            null
        } else {
            //成功
            baseResponse.data
        }
    }
}