package com.lab.ddlite.ui.restaurantdetail


import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.lab.ddlite.R
import com.lab.ddlite.databinding.ViewRestaurantDetailBinding
import com.lab.ddlite.ui.common.mvx.MvxEffect
import com.lab.ddlite.ui.common.mvx.MvxEffectHandler
import com.lab.ddlite.ui.common.mvx.MvxState
import com.lab.ddlite.ui.common.mvx.MvxView
import com.lab.ddlite.ui.common.viewproxy.BaseViewProxy
import com.lab.ddlite.ui.restaurantdetail.RestaurantDetailContract.*

class RestaurantDetailViewProxy(
    private val binding: ViewRestaurantDetailBinding
) : BaseViewProxy<Listener>(
    rootView = binding.root
), MvxView, MvxEffectHandler {

    private val toolbarView = binding.toolbar
    private val nameTextView = binding.textName
    private val coverImageView = binding.imageCover
    private val ratingBarView = binding.ratingBar
    private var statusTextView = binding.textStatus
    private var descriptionTextView = binding.textDescription

    init {
        toolbarView.setNavigationIcon(R.drawable.abc_ic_ab_back_material)
        toolbarView.setNavigationOnClickListener {
            notifyListeners(ViewEvent.NavBackEvent)
        }
    }

    override fun render(state: MvxState) {
        when (state) {
            is ViewState.DataState -> {
                renderView(state.uiModel)
            }
        }
    }

    private fun renderView(uiModel: RestaurantDetailUIModel) {
        toolbarView.title = uiModel.name
        renderCoverImageView(uiModel.imageUrl)
        nameTextView.text = uiModel.name
        ratingBarView.rating = uiModel.rating
        renderStatusText(uiModel)
        descriptionTextView.text = uiModel.description
    }

    private fun renderCoverImageView(imageUrl: String) {
        Glide.with(coverImageView)
            .load(imageUrl)
            .placeholder(R.drawable.ic_image_placeholder)
            .centerCrop()
            .into(coverImageView)
    }

    private fun renderStatusText(uiModel: RestaurantDetailUIModel) {
        if (!uiModel.isOpen) {
            statusTextView.text = statusTextView.context.getString(R.string.status_closed)
        } else {
            statusTextView.text =
                statusTextView.context.getString(R.string.status_mins, uiModel.orderTime.toString())
        }
    }

    private fun renderErrorState(error: Throwable) {
        Snackbar.make(binding.root, error.message.toString(), Snackbar.LENGTH_SHORT).show()
    }

    private fun notifyListeners(event: ViewEvent) {
        for (listener in getListeners()) {
            listener.onEvent(event)
        }
    }

    override fun handle(effect: MvxEffect) {
        when (effect) {
            is ViewEffect.ShowError -> {
                renderErrorState(effect.reason)
            }
        }
    }
}