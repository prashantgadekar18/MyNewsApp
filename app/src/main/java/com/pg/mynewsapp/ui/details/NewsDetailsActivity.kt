package com.pg.mynewsapp.ui.details

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.pg.mynewsapp.databinding.ActivityNewsDetailsBinding
import com.pg.mynewsapp.utils.AppConstant

class NewsDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNewsDetailsBinding

    companion object {
        fun getStartIntent(context: Context): Intent {
            return Intent(context, NewsDetailsActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityNewsDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setNewsDetails()

    }

    private fun setNewsDetails() {
        binding.textViewTitle.text = intent.getStringExtra(AppConstant.TITLE)
        binding.textViewDescription.text =intent.getStringExtra(AppConstant.DESCRIPTION)

        Glide.with(binding.imageViewBanner.context).load(intent.getStringExtra(AppConstant.IMAGE_URL))
            .into(binding.imageViewBanner)

    }
}