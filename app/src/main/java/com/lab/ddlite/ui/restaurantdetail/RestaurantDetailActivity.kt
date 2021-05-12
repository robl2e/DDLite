package com.lab.ddlite.ui.restaurantdetail

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.lab.ddlite.R
import com.lab.ddlite.databinding.ViewRestaurantDetailBinding
import com.lab.ddlite.feature.restaurant.Restaurant
import com.lab.ddlite.ui.common.activity.BaseActivity
import com.lab.ddlite.ui.common.navigator.ScreensNavigator
import com.lab.ddlite.ui.restaurantdetail.RestaurantDetailContract.*
import kotlinx.coroutines.flow.collect

class RestaurantDetailActivity : BaseActivity(), Listener {

    companion object {
        val TAG: String = RestaurantDetailActivity::class.java.simpleName

        private const val EXTRA_ID = "EXTRA_ID"
        private const val EXTRA_RESTAURANT = "EXTRA_RESTAURANT"

        fun start(activity: Activity, id: Int, restaurant: Restaurant?) {
            val intent = Intent(activity, RestaurantDetailActivity::class.java)
            intent.putExtra(EXTRA_ID, id)
            restaurant?.let { intent.putExtra(EXTRA_RESTAURANT, it) }
            activity.startActivity(intent)
            activity.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }
    }

    private lateinit var screensNavigator: ScreensNavigator
    private lateinit var viewProxy: RestaurantDetailViewProxy

    private val viewModel: RestaurantDetailViewModel by viewModels {
        compositionRoot.viewModelFactory
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ViewRestaurantDetailBinding.inflate(activityCompositionRoot.layoutInflater)
        setContentView(binding.root)
        screensNavigator = compositionRoot.screensNavigator
        viewProxy = compositionRoot.viewProxyFactory.newRestaurantDetailViewProxy(binding)
        initialize(savedInstanceState)
    }


    private fun initialize(savedInstanceState: Bundle?) {
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
                    is ViewEffect.NavBack -> screensNavigator.navigateBack()
                    is ViewEffect.ShowError -> viewProxy.handle(it)
                }
            }
        }

        if (savedInstanceState == null) {
            lifecycleScope.launchWhenCreated {
                intent.getParcelableExtra<Restaurant>(EXTRA_RESTAURANT)
                    ?.toRestaurantDetailUIModel()?.let {
                        viewModel.process(ViewEvent.InitializeEvent(it))
                    }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        viewProxy.registerListener(this)
        intent?.let {
            val id = it.getIntExtra(EXTRA_ID, -1)
            viewModel.process(ViewEvent.LoadEvent(id))
        }
    }

    override fun onStop() {
        viewProxy.unregisterListener(this)
        super.onStop()
    }

    override fun onEvent(event: ViewEvent) {
        Log.d(TAG, "onEvent: ${event.javaClass.simpleName}")
        viewModel.process(event)
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}