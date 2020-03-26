package view.naughtychild.myapplication

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import kotlinx.android.synthetic.main.activity_base.*


abstract class BaseActivity : AppCompatActivity() {
    var title: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base)
        initToolBar()
        val layoutParams= ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT)
        contentView.addView(layoutInflater.inflate(getCurrentViewLayout(),null),layoutParams)
        initView()
        initData()
    }


    private fun initToolBar() {
        tittleTv.text = title
        backIv.setOnClickListener {
            onBackPressed()
        }
    }

    abstract fun getCurrentViewLayout(): Int
    abstract fun initView()
    abstract fun initData()
}
