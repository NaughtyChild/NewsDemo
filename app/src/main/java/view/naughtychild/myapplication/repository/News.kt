package view.naughtychild.myapplication.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import view.naughtychild.myapplication.NewsService
import view.naughtychild.myapplication.RetrofitClient
import view.naughtychild.myapplication.jsonObj.NewsData

class NewsSource : PagingSource<Int, NewsData>() {
    private val newsService:NewsService=RetrofitClient.api
    override fun getRefreshKey(state: PagingState<Int, NewsData>): Int? {
        TODO("Not yet implemented")
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, NewsData> {
        TODO("Not yet implemented")
    }
}