package com.pg.mynewsapp.ui.topheadline

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.pg.mynewsapp.data.local.entity.Article
import com.pg.mynewsapp.ui.base.BaseActivity
import com.pg.mynewsapp.utils.Status
import com.pg.mynewsapp.databinding.ActivityTopHeadlineNewBinding
import com.pg.mynewsapp.ui.details.NewsDetailsActivity
import com.pg.mynewsapp.ui.topheadline.adapter.TopHeadlineByListAdapter
import com.pg.mynewsapp.utils.AppConstant
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class TopHeadlineActivityByList : BaseActivity<TopHeadlineViewModel, ActivityTopHeadlineNewBinding>() {

    @Inject
    lateinit var topHeadlineAdapter: TopHeadlineByListAdapter

    companion object {
        fun getStartIntent(context: Context): Intent {
            return Intent(context, TopHeadlineActivityByList::class.java)
        }
    }

    private fun renderList(newsList: List<Article>) {
        topHeadlineAdapter.addData(newsList)
        topHeadlineAdapter.notifyDataSetChanged()
        Toast.makeText(baseContext, "Render list", Toast.LENGTH_SHORT).show()
    }

    override fun setupObserver() {
        super.setupObserver()
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.data.collect {
                    when (it.status) {
                        Status.LOADING -> {
                            binding.progressBar.visibility = View.VISIBLE
                            binding.rvTopHeadlines.visibility = View.GONE
                            binding.includeLayout.errorLayout.visibility = View.GONE
                        }
                        Status.ERROR -> {
                            binding.progressBar.visibility = View.GONE
                            binding.includeLayout.errorLayout.visibility = View.VISIBLE
                            Toast.makeText(this@TopHeadlineActivityByList, it.message, Toast.LENGTH_LONG)
                                .show()
                        }
                        Status.SUCCESS -> {
                            binding.progressBar.visibility = View.GONE
                            binding.includeLayout.errorLayout.visibility = View.GONE
                            it.data?.let { newsList ->
                                renderList(newsList as List<Article>)
                            }
                            binding.rvTopHeadlines.visibility = View.VISIBLE
                        }
                    }
                }
            }
        }

    }

    override fun setupViewModel() {
        viewModel = ViewModelProvider(this)[TopHeadlineViewModel::class.java]
    }


    override fun setupView(savedInstanceState: Bundle?) {
        val rvTopHeadlines = binding.rvTopHeadlines
        binding.rvTopHeadlines.apply {
            layoutManager = LinearLayoutManager(this@TopHeadlineActivityByList)
            addItemDecoration(
                DividerItemDecoration(
                    rvTopHeadlines.context,
                    (rvTopHeadlines.layoutManager as LinearLayoutManager).orientation
                )
            )
            adapter = topHeadlineAdapter
        }

        binding.includeLayout.tryAgainBtn.setOnClickListener {
            viewModel.startFetchingNews()
        }
        topHeadlineAdapter.itemClickListener = { _, data ->
            val article = data as Article
            var intent = NewsDetailsActivity.getStartIntent(this@TopHeadlineActivityByList)
            intent.putExtra(AppConstant.TITLE, article.title)
            intent.putExtra(AppConstant.URL, article.url)
            intent.putExtra(AppConstant.DESCRIPTION, article.description)
            intent.putExtra(AppConstant.IMAGE_URL, article.imageUrl)
            startActivity(intent)
        }
    }

    override fun setupViewBinding(inflater: LayoutInflater): ActivityTopHeadlineNewBinding {
        return ActivityTopHeadlineNewBinding.inflate(inflater)
    }


}