package view.naughtychild.myapplication

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_main2.*
import kotlinx.coroutines.*
import view.naughtychild.myapplication.jsonObj.NewsData
import java.nio.charset.Charset

class Main2Activity : BaseActivity() {
    lateinit var adapter: MyRecycleAdapter
    var dataArray = ArrayList<NewsData>()
    val manager = LinearLayoutManager(this).apply {
        orientation = LinearLayoutManager.VERTICAL
    }

    override fun getCurrentViewLayout(): Int {
        return R.layout.activity_main2

    }

    override fun initView() {
        title="热点文章"
        Log.d("Main2Activity", "initView: ")
        adapter = MyRecycleAdapter(dataArray, this)
        recycleView.layoutManager = LinearLayoutManager(this).apply {
            orientation = LinearLayoutManager.VERTICAL
        }
        recycleView.adapter = adapter
        getNews.setOnClickListener {
            runBlocking {
                async(Dispatchers.IO) {
                    RetrofitClient.api.login("195e1d1d7780de26448c93732a691860", "guoji")
                }.await().result.data.forEach {
                    Log.d("Main2Activity", "onCreate: ${it.toString()}")
                    if (!dataArray.contains(it)) {
                        dataArray.add(it)
                    }
                    Log.d("Main2Activity", "onCreate: 数据改变，${dataArray.size}")
                    adapter.notifyDataSetChanged()
                }
            }
        }
    }

    override fun initData() {
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

class MyRecycleAdapter(val data: ArrayList<NewsData>, val context: Context) : RecyclerView.Adapter<NewsHolder>() {
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