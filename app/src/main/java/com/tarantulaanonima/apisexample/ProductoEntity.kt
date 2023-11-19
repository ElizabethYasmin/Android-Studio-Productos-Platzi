package com.tarantulaanonima.apisexample

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "productos")
data class ProductoEntity(
    @PrimaryKey val id: Int,
    val title: String,
    val price: Int,
    val description: String,
    val category: String,
    val image: String
)