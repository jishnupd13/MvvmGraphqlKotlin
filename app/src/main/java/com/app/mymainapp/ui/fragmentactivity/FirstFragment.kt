package com.app.mymainapp.ui.fragmentactivity

import android.os.Bundle
import android.view.View
import android.viewbinding.library.fragment.viewBinding
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.app.mymainapp.R
import com.app.mymainapp.databinding.FragmentFirstBinding
import com.app.mymainapp.listeners.OnItemClickListener
import com.app.mymainapp.models.Name
import com.app.mymainapp.models.TestApiNestedModel
import com.app.mymainapp.ui.adapters.nestedadapter.TestNestedAdapter
import com.app.mymainapp.utils.showToast
import com.app.mymainapp.viewmodels.FragmentViewModel
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber


@AndroidEntryPoint
class FirstFragment : Fragment(R.layout.fragment_first), View.OnClickListener, OnItemClickListener {


    private val firstBinding: FragmentFirstBinding by viewBinding()
    private val fragmentViewModel: FragmentViewModel by viewModels()

    private lateinit var testNestedAdapter: TestNestedAdapter


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        firstBinding.viewModel = fragmentViewModel
        firstBinding.listener = this
        testNestedAdapter = TestNestedAdapter(this)
        initViews()
    }

    private fun initViews() {

        firstBinding.postNestedRecyclerView.adapter = testNestedAdapter
        testNestedAdapter.differ.submitList(setUpList())

    }

    override fun onClick(view: View?) {

    }

    override fun onItemClick(key: String, item: Any) {

        when (key) {
            "root" -> {
                val data = item as TestApiNestedModel
                Timber.e("<<<<<<<<<<<<<<<<<<<<< root >>>>>>>>>>>>>>>>>>>>>>>")
                val currentList = testNestedAdapter.differ.currentList
                val position = currentList.indexOf(data)

                if (position >= 0) {
                    currentList[position].isVisible = currentList[position].isVisible != true
                    testNestedAdapter.notifyDataSetChanged()
                }
            }
            "name" -> {
                val data = item as Name
                data.userName?.let { showToast(it) }
            }
        }
    }


    private fun setUpList(): ArrayList<TestApiNestedModel> {
        val list = ArrayList<TestApiNestedModel>()
        val userNameList = ArrayList<Name>()
        userNameList.add(Name("Jishnu P Dileep"))
        list.add(TestApiNestedModel("Hello", 1, "Hello World", 1, userNameList, false))
        list.add(TestApiNestedModel("Hello", 2, "Hello World", 1, userNameList, false))
        list.add(TestApiNestedModel("Hello", 3, "Hello World", 1, userNameList, false))
        return list
    }
}