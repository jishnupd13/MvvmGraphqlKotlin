package com.app.mymainapp.ui.adapters.nestedadapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.app.mymainapp.R
import com.app.mymainapp.databinding.ItemUserNameBinding
import com.app.mymainapp.listeners.OnItemClickListener
import com.app.mymainapp.models.Name
import com.app.mymainapp.models.TestApiNestedModel
import timber.log.Timber

/** Created by Jishnu P Dileep on 28-05-2021 */
class TestUserNameAdapter (val onItemClickListener: OnItemClickListener) :
    RecyclerView.Adapter<TestUserNameAdapter.TestUserNameAdapterViewHolder>() {

    inner class TestUserNameAdapterViewHolder(binding: ItemUserNameBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val itemBinding = binding
    }

    private val differCallback = object : DiffUtil.ItemCallback<Name>() {
        override fun areItemsTheSame(
            oldItem: Name,
            newItem: Name
        ): Boolean {
            return oldItem.userName == newItem.userName
        }

        override fun areContentsTheSame(
            oldItem: Name,
            newItem: Name
        ): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    fun submitList(list: List<Name>){
        differ.submitList(list)
    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TestUserNameAdapterViewHolder {

        val itemUserNameBinding: ItemUserNameBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_user_name, parent, false
        )
        return TestUserNameAdapterViewHolder(itemUserNameBinding)
    }

    override fun onBindViewHolder(holder: TestUserNameAdapterViewHolder, position: Int) {
        val data = differ.currentList[position]
        val binding = holder.itemBinding

        Timber.e("${data}")

        binding.item = data
        binding.listner=onItemClickListener
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

}