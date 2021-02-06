package com.spinnertech.noteapp.network.api

import com.haroldadmin.cnradapter.NetworkResponse
import com.spinnertech.noteapp.models.AuthRespModel
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface api {

    @FormUrlEncoded
    @POST("login")
    suspend fun  user_login(
        @Field("mail") mail: String ,
        @Field("passwordHash") pass: String
    ): NetworkResponse<AuthRespModel , AuthRespModel>

    @FormUrlEncoded
    @POST("register")
    suspend fun  user_reg(
        @Field("user_name") name: String,
        @Field("mail") mail: String ,
        @Field("passwordHash") pass: String
    ): NetworkResponse<AuthRespModel , AuthRespModel>

    @FormUrlEncoded
    @POST("reset_pass")
    suspend fun  reset_user(
        @Field("mail") mail: String ,
        @Field("new_pass_hash") pass: String
    ): NetworkResponse<AuthRespModel , AuthRespModel>




}