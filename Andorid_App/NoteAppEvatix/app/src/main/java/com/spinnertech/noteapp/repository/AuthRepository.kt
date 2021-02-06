package com.spinnertech.noteapp.repository

import android.util.Log
import com.haroldadmin.cnradapter.NetworkResponse
import com.spinnertech.noteapp.models.AuthRespModel
import com.spinnertech.noteapp.models.UserModel
import com.spinnertech.noteapp.network.MyRetrofitBuilder
import com.spinnertech.noteapp.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class AuthRepository {


    fun loginUser(mail: String, pass: String): Flow<Resource<AuthRespModel>> =
        flow {
        val response = MyRetrofitBuilder.apiService.user_login(mail, pass)

        when (response) {
            is NetworkResponse.Success -> {
                val dataList: List<UserModel> = response.body.data;
                Log.d("TAG", response.body.toString() + " ")
                if (!response.body.error || dataList.isNotEmpty()) {
                    // that means user exist
                    emit(
                        Resource.success(
                            data = response.body
                        )
                    )
                } else {
                    emit(
                        Resource.error(
                            data = null,
                            message = " ${response.body.msg}"
                        )
                    )
                }

            }
            is NetworkResponse.ServerError -> {
                emit(
                    Resource.error(
                        data = null,
                        message = "Error with code : ${response.code}"
                    )
                )
            }

            else -> emit(
                Resource.error(
                    data = null,
                    message = "Error with api "
                )
            )
        }

    }.flowOn(Dispatchers.IO)


    fun regUser(user_name: String, mail: String, pass: String): Flow<Resource<AuthRespModel>> =
        flow {
            val response = MyRetrofitBuilder.apiService.user_reg(user_name, mail, pass)
            Log.d("TAG", "regUser: $response")
            when (response) {
                is NetworkResponse.Success -> {
                    val dataList: List<UserModel> = response.body.data;
                    Log.d("TAG", response.body.toString() + " ")
                    if (!response.body.error || dataList.isNotEmpty()) {
                        // that means user exist
                        emit(
                            Resource.success(
                                data = response.body
                            )
                        )
                    } else {
                        emit(
                            Resource.error(
                                data = null,
                                message = " ${response.body.msg}"
                            )
                        )
                    }

                }
                is NetworkResponse.ServerError -> {
                    emit(
                        Resource.error(
                            data = null,
                            message = "Error with code : ${response.code}"
                        )
                    )
                }

                else -> emit(
                    Resource.error(
                        data = null,
                        message = "Error with api "
                    )
                )
            }

        }.flowOn(Dispatchers.IO)

    fun resetUser(mail: String, pass: String): Flow<Resource<AuthRespModel>> =
        flow {
            val response = MyRetrofitBuilder.apiService.reset_user( mail, pass)
            Log.d("TAG", "regUser: $response")
            when (response) {
                is NetworkResponse.Success -> {
                    val dataList: List<UserModel> = response.body.data;
                    Log.d("TAG", response.body.toString() + " ")
                    if (!response.body.error || dataList.isNotEmpty()) {
                        // that means user exist
                        emit(
                            Resource.success(

                                data = response.body
                            )
                        )
                    } else {
                        emit(
                            Resource.error(
                                data = null,
                                message = " ${response.body.msg}"
                            )
                        )
                    }

                }
                is NetworkResponse.ServerError -> {
                    emit(
                        Resource.error(
                            data = null,
                            message = "Error with code : ${response.code}"
                        )
                    )
                }

                else -> emit(
                    Resource.error(
                        data = null,
                        message = "Error with api "
                    )
                )
            }

        }.flowOn(Dispatchers.IO)

}