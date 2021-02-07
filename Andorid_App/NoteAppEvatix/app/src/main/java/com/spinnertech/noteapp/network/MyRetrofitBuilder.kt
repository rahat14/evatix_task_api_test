package com.spinnertech.noteapp.network

import com.haroldadmin.cnradapter.NetworkResponseAdapterFactory
import com.spinnertech.noteapp.network.api.api
import com.spinnertech.noteapp.utils.Const
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object MyRetrofitBuilder {

    private val retrofitBuilder: Retrofit.Builder by lazy {

        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        val client: OkHttpClient = OkHttpClient.Builder().addInterceptor(interceptor).build()

        Retrofit.Builder()
            .baseUrl(Const.Base_Url)
            .addCallAdapterFactory(NetworkResponseAdapterFactory())
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())

    }

    val apiService: api by lazy {
        retrofitBuilder
            .build()
            .create(api::class.java)

    }

}
