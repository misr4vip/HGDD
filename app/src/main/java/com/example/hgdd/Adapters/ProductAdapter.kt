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
import com.example.hgdd.models.Products
import kotlinx.android.synthetic.main.product_row.view.*

class ProductAdapter (context: Context, product:ArrayList<Products>): ArrayAdapter<Products>(context,0,product) {


    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val viewcell = LayoutInflater.from(context).inflate(R.layout.product_row, parent, false)
        var myproduct = getItem(position)
        viewcell.tv_product_name_row.text = myproduct!!.productName
        viewcell.tv_product_price_row.text = myproduct.productPrice
        var image: Bitmap? = null
        val decodedString = Base64.decode(myproduct.productPic, Base64.DEFAULT)
        image = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)
        viewcell.img_product_row.setImageBitmap(image)
        return viewcell
    }
}