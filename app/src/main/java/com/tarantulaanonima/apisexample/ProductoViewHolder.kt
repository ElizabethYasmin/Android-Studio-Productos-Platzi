package com.tarantulaanonima.apisexample

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.tarantulaanonima.apisexample.databinding.ItemProductosBinding

class ProductoViewHolder (view: View): RecyclerView.ViewHolder(view){

    private val binding = ItemProductosBinding.bind(view)

    fun bind(product: ProductosResponse) {
        binding.textViewNombreProducto.text = product.title
        binding.textViewPrecioProducto.text = "Precio: ${product.price} $" // Puedes personalizar esto según tu necesidad
        Picasso.get().load(product.images.first()).into(binding.ivProductos)
    }
}