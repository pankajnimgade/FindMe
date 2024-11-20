package com.search.findme.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.findme.businesslogic.model.Item
import com.findme.businesslogic.model.SearchResponse
import com.google.gson.Gson
import com.search.findme.R
import com.search.findme.common.view.BaseActivity
import com.search.findme.databinding.ActivityMainBinding
import com.search.findme.di.viewmodel.ViewModelFactory
import com.search.findme.view.adapter.SearchAdapter
import javax.inject.Inject


class FindMeActivity : BaseActivity(), SearchAdapter.OnClickListener {

    private lateinit var binding: ActivityMainBinding

    @Inject
    lateinit var viewModel: FindMeViewModel

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appComponent.inject(this@FindMeActivity)
        initializeViewModel()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initializeUI()
    }


    private fun initializeViewModel() {
        viewModel = ViewModelProvider(
            this@FindMeActivity, viewModelFactory
        )[FindMeViewModel::class.java]
        viewModel.errorPublisher.observe(this@FindMeActivity, {
            if (it) {
                errorState()
            }
        })
    }

    private fun initializeUI() {
        welcomeScreen()
        binding.searchViewMainActivity.setOnQueryTextListener(object :
            SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    activateProgressBarState()
                    viewModel.searchForTag(query).observe(this@FindMeActivity, { response ->
                        showRecyclerViewState()
                        updateRecyclerView(response)
                    })
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                logMessage("onQueryTextChange", "$newText")
                return false
            }
        })
    }

    private fun updateRecyclerView(response: SearchResponse?) {
        response?.let {
            if (it.items.isNotEmpty()) {
                val adapter = SearchAdapter(it.items, this@FindMeActivity)
                binding.recyclerViewMainActivity.layoutManager = GridLayoutManager(
                    this@FindMeActivity, 2
                )
                binding.recyclerViewMainActivity.adapter = adapter
            }
        }
    }

    override fun onClick(item: Item) {
        logMessage("onClick", "item : ${item.title}")
        val intent = Intent(this@FindMeActivity, DetailImageActivity::class.java)
        intent.putExtra(DetailImageActivity.ITEM_KEY, Gson().toJson(item))
        startActivity(intent)
    }

    private fun activateProgressBarState() {
        binding.recyclerViewMainActivity.visibility = View.GONE
        binding.imageViewMainActivity.visibility = View.GONE
        binding.progressBarMainActivity.visibility = View.VISIBLE
        binding.progressBarMainActivity.isActivated = true
    }

    private fun showRecyclerViewState() {
        binding.imageViewMainActivity.visibility = View.GONE
        binding.progressBarMainActivity.visibility = View.GONE
        binding.recyclerViewMainActivity.visibility = View.VISIBLE
    }

    private fun errorState() {
        binding.recyclerViewMainActivity.visibility = View.GONE
        binding.progressBarMainActivity.visibility = View.GONE
        binding.imageViewMainActivity.visibility = View.VISIBLE
        binding.imageViewMainActivity.setBackgroundResource(R.drawable.error_screen)
    }

    private fun welcomeScreen() {
        binding.recyclerViewMainActivity.visibility = View.GONE
        binding.progressBarMainActivity.visibility = View.GONE
        binding.imageViewMainActivity.visibility = View.VISIBLE
        binding.imageViewMainActivity.setBackgroundResource(R.drawable.welcome_dog)
    }

}