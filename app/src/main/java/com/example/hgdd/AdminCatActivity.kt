package com.example.hgdd
import android.Manifest
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Base64
import android.view.View
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import com.gun0912.tedpermission.TedPermission
import com.example.hgdd.api.RetrofitClient
import com.example.hgdd.models.DefaultResponse
import gun0912.tedbottompicker.TedBottomPicker
import kotlinx.android.synthetic.main.activity_admin_cat.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.ByteArrayOutputStream
import java.io.FileInputStream
import com.gun0912.tedpermission.PermissionListener
import java.util.ArrayList






class AdminCatActivity : AppCompatActivity() {
    var ba :ByteArray? = null
    var imgString: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_cat)
    }


    override fun onStart() {
        super.onStart()

        getImg.setOnClickListener {
            val permissionlistener = object : PermissionListener {
                override fun onPermissionDenied(deniedPermissions: ArrayList<String>?) {
                    Toast.makeText(this@AdminCatActivity, "Permission Denied\n$deniedPermissions", Toast.LENGTH_SHORT).show()
                }

                override fun onPermissionGranted() {
                    UpdateImage(this@AdminCatActivity)
                }




            }
            TedPermission.with(this)
                .setPermissionListener(permissionlistener)
                .setDeniedMessage("If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [Permission]")
                .setPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.CAMERA)
                .check()



        }

        btn_Save_cat.setOnClickListener {

            if (imgString != null)
            {
                RetrofitClient.instance.createCateogry(et_catName_Cat.text.toString(),imgString!!)
                    .enqueue(object: Callback<DefaultResponse> {
                        override fun onFailure(call: Call<DefaultResponse>, t: Throwable) {
                            Toast.makeText(applicationContext, t.message, Toast.LENGTH_LONG).show()
                        }

                        override fun onResponse(call: Call<DefaultResponse>, response: Response<DefaultResponse>) {
                            Toast.makeText(this@AdminCatActivity,response.body()?.message,Toast.LENGTH_LONG).show()
                            if(!response.body()!!.error)
                            {
                                getImg.visibility = View.GONE
                                imgV_Logo_Cat.visibility = View.VISIBLE

                            }


                        }

                    })
            }
        }
    }

    private fun UpdateImage(ctx : FragmentActivity) {
        TedBottomPicker.with(this@AdminCatActivity)
            .show {
                // here is selected image uri
                val bitmap=  it.encodedPath     //  /storage/emulated/0/DCIM/Camera/IMG_20190314_183909.jpg
                val fis = FileInputStream(bitmap)
                val  img = BitmapFactory.decodeStream(fis)
                imgV_Logo_Cat.setImageBitmap(img)
                imgV_Logo_Cat.visibility=View.VISIBLE
                getImg.visibility = View.GONE
                val baos = ByteArrayOutputStream()
                img.compress(Bitmap.CompressFormat.JPEG,100,baos)
                ba = baos.toByteArray()
                val t =   Base64.encodeToString(ba , Base64.DEFAULT)
                imgString = t

            }




    }

}
