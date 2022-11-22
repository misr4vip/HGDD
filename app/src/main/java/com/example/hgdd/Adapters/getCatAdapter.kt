package com.example.hgdd.Adapters

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.example.hgdd.R
import com.example.hgdd.models.Cateogry
import kotlinx.android.synthetic.main.category_item.view.*

class getCatAdapter(context:Context,cat:ArrayList<Cateogry>):ArrayAdapter<Cateogry>(context,0,cat) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val viewcell = LayoutInflater.from(context).inflate(R.layout.category_item,parent,false)
        var mycat = getItem(position)
        viewcell.tv_item_xml.text = mycat!!.catName
        var image : Bitmap? = null
        val decodedString = Base64.decode(mycat.catPic, Base64.DEFAULT)
        image = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)
        viewcell.img_item_xml.setImageBitmap(image)
        return viewcell
    }


}