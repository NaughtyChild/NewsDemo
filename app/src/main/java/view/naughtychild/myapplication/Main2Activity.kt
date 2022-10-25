package view.naughtychild.myapplication

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import view.naughtychild.myapplication.jsonObj.NewsData

class Main2Activity : BaseActivity() {
    lateinit var adapter: MyRecycleAdapter
    var dataArray = ArrayList<NewsData>()
    lateinit var recyclerView: RecyclerView
    val manager = LinearLayoutManager(this).apply {
        orientation = LinearLayoutManager.VERTICAL
    }

    override fun getCurrentViewLayout(): Int {
        return R.layout.activity_main2
    }

    override fun initView() {
        setTitleText("热点头条")
        Log.d("Main2Activity", "initView: ")
        adapter = MyRecycleAdapter(dataArray, this)
        recyclerView = findViewById<RecyclerView>(R.id.recycleView)
        recyclerView.layoutManager =
            LinearLayoutManager(this).apply {
                orientation = LinearLayoutManager.VERTICAL
            }
        recyclerView.adapter = adapter
//        hotNews.setOnClickListener {
//            getNetRes(it)
//        }
//        societyBt.click {
//            getNetRes(it)
//        }
//        internalBt.click {
//            getNetRes(it)
//        }
//        abroadBt.click {
//            getNetRes(it)
//        }
//        amusementBt.click {
//            getNetRes(it)
//        }
//        physicalBt.click {
//            getNetRes(it)
//        }

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
        runBlocking {
            withContext(Dispatchers.IO) {
                RetrofitClient.api.login("195e1d1d7780de26448c93732a691860", type)
            }.result.data.forEach {
                Log.d("Main2Activity", "onCreate: ${it.toString()}")
                if (!dataArray.contains(it)) {
                    if (it.author_name != "休闲娱乐")
                        dataArray.add(it)
                }
            }
            adapter.notifyDataSetChanged()
            Log.d("Main2Activity", "onCreate: 数据改变，${dataArray.size}")
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