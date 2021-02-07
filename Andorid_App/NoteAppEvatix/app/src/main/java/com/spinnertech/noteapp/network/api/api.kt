package com.spinnertech.noteapp.network.api

import com.haroldadmin.cnradapter.NetworkResponse
import com.spinnertech.noteapp.models.AuthRespModel
import com.spinnertech.noteapp.models.CateogryResp
import com.spinnertech.noteapp.models.CreateResp
import com.spinnertech.noteapp.models.TaskResp
import retrofit2.http.*

interface api {

    @FormUrlEncoded
    @POST("login")
    suspend fun user_login(
        @Field("mail") mail: String,
        @Field("passwordHash") pass: String
    ): NetworkResponse<AuthRespModel, AuthRespModel>

    @FormUrlEncoded
    @POST("register")
    suspend fun user_reg(
        @Field("user_name") name: String,
        @Field("mail") mail: String,
        @Field("passwordHash") pass: String
    ): NetworkResponse<AuthRespModel, AuthRespModel>


    @FormUrlEncoded
    @POST("reset_pass")
    suspend fun reset_user(
        @Field("mail") mail: String,
        @Field("new_pass_hash") pass: String
    ): NetworkResponse<AuthRespModel, AuthRespModel>
    /*
    title:test t
desc:test lorem ipsum
start:2-05-2020
end:0
dated:0
is_whole_day:0
user_id:1
     */

    @FormUrlEncoded
    @POST("add_task")
    suspend fun add_task(
        @Field("title") title: String,
        @Field("desc") desc: String,
        @Field("start") start: String,
        @Field("end") pass: String,
        @Field("dated") dated: Int,
        @Field("date") date: String,
        @Field("is_whole_day") is_whole_day: Int,
        @Field("user_id") user_id: Int,
        @Field("cat_id") cat_id: Int,

        ): NetworkResponse<CreateResp, CreateResp>


    @GET("category_count")
    suspend fun get_cat_list()
        : NetworkResponse<CateogryResp, CateogryResp>

    @GET("tasks/by_date/{user_id}/{date}")
    suspend fun getListByDate(
        @Path("user_id") id: Int,
        @Path("date") date: String
    )
        : NetworkResponse<TaskResp, TaskResp>


    @GET("delete_task/{post_id}}")
    suspend fun deletePost(@Path("post_id") id: Int)
        : NetworkResponse<CreateResp, CreateResp>

    @GET("tasks/{user_id}}")
    suspend fun getListByUserID(@Path("user_id") id: Int)
        : NetworkResponse<TaskResp, TaskResp>


}
