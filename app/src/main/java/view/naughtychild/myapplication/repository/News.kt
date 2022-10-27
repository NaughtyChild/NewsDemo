package view.naughtychild.myapplication.repository

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import view.naughtychild.myapplication.NewsService
import view.naughtychild.myapplication.RetrofitClient
import view.naughtychild.myapplication.jsonObj.BaseResponse
import view.naughtychild.myapplication.jsonObj.NewsData
import java.io.IOException

private const val TAG = "NewsSource"
class NewsSource(val type: String) : PagingSource<Int, NewsData>() {
    //pageNum 最大为50，pageSize 最大为30
    private val newsService: NewsService = RetrofitClient.api
    val key: String = "195e1d1d7780de26448c93732a691860"
    override fun getRefreshKey(state: PagingState<Int, NewsData>): Int? {
        TODO("Not yet implemented")
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, NewsData> {
        try {
            val currentPage = params.key ?: 1
            val response = getInternetNewsData(params)
            Log.d(TAG, "load: $response")
            //当前页码 小于 总页码 页面加1
            val nextPage =
                if (currentPage < 50) {
                    currentPage + 1
                } else {
                    null
                }
            //上一页
            val prevKey = if (currentPage > 1) {
                currentPage - 1
            } else {
                null
            }
            when (response.result.stat) {
                "1" -> response.result.data.let {
                    return LoadResult.Page(
                        //需要加载的数据
                        data = it,
                        prevKey = prevKey,
                        //加载下一页的key 如果传null就说明到底了
                        nextKey = nextPage
                    )
                }
            }
        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
        return LoadResult.Error(throwable = IOException())
    }

    private suspend fun getInternetNewsData(params: LoadParams<Int>): BaseResponse {
        return newsService.login(key, type, params.key ?: 1, params.loadSize)
    }
}