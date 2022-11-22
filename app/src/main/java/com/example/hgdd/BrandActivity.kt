package com.example.hgdd

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.AdapterView
import android.widget.Toast
import com.example.hgdd.Adapters.BrandAdapter
import com.example.hgdd.api.RetrofitClient
import com.example.hgdd.models.Brands
import com.example.hgdd.models.BrandsResponse
import kotlinx.android.synthetic.main.activity_brand.*
import kotlinx.android.synthetic.main.category_item.view.*
import retrofit2.Call
import retrofit2.Response

class BrandActivity : AppCompatActivity() {
    var catname :String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_brand)

        val intent = intent
        var b = intent.extras
         catname = b!!.getString("catName")
       // Toast.makeText(this,catname,Toast.LENGTH_LONG).show()
    }

    override fun onStart() {
        super.onStart()
        RetrofitClient.instance.getBrandsWithCatId(catname!!)
            .enqueue(object: retrofit2.Callback<BrandsResponse> {
                override fun onFailure(call: Call<BrandsResponse>, t: Throwable) {

                }

                override fun onResponse(call: Call<BrandsResponse>, response: Response<BrandsResponse>) {
                    if (response.body()!!.Brands.isNullOrEmpty())
                    {
                        Toast.makeText(applicationContext,"Sorry This Cateogry Hasn't Brand Yet",Toast.LENGTH_LONG).show()

                    }else
                    {
                        var brandarray : ArrayList<Brands> = response.body()!!.Brands
                        var myAdapter = BrandAdapter(this@BrandActivity!!,brandarray)
                        gv_Brands.adapter=myAdapter
                    }





                }

            })

        gv_Brands.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->

            var Brand_name = view.tv_item_xml.text.toString()
            var intent = Intent(applicationContext,ProductActivity::class.java)
            var b = Bundle()
            b.putString("brandName",Brand_name)
            intent.putExtras(b)
            startActivity(intent)

        }



    }
}
