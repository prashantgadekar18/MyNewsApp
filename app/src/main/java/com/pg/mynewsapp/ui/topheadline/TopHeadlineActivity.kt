package com.pg.mynewsapp.ui.topheadline

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.browser.customtabs.CustomTabsIntent
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.pg.mynewsapp.data.local.entity.Article
import com.pg.mynewsapp.ui.base.BaseActivity
import com.pg.mynewsapp.utils.Status
import com.pg.mynewsapp.databinding.ActivityTopHeadlineBinding
import com.pg.mynewsapp.ui.topheadline.adapter.TopHeadlineAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class TopHeadlineActivity : BaseActivity<TopHeadlineViewModel, ActivityTopHeadlineBinding>() {

    @Inject
    lateinit var topHeadlineAdapter: TopHeadlineAdapter

    companion object {
        fun getStartIntent(context: Context): Intent {
            return Intent(context, TopHeadlineActivity::class.java)
        }
    }

    private fun renderList(newsList: List<Article>) {
        topHeadlineAdapter.addData(newsList)
        topHeadlineAdapter.notifyDataSetChanged()
    }

    override fun setupObserver() {
        super.setupObserver()
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.data.collect {
                    when (it.status) {
                        Status.LOADING -> {
                            binding.progressBar.visibility = View.VISIBLE
                            binding.recyclerView.visibility = View.GONE
                            binding.includeLayout.errorLayout.visibility = View.GONE
                        }
                        Status.ERROR -> {
                            binding.progressBar.visibility = View.GONE
                            binding.includeLayout.errorLayout.visibility = View.VISIBLE
                            Toast.makeText(this@TopHeadlineActivity, it.message, Toast.LENGTH_LONG)
                                .show()
                        }
                        Status.SUCCESS -> {
                            binding.progressBar.visibility = View.GONE
                            binding.includeLayout.errorLayout.visibility = View.GONE
                            it.data?.let { newsList ->
                                renderList(newsList as List<Article>)
                            }
                            binding.recyclerView.visibility = View.VISIBLE
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
        val recyclerView = binding.recyclerView
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(this@TopHeadlineActivity)
            addItemDecoration(
                DividerItemDecoration(
                    recyclerView.context,
                    (recyclerView.layoutManager as LinearLayoutManager).orientation
                )
            )
            adapter = topHeadlineAdapter
        }

        binding.includeLayout.tryAgainBtn.setOnClickListener {
            viewModel.startFetchingNews()
        }
        topHeadlineAdapter.itemClickListener = { _, data ->
            val article = data as Article
            val builder = CustomTabsIntent.Builder()
            val myIntent = builder.build()
            myIntent.launchUrl(this@TopHeadlineActivity, Uri.parse(article.url))

            startActivity(TopHeadlineActivity.getStartIntent(this@TopHeadlineActivity))


        }
    }

    override fun setupViewBinding(inflater: LayoutInflater): ActivityTopHeadlineBinding {
        return ActivityTopHeadlineBinding.inflate(inflater)
    }


}