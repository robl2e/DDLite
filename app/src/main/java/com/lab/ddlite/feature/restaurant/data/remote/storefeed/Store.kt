package com.lab.ddlite.feature.restaurant.data.remote.storefeed

import com.lab.ddlite.feature.restaurant.Restaurant

data class Store(
    val average_rating: Double,
    val business_id: Int,
    val cover_img_url: String,
    val delivery_fee: Int,
    val delivery_fee_monetary_fields: DeliveryFeeMonetaryFields,
    val description: String,
    val display_delivery_fee: String,
    val distance_from_consumer: Double,
    val extra_sos_delivery_fee: Int,
    val extra_sos_delivery_fee_monetary_fields: ExtraSosDeliveryFeeMonetaryFields,
    val header_img_url: String,
    val id: Int,
    val is_consumer_subscription_eligible: Boolean,
    val is_newly_added: Boolean,
    val location: Location,
    val menus: List<Menu>,
    val merchant_promotions: List<MerchantPromotion>,
    val name: String,
    val next_close_time: String,
    val next_open_time: String,
    val num_ratings: Int,
    val price_range: Int,
    val promotion_delivery_fee: Int,
    val service_rate: Any,
    val status: Status,
    val url: String
)

fun Store.toRestaurant(): Restaurant {
    return Restaurant(
        name = name,
        id = id,
        description = description,
        imageUrl = cover_img_url,
        isOpen = status.asap_available,
        orderTimeRange = status.asap_minutes_range,
        rating = average_rating
    )
}