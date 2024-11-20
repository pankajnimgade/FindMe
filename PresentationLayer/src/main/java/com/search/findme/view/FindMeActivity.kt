package com.search.findme.view

import android.os.Bundle
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import com.search.findme.common.view.BaseActivity
import com.search.findme.databinding.ActivityMainBinding
import com.search.findme.di.viewmodel.ViewModelFactory
import javax.inject.Inject


class FindMeActivity : BaseActivity() {

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
    }

    private fun initializeUI() {
        binding.searchViewMainActivity.setOnQueryTextListener(object :
            SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    viewModel.searchForTag(query)
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                logMessage("onQueryTextChange", "$newText")
                return false
            }
        })
    }

}