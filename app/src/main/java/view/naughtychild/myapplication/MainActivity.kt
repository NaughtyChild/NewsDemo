//package view.naughtychild.myapplication
//
//import android.content.Context
//import androidx.appcompat.app.AppCompatActivity
//import android.os.Bundle
//import android.provider.Contacts
//import android.util.Log
//import kotlinx.android.synthetic.main.activity_main.*
//import kotlinx.coroutines.*
//import okhttp3.OkHttpClient
//import okhttp3.Request
//
//class MainActivity : AppCompatActivity() {
//    val request = Request.Builder().url("https://www.baidu.com").get().build()
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
//
//        button.setOnClickListener {
//            Log.d("MainActivity", "onCreate: " + (System.currentTimeMillis()))
//            val firstTime = System.currentTimeMillis()
//            runBlocking {
//                text.text = async<String>(Dispatchers.Default) {
//                    Log.d("MainActivity", "onCreate: time");
//                    OkHttpClient().newCall(request).execute().body().string()
//                }.await()
//                delay(2000)
//                Log.d("MainActivity", "onCreate: 睡眠结束");
//            }
//            val secondTime = (System.currentTimeMillis())
//            Log.d("MainActivity", "onCreate: ${secondTime - firstTime}")
//        }
//    }
//}
