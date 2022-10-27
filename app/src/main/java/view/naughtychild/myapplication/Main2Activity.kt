package view.naughtychild.myapplication

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import view.naughtychild.myapplication.databinding.ActivityMain2Binding
import view.naughtychild.myapplication.databinding.NewsItemBinding
import view.naughtychild.myapplication.jsonObj.NewsData
import view.naughtychild.myapplication.repository.NewsRepository
import view.naughtychild.myapplication.utils.MyPagingAdapter

class Main2Activity : BaseActivity() {
    lateinit var adapter: MyPagingAdapter
    val main2Binding: ActivityMain2Binding by lazy {
        ActivityMain2Binding.inflate(layoutInflater)
    }
    var dataArray = ArrayList<NewsData>()
    lateinit var recyclerView: RecyclerView
    val manager = LinearLayoutManager(this).apply {
        orientation = LinearLayoutManager.VERTICAL
    }

    override fun getCurrentViewLayout(): View {
        return main2Binding.root
    }

    override fun initView() {
        setTitleText("热点头条")
        Log.d("Main2Activity", "initView: ")
        adapter = MyPagingAdapter()
        recyclerView = findViewById(R.id.recycleView)
        recyclerView.layoutManager =
            LinearLayoutManager(this).apply {
                orientation = LinearLayoutManager.VERTICAL
            }
        recyclerView.adapter = adapter
    }

    fun getNetRes(view: View) {
        dataArray.clear()
        var type = when (view.id) {
            R.id.hotNews -> "top"
            R.id.societyBt -> "shehui"
            R.id.internalBt -> "guonei"
            R.id.abroadBt -> "guoji"
            R.id.amusementBt -> "yule"
            R.id.physicalBt -> "tiyu"
            R.id.militaryBt -> "junshi"
            R.id.fashionBt -> "shishang"
            R.id.scienceBt -> "keji"
            R.id.financeBt -> "caijing"
            else -> "top"
        }
        getNewsData(type)
    }

    private fun getNewsData(type: String) {
        lifecycleScope.launch {
            NewsRepository().loadStudentMessage(type).collect() {
                adapter.submitData(it)
            }
        }
    }

    override fun initData() {
        getNewsData("top")
    }
}

class NewsHolder(val view: View) : RecyclerView.ViewHolder(view) {
    val image: ImageView
    val titleTv: TextView
    val authorTv: TextView
    val itemParentLl: ViewGroup

    init {
        image = view.findViewById(R.id.image)
        titleTv = view.findViewById(R.id.title)
        authorTv = view.findViewById(R.id.author)
        itemParentLl = view.findViewById(R.id.itemParentLl)
    }
}

class MyRecycleAdapter(val data: ArrayList<NewsData>, val context: Context) :
    RecyclerView.Adapter<NewsHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsHolder {
        val layout = LayoutInflater.from(context).inflate(R.layout.news_item, null)
        return NewsHolder(layout)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: NewsHolder, position: Int) {
        val newsData = data[position]
        holder.titleTv.text = newsData.title
        holder.authorTv.text = newsData.author_name
        Glide.with(context).load(newsData.thumbnail_pic_s).into(holder.image)
        holder.itemParentLl.setOnClickListener {
            context.startActivity(Intent(context, WebActivity::class.java).apply {
                this.putExtra("URL", newsData.url)
            })
        }
    }
}

class NewsDataViewHolder(var dataBindingUtil: NewsItemBinding) :
    RecyclerView.ViewHolder(dataBindingUtil.root)
