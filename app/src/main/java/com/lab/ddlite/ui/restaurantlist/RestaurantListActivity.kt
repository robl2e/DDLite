package com.lab.ddlite.ui.restaurantlist

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.lab.ddlite.R
import com.lab.ddlite.databinding.ViewRestaurantListBinding
import com.lab.ddlite.ui.common.activity.BaseActivity
import com.lab.ddlite.ui.common.navigator.ScreensNavigator
import com.lab.ddlite.ui.restaurantlist.RestaurantListContract.*
import kotlinx.coroutines.flow.collect

class RestaurantListActivity : BaseActivity(), Listener {
    companion object {
        val TAG: String = RestaurantListActivity::class.java.simpleName
    }

    private lateinit var screensNavigator: ScreensNavigator
    private lateinit var viewProxy: RestaurantListViewProxy

    private val viewModel: RestaurantListViewModel by viewModels {
        compositionRoot.viewModelFactory
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ViewRestaurantListBinding.inflate(activityCompositionRoot.layoutInflater)
        setContentView(binding.root)
        screensNavigator = compositionRoot.screensNavigator
        viewProxy = compositionRoot.viewProxyFactory.newRestaurantListViewProxy(binding)
        initialize(savedInstanceState)
    }

    private fun initialize(savedInstanceState: Bundle?) {
        setTitle(R.string.discover)

        viewProxy.render(viewModel.currentState)

        lifecycleScope.launchWhenStarted {
            viewModel.viewState.collect {
                Log.d(TAG, "State: ${it.javaClass.simpleName}")
                viewProxy.render(it)
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.effect.collect {
                when (it) {
                    is ViewEffect.NavigateToDetailScreen -> {
                        screensNavigator.toRestaurantDetail(
                            it.id,
                            it.restaurant
                        )
                    }
                    is ViewEffect.ShowError -> {
                        // delegate to view to show error
                        viewProxy.handle(it)
                    }
                }
            }
        }

        if (savedInstanceState == null) {
            lifecycleScope.launchWhenCreated {
                viewModel.process(ViewEvent.LoadEvent)
            }
        }
    }

    override fun onStart() {
        super.onStart()
        viewProxy.registerListener(this)
    }

    override fun onStop() {
        viewProxy.unregisterListener(this)
        super.onStop()
    }

    override fun onEvent(event: ViewEvent) {
        Log.d(TAG, "Event: ${event.javaClass.simpleName}")
        viewModel.process(event)
    }
}