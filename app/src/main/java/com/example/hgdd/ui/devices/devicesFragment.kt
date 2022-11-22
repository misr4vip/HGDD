package com.example.hgdd.ui.devices

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.hgdd.Adapters.getCatAdapter
import com.example.hgdd.R
import com.example.hgdd.api.RetrofitClient
import com.example.hgdd.models.Cateogry
import com.example.hgdd.models.CateogryResponse
import kotlinx.android.synthetic.main.fragment_devices.*
import retrofit2.Call
import retrofit2.Response
import javax.security.auth.callback.Callback

class devicesFragment : Fragment() {



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
      //  devicesViewModel = ViewModelProviders.of(this).get(devicesViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_devices, container, false)
       // val textView: TextView = root.findViewById(R.id.text_Devices)


        return root
    }


}