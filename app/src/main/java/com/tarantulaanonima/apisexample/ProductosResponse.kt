package com.tarantulaanonima.apisexample

import com.google.gson.annotations.SerializedName

data class ProductosResponse(
    @SerializedName("id")
    val id: Int,
    @SerializedName("title")
    val title: String,
    @SerializedName("price")
    val price: Int,
    @SerializedName("description")
    val description: String,
    @SerializedName("category")
    val category: Category,
    @SerializedName("images")
    val images: List<String>
)

data class Category(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("image")
    val image: String
)
