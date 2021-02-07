package com.spinnertech.noteapp.repository

import com.haroldadmin.cnradapter.NetworkResponse
import com.spinnertech.noteapp.models.CateogryResp
import com.spinnertech.noteapp.models.CreateResp
import com.spinnertech.noteapp.models.TaskItem
import com.spinnertech.noteapp.models.TaskResp
import com.spinnertech.noteapp.network.MyRetrofitBuilder
import com.spinnertech.noteapp.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class DashboardRepository {


    fun getCategories(): Flow<Resource<CateogryResp>> =
        flow {
            val response = MyRetrofitBuilder.apiService.get_cat_list()

            when (response) {
                is NetworkResponse.Success -> {
                    emit(
                        Resource.success(
                            data = response.body

                        )
                    )
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


    fun getTodayTaskList(user_id: Int, date: String): Flow<Resource<TaskResp>> =
        flow {
            val response = MyRetrofitBuilder.apiService.getListByDate(user_id, date)

            when (response) {
                is NetworkResponse.Success -> {
                    emit(
                        Resource.success(
                            data = response.body,

                        )
                    )


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


    fun getAllList(user_id: Int): Flow<Resource<TaskResp>> =
        flow {
            val response = MyRetrofitBuilder.apiService.getListByUserID(user_id)

            when (response) {
                is NetworkResponse.Success -> {
                    emit(
                        Resource.success(
                            data = response.body,

                            )
                    )


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


    fun createTask(taskItem: TaskItem): Flow<Resource<CreateResp>> =
        flow {
            val response = MyRetrofitBuilder.apiService.add_task(
                taskItem.task_title ,
                taskItem.description ,
                taskItem.start_at,
                taskItem.end_at ,
                taskItem.dated ,
                taskItem.date ,
                taskItem.is_whole_day,
                taskItem.user_id ,
                taskItem.cat_id
            )

            when (response) {
                is NetworkResponse.Success -> {
                    emit(
                        Resource.success(
                            data = response.body,

                            )
                    )


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


    fun deleteTask(teakId: Int): Flow<Resource<CreateResp>> =
        flow {
            val response = MyRetrofitBuilder.apiService.deletePost(
                teakId
            )

            when (response) {
                is NetworkResponse.Success -> {
                    emit(
                        Resource.success(
                            data = response.body,

                            )
                    )


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
