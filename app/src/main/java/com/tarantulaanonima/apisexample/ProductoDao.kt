package com.tarantulaanonima.apisexample

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ProductoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProducto(producto: ProductoEntity)

    @Query("SELECT * FROM productos WHERE id = :productId")
    suspend fun getProductoById(productId: Int): ProductoEntity?
}
