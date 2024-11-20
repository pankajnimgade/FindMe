package com.search.findme.view

import android.os.Bundle
import androidx.core.text.HtmlCompat
import com.findme.businesslogic.model.Item
import com.findme.frameworklayer.utility.changeDateFrom
import com.google.gson.Gson
import com.search.findme.R
import com.search.findme.common.view.BaseActivity
import com.search.findme.databinding.ActivityDeatilsImageBinding
import com.squareup.picasso.Picasso

class DetailImageActivity : BaseActivity() {

    companion object {
        const val ITEM_KEY = "ITEM_KEY"
    }

    private lateinit var binding: ActivityDeatilsImageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDeatilsImageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initializeUI()
    }

    private fun initializeUI() {
        setSupportActionBar(binding.toolbarDetailImageActivity)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        intent.getStringExtra(ITEM_KEY)?.let { itemText ->
            logMessage("initializeUI","itemText: ${itemText}")
            (Gson().fromJson<Item>(itemText, Item::class.java))?.let { item ->
                binding.textViewTitleDetailImageActivity.text = item.title
                binding.textViewAutorDetailImageActivity.text = item.author
                binding.textViewDescriptionDetailImageActivity.text = HtmlCompat.fromHtml(
                    item.description, HtmlCompat.FROM_HTML_MODE_LEGACY
                )
                binding.textViewDateDetailImageActivity.text = item.date_taken.changeDateFrom()

                Picasso.with(baseContext).load(item.media.m)
                    .placeholder(R.drawable.welcome_dog)
                    .error(R.drawable.welcome_dog)
                    .into(binding.imageViewDetailImageActivity)
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

}