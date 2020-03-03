package view.naughtychild.myapplication

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.ResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface NetService {
    @GET("toutiao/index?")
    suspend fun login(@Query("key") key: String, @Query("type") type: String): ResponseBody
}

object RetrofitClient {
    val BASE_URL = "https://v.juhe.cn/"
//
    val api by lazy {
        var logInterceptor = HttpLoggingInterceptor()
        logInterceptor.apply { logInterceptor.level = HttpLoggingInterceptor.Level.BODY }
        val httpClient = OkHttpClient.Builder().addInterceptor(logInterceptor).build()
        val retrofit = Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory.invoke()).client(httpClient).build()

        return@lazy retrofit.create(NetService::class.java)
    }

}