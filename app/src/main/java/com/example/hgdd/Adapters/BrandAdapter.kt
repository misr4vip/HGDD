package com.example.hgdd.Adapters

import  android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.example.hgdd.R
import com.example.hgdd.models.Brands
import kotlinx.android.synthetic.main.category_item.view.*


class BrandAdapter (context: Context, brand:ArrayList<Brands>): ArrayAdapter<Brands>(context,0,brand){


    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val viewcell = LayoutInflater.from(context).inflate(R.layout.category_item,parent,false)
        var mybrand = getItem(position)
        viewcell.tv_item_xml.text = mybrand!!.brandName
        var image : Bitmap? = null
        val decodedString = Base64.decode(mybrand.brandPic, Base64.DEFAULT)
        image = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)
        viewcell.img_item_xml.setImageBitmap(image)
        return viewcell
    }


}