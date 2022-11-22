package com.example.hgdd

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.AdapterView
import android.widget.Toast
import com.example.hgdd.Adapters.getCatAdapter
import com.example.hgdd.api.RetrofitClient
import com.example.hgdd.models.Cateogry
import com.example.hgdd.models.CateogryResponse
import kotlinx.android.synthetic.main.activity_cateogry.*
import kotlinx.android.synthetic.main.category_item.view.*
import retrofit2.Call
import retrofit2.Response

class CateogryActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cateogry)
    }
        override fun onStart() {
            super.onStart()
            RetrofitClient.instance.getCateogry("nothing")
                .enqueue(object: retrofit2.Callback<CateogryResponse> {
                    override fun onFailure(call: Call<CateogryResponse>, t: Throwable) {
                        Toast.makeText(applicationContext,t.message ,Toast.LENGTH_LONG).show()
                    }

                    override fun onResponse(call: Call<CateogryResponse>, response: Response<CateogryResponse>) {

                        var catarray : ArrayList<Cateogry> = response.body()!!.Cateogry
                        var myAdapter = getCatAdapter(this@CateogryActivity!!,catarray)
                        gv_Devices.adapter=myAdapter


                    }

                })

        gv_Devices.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->

            var cateogry_name = view.tv_item_xml.text.toString()
            var intent = Intent(applicationContext,BrandActivity::class.java)
            var b = Bundle()
            b.putString("catName",cateogry_name)
            intent.putExtras(b)
            startActivity(intent)

        }



    }
}
