package com.lab.ddlite.ui.restaurantlist

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.lab.ddlite.databinding.ViewRestaurantListBinding
import com.lab.ddlite.ui.common.ItemClickSupport
import com.lab.ddlite.ui.common.mvx.MvxEffect
import com.lab.ddlite.ui.common.mvx.MvxEffectHandler
import com.lab.ddlite.ui.common.mvx.MvxState
import com.lab.ddlite.ui.common.mvx.MvxView
import com.lab.ddlite.ui.common.viewproxy.BaseViewProxy
import com.lab.ddlite.ui.restaurantlist.RestaurantListContract.*

class RestaurantListViewProxy(
    private val binding: ViewRestaurantListBinding,
) : BaseViewProxy<Listener>(
    rootView = binding.root
), MvxView, MvxEffectHandler, ItemClickSupport.OnItemClickListener {

    private val viewSwitcher = binding.viewSwitcher
    private val loadingView = binding.loadingView
    private val listView: RecyclerView = binding.listView
    private var adapter: RestaurantListAdapter = RestaurantListAdapter()

    init {
        ItemClickSupport.addTo(listView).setOnItemClickListener(this)
        val layoutManager = LinearLayoutManager(context)
        listView.layoutManager = layoutManager
        listView.adapter = adapter
    }

    override fun render(state: MvxState) {
        when (state) {
            is ViewState.DataState -> {
                if (state.uiModels.isEmpty()) {
                    renderEmptyView()
                } else {
                    renderListView(state.uiModels)
                }
            }
            is ViewState.IdleState -> {
                renderEmptyView()
            }
            is ViewState.LoadingState -> {
                renderLoadingView(state.show)
            }
        }
    }

    private fun renderListView(uiModels: List<RestaurantListItemUIModel>) {
        viewSwitcher.displayedChild = 1
        adapter.submitList(uiModels)
    }

    private fun renderErrorState(error: Throwable) {
        Snackbar.make(binding.root, error.message.toString(), Snackbar.LENGTH_SHORT).show()
    }

    private fun renderEmptyView() {
        viewSwitcher.displayedChild = 0
    }

    private fun renderLoadingView(show: Boolean) {
        if (show) {
            loadingView.visibility = View.VISIBLE
            loadingView.show()
        } else {
            loadingView.hide()
            loadingView.visibility = View.INVISIBLE
        }
    }

    private fun notifyListeners(event: ViewEvent) {
        for (listener in getListeners()) {
            listener.onEvent(event)
        }
    }

    override fun onItemClicked(recyclerView: RecyclerView?, position: Int, v: View?) {
        adapter.getItemBy(position)?.let {
            notifyListeners(ViewEvent.ItemClickEvent(it))
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