package com.example.hgdd

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.hgdd.api.RetrofitClient
import com.example.hgdd.models.LoginResponse
import com.example.hgdd.storage.SharedPrefManager
import kotlinx.android.synthetic.main.activity_login.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val shared = getSharedPreferences("LoginDetails", Context.MODE_PRIVATE)
        val mail =  shared.getString("UserName",null)
        if (mail != null)
        {
            et_userName.setText(mail)
        }
        btn_login.setOnClickListener {
            val email = et_userName.text.toString().trim()
            val password = et_PWD.text.toString().trim()
            if(email.isEmpty()){
                et_userName.error = "Email required"
                et_userName.requestFocus()
                return@setOnClickListener
            }
            if(password.isEmpty()){
                et_PWD.error = "Password required"
                et_PWD.requestFocus()
                return@setOnClickListener
            }
            RetrofitClient.instance.userLogin(email, password)
                .enqueue(object: Callback<LoginResponse> {
                    override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                        Toast.makeText(applicationContext, t.message, Toast.LENGTH_LONG).show()
                    }
                    override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                        if(!response.body()?.error!!){
                            SharedPrefManager.getInstance(applicationContext).saveUser(response.body()?.user!!)
                            val shared = getSharedPreferences("LoginDetails", Context.MODE_PRIVATE)
                            with(shared.edit()){
                                putString("UserName",et_userName.text.toString())
                                putString("Password",et_PWD.text.toString())
                                apply()
                            }

                            val intent = Intent(applicationContext, UserActivity::class.java)
                            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                            startActivity(intent)
                        }else{
                            Toast.makeText(applicationContext, response.body()?.message, Toast.LENGTH_LONG).show()
                        }

                    }
                })

        }
    }
}
