package com.lab.ddlite.feature.restaurant.data.remote.storefeed

data class MerchantPromotion(
    val category_new_store_customers_only: Boolean,
    val daypart_constraints: List<Any>,
    val delivery_fee: Int,
    val delivery_fee_monetary_fields: DeliveryFeeMonetaryFieldsX,
    val id: Int,
    val minimum_subtotal: Int,
    val minimum_subtotal_monetary_fields: MinimumSubtotalMonetaryFields
)