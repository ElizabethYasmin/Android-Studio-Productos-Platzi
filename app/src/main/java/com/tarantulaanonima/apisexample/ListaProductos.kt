package com.tarantulaanonima.apisexample

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.tarantulaanonima.apisexample.databinding.FragmentListaProductosBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ListaProductos : Fragment() {
    private lateinit var binding: FragmentListaProductosBinding
    private lateinit var adapter: ProductoAdapter
    private val productosList = mutableListOf<ProductosResponse>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentListaProductosBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        binding.svProductos.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (!query.isNullOrEmpty()) {
                    searchByTitle(query)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }
        })
        binding.btnOrdenarPorPrecio.setOnClickListener {
            orderProductsByPrice()
        }
    }
    private fun initRecyclerView() {
        adapter = ProductoAdapter(productosList)
        binding.rvProductos.layoutManager = LinearLayoutManager(requireContext())
        binding.rvProductos.adapter = adapter
    }
    private fun getRetrofit(): Retrofit {
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
        Toast.makeText(requireContext(), "Ha ocurrido un ERROR", Toast.LENGTH_SHORT).show()
    }

//    override fun onQueryTextSubmit(query: String?): Boolean {
//        if (!query.isNullOrEmpty()) {
//            searchByTitle(query)
//        }
//        return true
//    }
//
//
//    override fun onQueryTextChange(newText: String?): Boolean {
//        return true
//    }
}