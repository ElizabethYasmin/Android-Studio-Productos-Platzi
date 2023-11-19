package com.tarantulaanonima.apisexample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.SearchView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.tarantulaanonima.apisexample.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Response

class MainActivity : AppCompatActivity(), SearchView.OnQueryTextListener,
    androidx.appcompat.widget.SearchView.OnQueryTextListener {

    private lateinit var binding: ActivityMainBinding
//    private lateinit var adapter: ProductoAdapter
//    private val productosImages = mutableListOf<String>()
    private lateinit var adapter: ProductoAdapter
    private val productosList = mutableListOf<ProductosResponse>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        //setContentView(R.layout.activity_main)
        setContentView(binding.root)
        initRecyclerView()
        binding.svProductos.setOnQueryTextListener(this)//error???/////funciona
        binding.btnOrdenarPorPrecio.setOnClickListener {
            orderProductsByPrice()
        }

    }



//    private fun initRecyclerView() {
//        adapter = ProductoAdapter(productosImages)
//        binding.rvProductos.layoutManager=LinearLayoutManager(this)
//        binding.rvProductos.adapter = adapter
//    }
    private fun initRecyclerView() {
        adapter = ProductoAdapter(productosList)
        binding.rvProductos.layoutManager = LinearLayoutManager(this)
        binding.rvProductos.adapter = adapter
    }

    private fun getRetrofit():Retrofit{
        return Retrofit.Builder()
            .baseUrl("https://api.escuelajs.co/api/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private suspend fun handleResponse(call: Response<List<ProductosResponse>>) {
        withContext(Dispatchers.Main) {
            if (call.isSuccessful) {
                val response = call.body()
                if (response != null) {
                    productosList.clear()
                    productosList.addAll(response)
                    adapter.notifyDataSetChanged()
                } else {
                    showError()
                }
            } else {
                showError()
            }
        }
    }


    private fun searchByTitle(title: String) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val call = getRetrofit().create(APIService::class.java).getProducts(title = title)
                handleResponse(call)
            } catch (e: Exception) {
                e.printStackTrace()
                showError()
            }
        }
    }

    private fun orderProductsByPrice() {
        productosList.sortBy { it.price }
        adapter.notifyDataSetChanged()
    }



    private fun showError() {
        Toast.makeText(this, "Ha ocurrido un ERROR", Toast.LENGTH_SHORT).show()
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        if (!query.isNullOrEmpty()) {
            searchByTitle(query)
        }
        return true
    }


    override fun onQueryTextChange(newText: String?): Boolean {
        return true
    }
}