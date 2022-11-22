package com.example.hgdd

import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import android.view.Menu
import com.example.hgdd.Adapters.getCatAdapter
import com.example.hgdd.api.RetrofitClient
import com.example.hgdd.models.Cateogry
import com.example.hgdd.models.CateogryResponse
import kotlinx.android.synthetic.main.activity_cateogry.*
import retrofit2.Call
import retrofit2.Response

class UserActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)
        appBarConfiguration = AppBarConfiguration(setOf( R.id.nav_home, R.id.nav_devices, R.id.nav_brands, R.id.nav_profile, R.id.nav_logout ), drawerLayout)
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.user, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun onStart() {
        super.onStart()
        RetrofitClient.instance.getCateogry("nothing")
            .enqueue(object : retrofit2.Callback<CateogryResponse> {
                override fun onFailure(call: Call<CateogryResponse>, t: Throwable) {

                }

                override fun onResponse(
                    call: Call<CateogryResponse>,
                    response: Response<CateogryResponse>
                ) {


                    var catarray: ArrayList<Cateogry> = response.body()!!.Cateogry
                    var myAdapter = getCatAdapter(this@UserActivity!!, catarray)
                    gv_Devices.adapter = myAdapter


                }

            })
    }
    }
