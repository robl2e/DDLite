package com.lab.ddlite.ui.restaurantlist


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.lab.ddlite.databinding.ItemRestaurantBinding

class RestaurantListAdapter :
    ListAdapter<RestaurantListItemUIModel, RestaurantListViewHolder>(
        DiffItemCallback()
    ) {

    class DiffItemCallback : DiffUtil.ItemCallback<RestaurantListItemUIModel>() {
        override fun areItemsTheSame(
            oldItem: RestaurantListItemUIModel,
            newItem: RestaurantListItemUIModel
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: RestaurantListItemUIModel,
            newItem: RestaurantListItemUIModel
        ): Boolean {
            return oldItem.name == newItem.name &&
                    oldItem.imageUrl == newItem.imageUrl &&
                    oldItem.subtext == newItem.subtext &&
                    oldItem.isClosed == newItem.isClosed &&
                    oldItem.orderTime == newItem.orderTime
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RestaurantListViewHolder {
        val binding = ItemRestaurantBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return RestaurantListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RestaurantListViewHolder, position: Int) {
        val item = getItem(position)
        holder.bindItem(item)
    }

    fun getItemBy(position: Int): RestaurantListItemUIModel? {
        return getItem(position)
    }
}


