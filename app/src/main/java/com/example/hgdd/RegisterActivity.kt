package com.example.hgdd

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.widget.Toast
import com.example.hgdd.api.RetrofitClient
import com.example.hgdd.models.DefaultResponse
import com.example.hgdd.storage.SharedPrefManager
import kotlinx.android.synthetic.main.activity_register.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
    }
    override fun onStart() {
        super.onStart()

        if(SharedPrefManager.getInstance(this).isLoggedIn){
            val intent = Intent(applicationContext, ProfileActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

            startActivity(intent)
        }

        btn_register_cancel.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }


        btn_register_register.setOnClickListener {
            val repwd = et_REPWD.text.toString()
            val FirstName = et_FirstName.text.toString()
            val LastName = et_LastName.text.toString()
            val pwd = et_register_PWD.text.toString()
            val email = et_Email.text.toString()
            val password = et_register_PWD.text.toString()


            if (TextUtils.isEmpty(LastName)) {
                et_LastName.error = "Please enter your email"
                et_LastName.requestFocus()
                return@setOnClickListener
            }
            if (TextUtils.isEmpty(FirstName)) {
                et_FirstName.error = "Please enter your email"
                et_FirstName.requestFocus()
                return@setOnClickListener
            }

            if (TextUtils.isEmpty(email)) {
                et_Email.error = "Please enter your email"
                et_Email.requestFocus()
                return@setOnClickListener
            }

            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                et_Email.error = "Enter a valid email"
                et_Email.requestFocus()
                return@setOnClickListener
            }

            if (TextUtils.isEmpty(password)) {
                et_register_PWD.error = "Enter a password"
                et_register_PWD.requestFocus()
                return@setOnClickListener
            }

            if (pwd != repwd) {
                et_register_PWD.error = "PassWord Dismatch"
                return@setOnClickListener
            }
            if (pwd.length < 6) {
                et_register_PWD.error = "PassWord  is weak"
                return@setOnClickListener
            }

            RetrofitClient.instance.createUser(email,FirstName,LastName, pwd)
                .enqueue(object: Callback<DefaultResponse> {
                    override fun onFailure(call: Call<DefaultResponse>, t: Throwable) {
                        Toast.makeText(applicationContext, t.message, Toast.LENGTH_LONG).show()
                    }

                    override fun onResponse(call: Call<DefaultResponse>, response: Response<DefaultResponse>) {
                        Toast.makeText(applicationContext, response.body()!!.message, Toast.LENGTH_LONG).show()
                        if(!response.body()!!.error)
                        {
                            val intent = Intent(this@RegisterActivity,LoginActivity::class.java)
                            startActivity(intent)
                        }

                    }

                })



        }

    }
}
