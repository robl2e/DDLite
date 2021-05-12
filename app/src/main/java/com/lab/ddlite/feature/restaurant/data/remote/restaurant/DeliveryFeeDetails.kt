package com.lab.ddlite.feature.restaurant.data.remote.restaurant

data class DeliveryFeeDetails(
    val discount: Discount,
    val final_fee: FinalFee,
    val original_fee: OriginalFee,
    val surge_fee: SurgeFee
)