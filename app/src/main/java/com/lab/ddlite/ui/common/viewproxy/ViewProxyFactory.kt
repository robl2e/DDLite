package com.lab.ddlite.ui.common.viewproxy


import com.lab.ddlite.databinding.ViewRestaurantDetailBinding
import com.lab.ddlite.databinding.ViewRestaurantListBinding
import com.lab.ddlite.ui.restaurantdetail.RestaurantDetailViewProxy
import com.lab.ddlite.ui.restaurantlist.RestaurantListViewProxy

class ViewProxyFactory {

    fun newRestaurantListViewProxy(binding: ViewRestaurantListBinding): RestaurantListViewProxy {
        return RestaurantListViewProxy(binding)
    }

    fun newRestaurantDetailViewProxy(binding: ViewRestaurantDetailBinding): RestaurantDetailViewProxy {
        return RestaurantDetailViewProxy(binding)
    }
}