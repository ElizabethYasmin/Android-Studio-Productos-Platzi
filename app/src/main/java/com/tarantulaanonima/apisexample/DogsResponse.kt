package com.tarantulaanonima.apisexample

import com.google.gson.annotations.SerializedName

data class DogsResponse(
    @SerializedName("message")
    val images: List<String>,
    @SerializedName("status")
    val status: String
)