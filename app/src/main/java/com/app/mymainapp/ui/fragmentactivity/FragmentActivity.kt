package com.app.mymainapp.ui.fragmentactivity

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.app.mymainapp.databinding.ActivityFragmentBinding
import com.app.mymainapp.viewmodels.FragmentViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FragmentActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var fragmentBinding: ActivityFragmentBinding

    private val fragmentViewModel: FragmentViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fragmentBinding = ActivityFragmentBinding.inflate(layoutInflater)
        setContentView(fragmentBinding.root)
        fragmentBinding.viewModel = fragmentViewModel
        fragmentBinding.listener = this
        setUpFragment()
    }

    private fun setUpFragment() {
        supportFragmentManager.beginTransaction()
            .replace(fragmentBinding.fragmentContainerView.id, FirstFragment())
            .commit()
    }

    override fun onClick(view: View?) {

    }
}