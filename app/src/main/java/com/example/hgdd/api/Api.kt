package com.example.hgdd.api

import com.example.hgdd.models.CateogryResponse
import com.example.hgdd.models.CateogryWithOutPicResponse
import com.example.hgdd.models.*

import retrofit2.Call
import retrofit2.http.*

interface Api {


    @FormUrlEncoded
    @POST("createUser")
   fun  createUser(
        @Field("email") email: String,
        @Field("firstName") firstName: String,
        @Field("lastName") lastName: String,
        @Field("pwd") password: String
    ): Call<DefaultResponse>

    @FormUrlEncoded
    @POST("userlogin")
    fun userLogin(
        @Field("email") email:String,
        @Field("pwd") password: String
    ):Call<LoginResponse>

    @FormUrlEncoded
    @POST("createCateogry")
    fun createCateogry(
        @Field("catName") catName:String,
        @Field("catPic") catPic: String
    ):Call<DefaultResponse>

    @FormUrlEncoded
    @POST("createBrand")
    fun createBrand(
        @Field("brandName") brandName:String,
        @Field("catName") catName:String,
        @Field("brandPic") brandPic: String
    ):Call<DefaultResponse>

    @FormUrlEncoded
    @POST("createProduct")
    fun createProduct(
        @Field("productName") productName:String,
        @Field("productPrice") productPrice:String,
        @Field("brandName") brandName: String,
    @Field("productPic") productPic: String
    ):Call<DefaultResponse>

    @FormUrlEncoded
    @POST("createProperty")
    fun createProperty(
        @Field("propertyName") propertyName:String,
        @Field("propertyValue") propertyValue:String,
        @Field("productName") productName: String
    ):Call<DefaultResponse>
    @FormUrlEncoded
    @POST("getCateogryWithOutPic")
    fun getCateogryWithOutPic(
        @Field("catName") catName:String
    ):Call<CateogryWithOutPicResponse>

    @FormUrlEncoded
    @POST("getCateogry")
    fun getCateogry(
        @Field("catName") catName:String
    ):Call<CateogryResponse>

    @FormUrlEncoded
    @POST("getBrandWithOutPic")
    fun getBrandWithOutPic(
        @Field("catName") brandName:String
    ):Call<BrandWithOutPicresponse>

    @FormUrlEncoded
    @POST("getBrandsWithCatId")
    fun getBrandsWithCatId(
        @Field("catName") MyCatName:String
    ):Call<BrandsResponse>

    @FormUrlEncoded
    @POST("getProductsWithbrandId")
    fun getProductsWithbrandId(
        @Field("brandName") MybrandName:String
    ):Call<ProductsResponse>

    @FormUrlEncoded
    @POST("getProductWithOutPic")
    fun getProductWithOutPic(
        @Field("productName") productName:String
    ):Call<ProductWithOutPicResponse>



    @FormUrlEncoded
    @POST("getuserprofilebyid")
    fun getuserprofilebyid(
        @Field("userid") userid : Int
    ):Call<UserProfileResponse>

    @FormUrlEncoded
    @PUT("updatepassword")
    fun  updatePassword(

        @Field("email") email: String,
        @Field("currentpassword") currentpassword: String,
        @Field("newpassword") newpassword: String
    ): Call<DefaultResponse>


    @FormUrlEncoded
    @POST("updateprofilepic")
    fun updateprofilepic(
        @Field("pic") pic:String,
        @Field("userid") userid: Int
    ):Call<DefaultResponse>

    @FormUrlEncoded
    @POST("updateprofilebio")
    fun updateprofilebio(
        @Field("bio") pic:String,
        @Field("userid") userid: Int
    ):Call<DefaultResponse>
}




