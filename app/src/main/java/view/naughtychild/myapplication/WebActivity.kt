package view.naughtychild.myapplication

import android.graphics.Bitmap
import android.net.http.SslError
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.view.animation.RotateAnimation
import android.webkit.*
import kotlinx.android.synthetic.main.activity_web.*

class WebActivity : BaseActivity() {
    val forbiddenAd = arrayListOf<String>("pos.baidu.com", "sh996.dftoutiao.com","mp.weixinbridge.com")
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
//                var adDiv=document.getElementById("J_hot_news");
//                if(adDiv!=null){
//                    alert("找到hot_news");
//                    addDiv.style.display="hidden";//隐藏
//                 }else{
//                    alert("没找到热点新闻");
//                        }


    override fun getCurrentViewLayout(): Int {
        return R.layout.activity_web
    }

    override fun initView() {
        initLoading()
        initWeb()
        setTitleText("热点新闻")
    }

    private fun initLoading() {

        val anim = RotateAnimation(0f, 360f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f)
        anim.repeatCount = Animation.INFINITE
        anim.repeatMode = Animation.RESTART
        anim.duration = 2000L
        loadingIV.startAnimation(anim)
    }

    override fun initData() {
    }

    private fun initWeb() {
        val settings = articleShowWeb.settings
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
        articleShowWeb.webChromeClient = object : WebChromeClient() {
            override fun onReceivedTitle(view: WebView?, title: String?) {
            }
        }
        articleShowWeb.webViewClient = object : WebViewClient() {
            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                Log.d("WebActivity", "onPageStarted: ")
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                Log.d("WebActivity", "onPageFinished: ")
                articleShowWeb.postDelayed(object : Runnable {
                    override fun run() {
                        Log.d("WebActivity", "run: 加载隐藏load显示网页")
                        articleShowWeb.loadUrl(hideAdStr)
                        articleShowWeb.loadUrl("javascript:hideAd();");
                        loadingIV.visibility = View.GONE
                        articleShowWeb.visibility = View.VISIBLE
                    }
                }, 100L)
            }

            override fun shouldOverrideUrlLoading(view: WebView, request: WebResourceRequest): Boolean {
                Log.d("WebActivity", "shouldOverrideUrlLoading:${request.url} ")
                articleShowWeb.loadUrl(request.url.toString())
                return true
            }

            override fun shouldInterceptRequest(view: WebView?, request: WebResourceRequest?): WebResourceResponse? {
                var resourceURL = request?.url
                if (resourceURL != null) {
                    val isAd = false
                    forbiddenAd.forEach {
                        if (resourceURL.toString().contains(it)) return WebResourceResponse(null, null, null)
                    }
                }
                return super.shouldInterceptRequest(view, request)
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
        articleShowWeb.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        Log.d("WebActivity", "initWeb: $url")
        articleShowWeb.loadUrl(url)
    }
}