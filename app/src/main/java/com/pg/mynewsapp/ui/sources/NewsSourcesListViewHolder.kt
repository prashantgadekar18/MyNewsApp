package com.pg.mynewsapp.ui.sources

import androidx.recyclerview.widget.RecyclerView
import com.pg.mynewsapp.data.local.entity.NewsSource
import com.pg.mynewsapp.utils.ItemClickListener
import com.pg.mynewsapp.databinding.NewsSourcesItemLayoutBinding


class NewsSourcesListViewHolder(private val binding: NewsSourcesItemLayoutBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bindView(newsSource: NewsSource, itemClickListener: ItemClickListener<NewsSource>) {
        binding.newsSourceBtn.text = newsSource.name
        itemView.setOnClickListener {
            itemClickListener(bindingAdapterPosition, newsSource)

        }
    }


}
