package com.lab.ddlite.ui.restaurantlist

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.lab.ddlite.R
import com.lab.ddlite.databinding.ItemRestaurantBinding


class RestaurantListViewHolder(binding: ItemRestaurantBinding) :
    RecyclerView.ViewHolder(binding.root) {

    private val nameTextView = binding.textName
    private val subTextView = binding.textSub
    private val logoImageView = binding.imageLogo
    private val statusTextView = binding.textStatus

    fun bindItem(uiModel: RestaurantListItemUIModel) {
        renderName(uiModel)
        renderSubText(uiModel)
        renderImageLogo(uiModel)
        renderStatusText(uiModel)
    }

    private fun renderStatusText(uiModel: RestaurantListItemUIModel) {
        if (uiModel.isClosed) {
            statusTextView.text = statusTextView.context.getString(R.string.status_closed)
        } else {
            statusTextView.text =
                statusTextView.context.getString(R.string.status_mins, uiModel.orderTime.toString())
        }
    }

    private fun renderImageLogo(uiModel: RestaurantListItemUIModel) {
        Glide.with(logoImageView)
            .load(uiModel.imageUrl)
            .placeholder(R.drawable.ic_image_placeholder)
            .into(logoImageView)
    }

    private fun renderSubText(uiModel: RestaurantListItemUIModel) {
        subTextView.text = uiModel.subtext
    }

    private fun renderName(uiModel: RestaurantListItemUIModel) {
        nameTextView.text = uiModel.name
    }

}