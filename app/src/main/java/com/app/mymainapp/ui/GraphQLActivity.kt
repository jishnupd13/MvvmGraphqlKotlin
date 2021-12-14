package com.app.mymainapp.ui

import android.os.Bundle
import android.view.View
import android.viewbinding.library.activity.viewBinding
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.app.mymainapp.LaunchListQuery
import com.app.mymainapp.baseresult.BaseResult
import com.app.mymainapp.databinding.ActivityGraphQlactivityBinding
import com.app.mymainapp.ui.adapter.LaunchListAdapter
import com.app.mymainapp.utils.hide
import com.app.mymainapp.utils.show
import com.app.mymainapp.utils.showToast
import com.app.mymainapp.viewmodels.GraphQlViewModel
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class GraphQLActivity : AppCompatActivity(), View.OnClickListener {

    private val binding: ActivityGraphQlactivityBinding by viewBinding()
    private val viewModel: GraphQlViewModel by viewModels()

    private val launchList = arrayListOf<LaunchListQuery.Launch?>()
    private lateinit var launchListAdapter: LaunchListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.viewModel = viewModel
        binding.listener = this
        launchListAdapter = LaunchListAdapter(this)
        binding.recyclerView.adapter = launchListAdapter
        observeLaunchListData()
        viewModel.fetchLiveData()

        launchListAdapter.onItemClicked = { launch ->
           showToast(launch.mission?.name?:"")
        }
    }

    private fun observeLaunchListData() {
        viewModel.listLiveData.observe(this, {

            when (it.status) {

                BaseResult.Status.SUCCESS -> {
                    binding.progressBar.hide()
                    binding.recyclerView.show()

                    if (it.data != null) {
                        launchList.addAll(it.data)
                        launchListAdapter.submitList(launchList)
                    }
                }

                BaseResult.Status.ERROR -> {
                    binding.progressBar.hide()
                    binding.recyclerView.show()
                    Timber.e(it.message)
                }

                BaseResult.Status.LOADING -> {
                    binding.progressBar.show()
                    binding.recyclerView.hide()
                }
            }
        })
    }



    override fun onClick(view: View?) {

    }
}