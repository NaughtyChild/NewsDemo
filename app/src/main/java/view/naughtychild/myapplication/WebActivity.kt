package view.naughtychild.myapplication

import android.graphics.Bitmap
import android.net.http.SslError
import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.view.animation.RotateAnimation
import android.webkit.*
import android.widget.ImageView
import view.naughtychild.myapplication.databinding.ActivityWebBinding

class WebActivity : BaseActivity() {
    val forbiddenAd =
        arrayListOf<String>("pos.baidu.com", "sh996.dftoutiao.com", "mp.weixinbridge.com")
    val webActivityWebBinding: ActivityWebBinding by lazy {
        ActivityWebBinding.inflate(layoutInflater)
    }
    val url by lazy {
        intent.getStringExtra("URL")
//        "https://www.baidu.com"
    }
    var hideAdStr = "javascript: function hideAd() {" +
            "console.log('sdsds'); " +
            "var hotNews=document.getElementById('J_hot_news');" +
            " console.log(hotNews.id);" +
            "hotNews.style.display='hidden';" +
            "console.log(hotNews.style.display);" +
            "hotNews.remove();" +
            "var interestNews=document.getElementById('J_interest_news');" +
            "var yueduyuanwen=document.getElementById('js_toobar3');" +
            "if(yueduyuanwen!=null){yueduyuanwen.remove();}" +
            "interestNews.remove();}"
    var tmp = "javascript: function sayHi() { }";

    override fun getCurrentViewLayout(): View {
        return webActivityWebBinding.root
    }

    override fun initView() {
        initLoading()
        initWeb()
        setTitleText("热点新闻")
    }

    private fun initLoading() {

        val anim = RotateAnimation(
            0f,
            360f,
            Animation.RELATIVE_TO_SELF,
            0.5f,
            Animation.RELATIVE_TO_SELF,
            0.5f
        )
        anim.repeatCount = Animation.INFINITE
        anim.repeatMode = Animation.RESTART
        anim.duration = 2000L
        findViewById<ImageView>(R.id.loadingIV).startAnimation(anim)
    }

    override fun initData() {
    }

    private fun initWeb() {
        var webView = findViewById<WebView>(R.id.articleShowWeb)
        val settings = webView.settings
//        setting.javaScriptEnabled = true
        settings.cacheMode = WebSettings.LOAD_CACHE_ELSE_NETWORK
        settings.domStorageEnabled = true
        settings.mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
        settings.setJavaScriptCanOpenWindowsAutomatically(true);//设置js可以直接打开窗口，如window.open()，默认为false
        settings.setJavaScriptEnabled(true);//是否允许执行js，默认为false。设置true时，会提醒可能造成XSS漏洞
        settings.setSupportZoom(true);//是否可以缩放，默认true
        settings.setBuiltInZoomControls(true);//是否显示缩放按钮，默认false
        settings.setUseWideViewPort(true);//设置此属性，可任意比例缩放。大视图模式
        settings.setLoadWithOverviewMode(true);//和setUseWideViewPort(true)一起解决网页自适应问题
        settings.setAppCacheEnabled(true);//是否使用缓存
        settings.setDomStorageEnabled(true);//DOM Storage
        webView.webChromeClient = object : WebChromeClient() {
            override fun onReceivedTitle(view: WebView?, title: String?) {
            }
        }
        webView.webViewClient = object : WebViewClient() {
            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                Log.d("WebActivity", "onPageStarted: ")
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                Log.d("WebActivity", "onPageFinished: ")
                webView.postDelayed(object : Runnable {
                    override fun run() {
                        Log.d("WebActivity", "run: 加载隐藏load显示网页")
                        webView.loadUrl(hideAdStr)
                        webView.loadUrl("javascript:hideAd();");
                        findViewById<ImageView>(R.id.loadingIV).visibility = View.GONE
                        webView.visibility = View.VISIBLE
                    }
                }, 100L)
            }

            override fun shouldOverrideUrlLoading(
                view: WebView,
                request: WebResourceRequest
            ): Boolean {
                Log.d("WebActivity", "shouldOverrideUrlLoading:${request.url} ")
                webView.loadUrl(request.url.toString())
                return true
            }

            override fun shouldInterceptRequest(
                view: WebView?,
                request: WebResourceRequest?
            ): WebResourceResponse? {
                var resourceURL = request?.url
                if (resourceURL != null) {
                    val isAd = false
                    forbiddenAd.forEach {
                        if (resourceURL.toString().contains(it)) return WebResourceResponse(
                            null,
                            null,
                            null
                        )
                    }
                }
                return super.shouldInterceptRequest(view, request)
            }

            override fun onReceivedError(
                view: WebView?,
                request: WebResourceRequest?,
                error: WebResourceError
            ) {
                Log.d("WebActivity", "onReceivedError: ${error.description}")
                super.onReceivedError(view, request, error)
            }

            override fun onReceivedSslError(
                view: WebView?,
                handler: SslErrorHandler?,
                error: SslError?
            ) {
                Log.d("WebActivity", "onReceivedSslError: ")
                handler?.proceed()
                super.onReceivedSslError(view, handler, error)
            }
        }
        webView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        Log.d("WebActivity", "initWeb: $url")
        webView.loadUrl(url)
    }
}