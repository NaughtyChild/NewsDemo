package view.naughtychild.myapplication.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import view.naughtychild.myapplication.jsonObj.NewsData


class NewsRepository {
    fun loadStudentMessage(type: String): Flow<PagingData<NewsData>> {
        return Pager(
            config = PagingConfig(pageSize = 5),
            pagingSourceFactory = { NewsSource(type) }
        ).flow
    }
}