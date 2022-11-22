package com.example.hgdd.ui.brands

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.hgdd.R

class brandsFragment : Fragment() {

    private lateinit var brandsViewModel:  brandsViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        brandsViewModel =
            ViewModelProviders.of(this).get(brandsViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_brands, container, false)
        val textView: TextView = root.findViewById(R.id.text_brands)
        brandsViewModel.text.observe(this, Observer {
            textView.text = it
        })
        return root
    }
}