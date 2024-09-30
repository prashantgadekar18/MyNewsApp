package com.pg.mynewsapp.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.pg.mynewsapp.ui.search.SearchActivity
import com.pg.mynewsapp.ui.sources.SourcesActivity
import com.pg.mynewsapp.databinding.ActivityMainBinding
import com.pg.mynewsapp.ui.topheadline.TopHeadlineActivity
import com.pg.mynewsapp.ui.topheadline.TopHeadlineActivityByList

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.topHeadlineButton.setOnClickListener {
            startActivity(TopHeadlineActivity.getStartIntent(this@MainActivity))
        }
        binding.topHeadlineListButton.setOnClickListener {
            startActivity(TopHeadlineActivityByList.getStartIntent(this@MainActivity))
        }

        binding.newsSourceButton.setOnClickListener {
            startActivity(SourcesActivity.getStartIntent(this@MainActivity))
        }

        binding.searchButton.setOnClickListener {
            startActivity(SearchActivity.getStartIntent(this@MainActivity))
        }
    }
}