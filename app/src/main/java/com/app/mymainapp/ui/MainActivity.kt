package com.app.mymainapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.app.mymainapp.R
import com.app.mymainapp.baseresult.Status
import com.app.mymainapp.databinding.ActivityMainBinding
import com.app.mymainapp.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.textHelloWorld.text="Hi hoo hii"



        mainViewModel.res.observe(this, Observer {

            when(it.status){
                Status.SUCCESS -> {

                    it.data.let {
                        Log.e("data","${it}")
                    }
                }

                Status.ERROR -> {

                }

                Status.LOADING -> {

                }
            }

        })
    }
}