package com.example.hgdd.models

class spinnerArray {

    fun spinnerArrayList(): ArrayList<String>
    {
        var MyArray = ArrayList<String>(99)
        var i = 0
        while (i < 100)
        {
            MyArray.add("#" + i+"5"+i )
            i++
        }
        return MyArray
    }
}