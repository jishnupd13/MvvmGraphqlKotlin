package com.app.mymainapp.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.app.mymainapp.LaunchListQuery
import com.app.mymainapp.R
import com.app.mymainapp.databinding.ItemLauchItemBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy

class LaunchListAdapter(val context: Context): RecyclerView.Adapter<LaunchListAdapter.LaunchListViewHolder>() {

    var onItemClicked: ((LaunchListQuery.Launch) -> Unit)? = null


    inner class  LaunchListViewHolder(binding: ItemLauchItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val itemBinding = binding
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LaunchListViewHolder {
        val itemLaunchItemBinding: ItemLauchItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_lauch_item, parent, false
        )
        return LaunchListViewHolder(itemLaunchItemBinding)
    }

    override fun onBindViewHolder(holder: LaunchListViewHolder, position: Int) {
        val data = differ.currentList[position]
        holder.itemBinding.item = data

        Glide.with(context)
            .load(data.mission?.missionPatch?:"")
            .placeholder(R.drawable.placeholder)
            .timeout(60000)
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .skipMemoryCache(true)
            .error(R.drawable.placeholder)
            .into(holder.itemBinding.imgRocket)

        holder.itemBinding.layoutRoot.setOnClickListener {
            onItemClicked?.invoke(data)
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    private val differCallback = object : DiffUtil.ItemCallback<LaunchListQuery.Launch>() {
        override fun areItemsTheSame(
            oldItem: LaunchListQuery.Launch,
            newItem: LaunchListQuery.Launch
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: LaunchListQuery.Launch,
            newItem: LaunchListQuery.Launch
        ): Boolean {
            return oldItem == newItem
        }
    }

    private val differ = AsyncListDiffer(this, differCallback)

    fun submitList(list: ArrayList<LaunchListQuery.Launch?>) {
        differ.submitList(list)
    }


}