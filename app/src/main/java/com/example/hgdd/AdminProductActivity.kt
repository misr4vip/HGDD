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
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.TedPermission
import gun0912.tedbottompicker.TedBottomPicker
import kotlinx.android.synthetic.main.activity_admin_product.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.ByteArrayOutputStream
import java.io.FileInputStream

class AdminProductActivity : AppCompatActivity() {
    var ba :ByteArray? = null
    var imgString: String? = null
    var Brand_name: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_product)


        sp_Brand_name_product.onItemSelectedListener =object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                Brand_name= parent!!.getItemAtPosition(position).toString()
                Toast.makeText(this@AdminProductActivity,Brand_name, Toast.LENGTH_LONG).show()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
        }

    }


    override fun onStart() {
        super.onStart()

        getImg_product.setOnClickListener {
            val permissionlistener = object : PermissionListener {
                override fun onPermissionDenied(deniedPermissions: java.util.ArrayList<String>?) {
                    Toast.makeText(this@AdminProductActivity, "Permission Denied\n$deniedPermissions", Toast.LENGTH_SHORT).show()
                }

                override fun onPermissionGranted() {
                    UpdateImage(this@AdminProductActivity)
                }




            }
            TedPermission.with(this)
                .setPermissionListener(permissionlistener)
                .setDeniedMessage("If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [Permission]")
                .setPermissions(
                    Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.CAMERA)
                .check()



        }

        btn_Save_product.setOnClickListener {

            if (imgString != null)
            {
                RetrofitClient.instance.createProduct(et_prod_name_prod.text.toString(),et_prodPrice_prod.text.toString(),Brand_name!!,imgString!!)
                    .enqueue(object: Callback<DefaultResponse> {
                        override fun onFailure(call: Call<DefaultResponse>, t: Throwable) {
                            Toast.makeText(applicationContext, t.message, Toast.LENGTH_LONG).show()
                        }

                        override fun onResponse(call: Call<DefaultResponse>, response: Response<DefaultResponse>) {
                            Toast.makeText(this@AdminProductActivity,response.body()?.message,Toast.LENGTH_LONG).show()
                            if(!response.body()!!.error)
                            {
                                getImg_product.visibility = View.GONE
                                imgV_Logo_product.visibility = View.VISIBLE

                            }


                        }

                    })
            }
        }
    }

    private fun UpdateImage(ctx : FragmentActivity) {
        TedBottomPicker.with(this@AdminProductActivity)
            .show {
                // here is selected image uri
                val bitmap=  it.encodedPath     //  /storage/emulated/0/DCIM/Camera/IMG_20190314_183909.jpg
                val fis = FileInputStream(bitmap)
                val  img = BitmapFactory.decodeStream(fis)
                imgV_Logo_product.setImageBitmap(img)
                imgV_Logo_product.visibility= View.VISIBLE
                getImg_product.visibility = View.GONE
                val baos = ByteArrayOutputStream()
                img.compress(Bitmap.CompressFormat.JPEG,100,baos)
                ba = baos.toByteArray()
                val t =   Base64.encodeToString(ba , Base64.DEFAULT)
                imgString = t

            }




    }
    fun getSpData() {

        RetrofitClient.instance.getBrandWithOutPic("Nothing")
            .enqueue(object : Callback<BrandWithOutPicresponse> {
                override fun onFailure(call: Call<BrandWithOutPicresponse>, t: Throwable)
                {
                    Toast.makeText(applicationContext, t.message, Toast.LENGTH_LONG).show()

                }

                override fun onResponse(
                    call: Call<BrandWithOutPicresponse>,
                    response: Response<BrandWithOutPicresponse>
                )
                {
                    //  Toast.makeText(this@AdminBrandActivity,"Good",Toast.LENGTH_LONG).show()
                    var brandList :ArrayList<String> = arrayListOf()

                    for (i in 0..response.body()!!.BrandWithOutPic.size-1)
                    {
                        brandList.add(response.body()!!.BrandWithOutPic[i].brandName)
                    }


                    var adapter = ArrayAdapter(this@AdminProductActivity,android.R.layout.simple_dropdown_item_1line,brandList)

                    adapter.insert("-- Chose Brand --",0)

                    sp_Brand_name_product.adapter = adapter

                }
            })
    }

    override fun onResume() {
        super.onResume()
        getSpData()
    }
}
