package view.naughtychild.myapplication

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import view.naughtychild.myapplication.databinding.ActivityBaseBinding

abstract class BaseActivity : AppCompatActivity() {
    val baseBinding: ActivityBaseBinding by lazy {
        ActivityBaseBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(baseBinding.root)
        val layoutParams =
            ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
        baseBinding.contentView.addView(getCurrentViewLayout(), layoutParams)
        initToolBar()
        initView()
        initData()
    }

    /**
     * 设置当前页面的标题
     */
    fun setTitleText(title: String) {
        findViewById<TextView>(R.id.tittleTv).text = title
    }

    private fun initToolBar() {
        findViewById<ImageView>(R.id.backIv).setOnClickListener {
            onBackPressed()
        }
    }

    abstract fun getCurrentViewLayout(): View
    abstract fun initView()
    abstract fun initData()
}
