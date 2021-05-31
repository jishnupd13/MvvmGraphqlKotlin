package com.app.mymainapp.ui.fragmentactivity

import android.os.Bundle
import android.view.View
import android.viewbinding.library.activity.viewBinding
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.app.mymainapp.databinding.ActivityFragmentBinding
import com.app.mymainapp.viewmodels.FragmentViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FragmentActivity : AppCompatActivity(), View.OnClickListener {

    private val fragmentBinding: ActivityFragmentBinding by viewBinding()
    private val fragmentViewModel: FragmentViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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