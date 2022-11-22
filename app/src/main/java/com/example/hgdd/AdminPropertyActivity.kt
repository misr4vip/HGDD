package com.example.hgdd

import android.Manifest
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Base64
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import com.example.hgdd.api.RetrofitClient
import com.example.hgdd.models.BrandWithOutPicresponse
import com.example.hgdd.models.DefaultResponse
import com.example.hgdd.models.ProductWithOutPicResponse
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.TedPermission
import gun0912.tedbottompicker.TedBottomPicker
import kotlinx.android.synthetic.main.activity_admin_property.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.ByteArrayOutputStream
import java.io.FileInputStream

class AdminPropertyActivity : AppCompatActivity() {

    var product_name: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_property)
        sp_Product_name_property.onItemSelectedListener =object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                product_name= parent!!.getItemAtPosition(position).toString()
                Toast.makeText(this@AdminPropertyActivity,product_name, Toast.LENGTH_LONG).show()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
        }

    }

    override fun onStart() {
        super.onStart()



        btn_Save_property.setOnClickListener {

            if (et_property_name_property.text.length> 0 && et_property_value_property.text.length>0)
            {
                RetrofitClient.instance.createProperty(et_property_name_property.text.toString(),et_property_value_property.text.toString(),product_name!!)
                    .enqueue(object: Callback<DefaultResponse> {
                        override fun onFailure(call: Call<DefaultResponse>, t: Throwable) {
                            Toast.makeText(applicationContext, t.message, Toast.LENGTH_LONG).show()
                        }

                        override fun onResponse(call: Call<DefaultResponse>, response: Response<DefaultResponse>) {
                            Toast.makeText(this@AdminPropertyActivity,response.body()?.message,Toast.LENGTH_LONG).show()
                            if(!response.body()!!.error)
                            {
                                Toast.makeText(applicationContext, "Property Added Successfully", Toast.LENGTH_LONG).show()

                            }


                        }

                    })
            }
        }
    }


    fun getSpData() {

        RetrofitClient.instance.getProductWithOutPic("Nothing")
            .enqueue(object : Callback<ProductWithOutPicResponse> {
                override fun onFailure(call: Call<ProductWithOutPicResponse>, t: Throwable)
                {
                    Toast.makeText(applicationContext, t.message, Toast.LENGTH_LONG).show()

                }

                override fun onResponse(
                    call: Call<ProductWithOutPicResponse>,
                    response: Response<ProductWithOutPicResponse>
                )
                {
                    //  Toast.makeText(this@AdminBrandActivity,"Good",Toast.LENGTH_LONG).show()
                    var brandList :ArrayList<String> = arrayListOf()

                    for (i in 0..response.body()!!.ProductWithOutPic.size-1)
                    {
                        brandList.add(response.body()!!.ProductWithOutPic[i].productName)
                    }


                    var adapter = ArrayAdapter(this@AdminPropertyActivity,android.R.layout.simple_dropdown_item_1line,brandList)

                    adapter.insert("-- Chose Product --",0)

                    sp_Product_name_property.adapter = adapter

                }
            })
    }

    override fun onResume() {
        super.onResume()
        getSpData()
    }
}
