package com.tarantulaanonima.apisexample

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.Call
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Url

interface APIService {
    //@GET
    //suspend fun getDogsByBreeds(@Url url:String): Response<DogsResponse>//se coloca suspend por la coorutina


    //suspend fun getProductosByName(@Url url:String): Response<ProductosResponse>

    @GET("products")
    suspend fun getProducts(
        @Query("title") title: String? = null,
        @Query("price") price: Int? = null,
        @Query("price_min") priceMin: Int? = null,
        @Query("price_max") priceMax: Int? = null,
        @Query("categoryId") categoryId: Int? = null,
        @Query("offset") offset: Int? = null,
        @Query("limit") limit: Int? = null
    ): Response<List<ProductosResponse>>
}