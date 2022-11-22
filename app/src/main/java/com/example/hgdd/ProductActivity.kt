package com.example.hgdd

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.hgdd.Adapters.ProductAdapter
import com.example.hgdd.api.RetrofitClient
import com.example.hgdd.models.Products
import com.example.hgdd.models.ProductsResponse
import kotlinx.android.synthetic.main.activity_product.*
import retrofit2.Call
import retrofit2.Response

class ProductActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product)

    }

    override fun onStart() {
        super.onStart()

        val intent = intent
        val b = intent.extras
        var brand_name = b!!.getString("brandName")
       // Toast.makeText(applicationContext,brand_name,Toast.LENGTH_LONG).show()

        RetrofitClient.instance.getProductsWithbrandId(brand_name!!).enqueue(object: retrofit2.Callback<ProductsResponse> {
                override fun onFailure(call: Call<ProductsResponse>, t: Throwable) {
                    Toast.makeText(applicationContext,t.message ,Toast.LENGTH_LONG).show()
                }

                override fun onResponse(call: Call<ProductsResponse>, response: Response<ProductsResponse>) {

                    var productarray : ArrayList<Products> = response.body()!!.product
                    var myAdapter = ProductAdapter(applicationContext,productarray)
                    gv_Products.adapter=myAdapter


                }

            })
    }
}
