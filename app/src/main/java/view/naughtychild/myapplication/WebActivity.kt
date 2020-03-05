package view.naughtychild.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_web.*

class WebActivity : AppCompatActivity() {
    val url by lazy {
        intent.getStringExtra("URL")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web)
        articleShowWeb.loadUrl(url)
    }
}