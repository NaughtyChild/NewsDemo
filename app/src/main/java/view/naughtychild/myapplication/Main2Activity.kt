package view.naughtychild.myapplication

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main2.*
import kotlinx.coroutines.*

class Main2Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        getNews.setOnClickListener {
            runBlocking {
                //                var data = async(context = Dispatchers.Default) {
                //                    RetrofitClient.api.login("195e1d1d7780de26448c93732a691860", "guoji")
                //                }
                showNews.text = async(Dispatchers.IO) {
                    RetrofitClient.api.login("195e1d1d7780de26448c93732a691860", "guoji")
                }.await().toString()
            }
        }
    }
}