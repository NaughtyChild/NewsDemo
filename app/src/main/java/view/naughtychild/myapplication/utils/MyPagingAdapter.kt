package view.naughtychild.myapplication.utils

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import view.naughtychild.myapplication.NewsDataViewHolder
import view.naughtychild.myapplication.NewsHolder
import view.naughtychild.myapplication.R
import view.naughtychild.myapplication.WebActivity
import view.naughtychild.myapplication.databinding.NewsItemBinding
import view.naughtychild.myapplication.ext.click
import view.naughtychild.myapplication.jsonObj.NewsData

class MyPagingAdapter : PagingDataAdapter<NewsData, RecyclerView.ViewHolder>(object :
    DiffUtil.ItemCallback<NewsData>() {
    //检查两个数据项的是否相同，例如数据项2 和数据项3
    override fun areItemsTheSame(oldItem: NewsData, newItem: NewsData): Boolean {
        return oldItem.uniquekey == newItem.uniquekey
    }

    //检查同个数据项是否改变，是否相同，比如检查新数据项2 和旧数据项2 是否相同
    override fun areContentsTheSame(oldItem: NewsData, newItem: NewsData): Boolean {
        return oldItem == newItem
    }
}) {

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        val newsHolder = holder as NewsDataViewHolder
        val newsData = getItem(position)
        newsData?.let {
            newsHolder.dataBindingUtil.bean = it
        }
        newsHolder.dataBindingUtil.itemParentLl.click {
            var context = newsHolder.dataBindingUtil.root.context
            context.startActivity(Intent(context, WebActivity::class.java).apply {
                this.putExtra("URL", newsData?.url)
            })
        }
        /*val newsHolder = holder as NewsHolder
        val newsData = getItem(position)
        newsHolder.titleTv.text = newsData?.title
        newsHolder.authorTv.text = newsData?.author_name
        Glide.with(newsHolder.view.context).load(newsData?.thumbnail_pic_s).into(newsHolder.image)
        newsHolder.itemParentLl.setOnClickListener {
            newsHolder.view.context.startActivity(
                Intent(
                    newsHolder.view.context,
                    WebActivity::class.java
                ).apply {
                    this.putExtra("URL", newsData?.url)
                })
        }*/
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val holder =
            NewsDataViewHolder(
                NewsItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        return holder
        //        val layout = LayoutInflater.from(parent.context).inflate(R.layout.news_item, null)
//        return NewsHolder(layout)
    }
}