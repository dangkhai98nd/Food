package com.test.food.data.network.responses

import com.google.gson.annotations.SerializedName

data class AuthResponse(
    @SerializedName("access_token")
    val accessToken : String,
    @SerializedName("expires_in")
    val expiresIn : Long,
    @SerializedName("refresh_token")
    val refreshToken: String
)