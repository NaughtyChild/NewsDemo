package view.naughtychild.myapplication

import android.content.Context
import android.graphics.Bitmap
import android.net.http.SslError
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.webkit.*
import kotlinx.android.synthetic.main.activity_web.*

class WebActivity : AppCompatActivity() {
    val url by lazy {
        intent.getStringExtra("URL")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web)
        Log.d("WebActivity", "onCreate: url=$url")
        initWeb()
    }

    private fun initWeb() {
        val setting = articleShowWeb.settings
//        setting.javaScriptEnabled = true
        setting.cacheMode = WebSettings.LOAD_CACHE_ELSE_NETWORK
        setting.domStorageEnabled = true
        setting.mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
        articleShowWeb.webViewClient = object : WebViewClient() {
            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                Log.d("WebActivity", "onPageStarted: ")
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                Log.d("WebActivity", "onPageFinished: ")
            }

            override fun shouldOverrideUrlLoading(view: WebView, request: WebResourceRequest): Boolean {
                Log.d("WebActivity", "shouldOverrideUrlLoading: ")
                articleShowWeb.loadUrl(request.url.toString())
                return true
            }

            override fun onReceivedError(view: WebView?, request: WebResourceRequest?, error: WebResourceError) {
                Log.d("WebActivity", "onReceivedError: ${error.description}")
                super.onReceivedError(view, request, error)
            }

            override fun onReceivedSslError(view: WebView?, handler: SslErrorHandler?, error: SslError?) {
                Log.d("WebActivity", "onReceivedSslError: ")
                handler?.proceed()
                super.onReceivedSslError(view, handler, error)
            }
        }
        articleShowWeb.setLayerType(View.LAYER_TYPE_SOFTWARE,null);
        articleShowWeb.loadUrl(url)
    }
}