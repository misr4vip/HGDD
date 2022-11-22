package com.example.hgdd

import android.Manifest
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Base64
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import com.example.hgdd.models.CateogryWithOutPicResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.example.hgdd.api.RetrofitClient
import android.widget.ArrayAdapter
import androidx.fragment.app.FragmentActivity
import com.example.hgdd.models.DefaultResponse
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.TedPermission
import gun0912.tedbottompicker.TedBottomPicker
import kotlinx.android.synthetic.main.activity_admin_brand.*
import java.io.ByteArrayOutputStream
import java.io.FileInputStream


class AdminBrandActivity : AppCompatActivity() {
    var ba :ByteArray? = null
    var imgString: String? = null
    var cat_name: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_brand)
        getSpData()

        sp_cat_name_brand.onItemSelectedListener =object :AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                cat_name= parent!!.getItemAtPosition(position).toString()
                Toast.makeText(this@AdminBrandActivity,cat_name,Toast.LENGTH_LONG).show()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
        }
    }

    override fun onStart() {
        super.onStart()

        getImg_Brand.setOnClickListener {
            val permissionlistener = object : PermissionListener {
                override fun onPermissionDenied(deniedPermissions: java.util.ArrayList<String>?) {
                    Toast.makeText(this@AdminBrandActivity, "Permission Denied\n$deniedPermissions", Toast.LENGTH_SHORT).show()
                }

                override fun onPermissionGranted() {
                    UpdateImage(this@AdminBrandActivity)
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

        btn_Save_Brand.setOnClickListener {

            if (imgString != null)
            {
                RetrofitClient.instance.createBrand(et_BrandName_Brand.text.toString(),cat_name!!,imgString!!)
                    .enqueue(object: Callback<DefaultResponse> {
                        override fun onFailure(call: Call<DefaultResponse>, t: Throwable) {
                            Toast.makeText(applicationContext, t.message, Toast.LENGTH_LONG).show()
                        }

                        override fun onResponse(call: Call<DefaultResponse>, response: Response<DefaultResponse>) {
                            Toast.makeText(this@AdminBrandActivity,response.body()?.message,Toast.LENGTH_LONG).show()
                            if(!response.body()!!.error)
                            {
                                getImg_Brand.visibility = View.GONE
                                imgV_Logo_Brand.visibility = View.VISIBLE

                            }


                        }

                    })
            }
        }
    }

    private fun UpdateImage(ctx : FragmentActivity) {
        TedBottomPicker.with(this@AdminBrandActivity)
            .show {
                // here is selected image uri
                val bitmap=  it.encodedPath     //  /storage/emulated/0/DCIM/Camera/IMG_20190314_183909.jpg
                val fis = FileInputStream(bitmap)
                val  img = BitmapFactory.decodeStream(fis)
                imgV_Logo_Brand.setImageBitmap(img)
                imgV_Logo_Brand.visibility= View.VISIBLE
                getImg_Brand.visibility = View.GONE
                val baos = ByteArrayOutputStream()
                img.compress(Bitmap.CompressFormat.JPEG,100,baos)
                ba = baos.toByteArray()
                val t =   Base64.encodeToString(ba , Base64.DEFAULT)
                imgString = t

            }




    }
    fun getSpData() {

        RetrofitClient.instance.getCateogryWithOutPic("Nothing")
            .enqueue(object : Callback<CateogryWithOutPicResponse> {
                override fun onFailure(call: Call<CateogryWithOutPicResponse>, t: Throwable)
                    {
                        Toast.makeText(applicationContext, t.message, Toast.LENGTH_LONG).show()

                    }

                override fun onResponse(
                    call: Call<CateogryWithOutPicResponse>,
                    response: Response<CateogryWithOutPicResponse>)
                    {
                      //  Toast.makeText(this@AdminBrandActivity,"Good",Toast.LENGTH_LONG).show()
                        var catList :ArrayList<String> = arrayListOf()

                        for (i in 0..response.body()!!.CateogryWithoutPic.size-1)
                        {
                             catList.add(response.body()!!.CateogryWithoutPic[i].catName)
                        }


                        var adapter = ArrayAdapter(this@AdminBrandActivity,android.R.layout.simple_dropdown_item_1line,catList)

                        adapter.insert("-- Chose Cateogry --",0)

                        sp_cat_name_brand.adapter = adapter

                    }
            })
    }

    override fun onResume() {
        super.onResume()
        getSpData()
    }
}






