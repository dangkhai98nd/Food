package com.test.food.data.network.params

import com.google.gson.annotations.SerializedName

data class LoginParams(
    val username : String,
    val password : String,
    @SerializedName("grant_type")
    val grantType : String,
    @SerializedName("device_token")
    val deviceToken : String,
    @SerializedName("device_type")
    val deviceType : String
)