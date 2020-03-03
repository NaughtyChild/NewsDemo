package view.naughtychild.myapplication

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Contacts
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*
import okhttp3.OkHttpClient
import okhttp3.Request

class MainActivity : AppCompatActivity() {
    val request = Request.Builder().url("https://www.baidu.com").get().build()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button.setOnClickListener {
            runBlocking {
                    text.text = async<String>(Dispatchers.Default) {
                        OkHttpClient().newCall(request).execute().body().string()
                    }.await()
            }
        }
    }
}
