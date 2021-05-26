package com.app.mymainapp.ui

import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.app.mymainapp.baseresult.Status
import com.app.mymainapp.databinding.ActivityMainBinding
import com.app.mymainapp.listeners.OnItemClickListener
import com.app.mymainapp.models.TestApiResponseModel
import com.app.mymainapp.preferences.PreferenceHandler
import com.app.mymainapp.ui.adapters.TestAdapter
import com.app.mymainapp.utils.hide
import com.app.mymainapp.utils.show
import com.app.mymainapp.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), OnItemClickListener {

    @Inject
    lateinit var preferenceHandler:PreferenceHandler

    private lateinit var binding: ActivityMainBinding
    private val mainViewModel: MainViewModel by viewModels()
    private lateinit var testAdapter: TestAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        preferenceHandler.userToken="Hello world"



        testAdapter = TestAdapter(this)
        binding.testRecyclerView.adapter = testAdapter

        mainViewModel.res.observe(this, Observer {

            when (it.status) {
                Status.SUCCESS -> {

                    Toast.makeText(applicationContext, preferenceHandler.userToken,Toast.LENGTH_SHORT).show()

                    binding.appLoader.hide()
                    binding.testRecyclerView.show()
                    it.data.let { testList ->
                        testAdapter.differ.submitList(testList)
                    }
                }

                Status.ERROR -> {
                    binding.appLoader.hide()
                    binding.testRecyclerView.show()
                }

                Status.LOADING -> {
                    binding.appLoader.show()
                    binding.testRecyclerView.hide()
                }
            }

        })
    }

    override fun onItemClick(key: String, item: Any) {
        val testModel = item as TestApiResponseModel

        when (key) {
            "root" -> {
                Toast.makeText(this, "root", Toast.LENGTH_SHORT).show()
            }

            "user" -> {
                Toast.makeText(this, "${item.userId}", Toast.LENGTH_SHORT).show()
            }

            "title" -> {
                Toast.makeText(this, "${item.title}", Toast.LENGTH_SHORT).show()
            }

            "description" -> {
                Toast.makeText(this, "${item.body}", Toast.LENGTH_SHORT).show()
            }

            else -> {
            }
        }

    }


}