package com.pg.mynewsapp.ui.newsListScreen

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.pg.mynewsapp.data.local.entity.Article
import com.pg.mynewsapp.data.model.topheadlines.APIArticle
import com.pg.mynewsapp.utils.ItemClickListener
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.pg.mynewsapp.R
import com.pg.mynewsapp.databinding.TopHeadlineItemLayoutBinding

class NewsListAdapter(
    private val articleList: ArrayList<Any>
) : RecyclerView.Adapter<NewsListAdapter.DataViewHolder>() {

    lateinit var itemClickListener: ItemClickListener<Any>

    class DataViewHolder(private val binding: TopHeadlineItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(article: APIArticle, itemClickListener: ItemClickListener<Any>) {
            binding.textViewTitle.text = article.title
            binding.textViewDescription.text = article.description
            binding.textViewSource.text = article.source.name

            Glide.with(binding.imageViewBanner.context).load(article.imageUrl)
                .apply( RequestOptions().placeholder(R.drawable.img_news_placeholder_squre).error(R.drawable.img_news_placeholder_squre))
                .into(binding.imageViewBanner)

            itemView.setOnClickListener {
                itemClickListener(bindingAdapterPosition, article)
            }
        }

        fun bindDBItem(article: Article, itemClickListener: ItemClickListener<Any>) {
            binding.textViewTitle.text = article.title
            binding.textViewDescription.text = article.description
            binding.textViewSource.text =  article.source.name

            Glide.with(binding.imageViewBanner.context).load(article.imageUrl)
                .apply( RequestOptions().placeholder(R.drawable.img_news_placeholder_squre).error(R.drawable.img_news_placeholder_squre))
                .into(binding.imageViewBanner)

            itemView.setOnClickListener {
                itemClickListener(bindingAdapterPosition, article)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = DataViewHolder(
        TopHeadlineItemLayoutBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
    )


    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        if (articleList[position] is APIArticle) {
            holder.bind(articleList[position] as APIArticle, itemClickListener)
        } else if (articleList[position] is Article) {
            holder.bindDBItem(articleList[position] as Article, itemClickListener)
        }

    }

    override fun getItemCount(): Int = articleList.size

    fun addData(list: List<Any>) {
        articleList.addAll(list)
    }

    fun replaceData(list: List<APIArticle>) {
        articleList.clear()
        articleList.addAll(list)
    }

}