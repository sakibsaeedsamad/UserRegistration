package com.sssakib.userregistration.model

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path


interface API {
    @POST("register")
    fun createUser(@Body user: User?): Call<ResponseModel?>?

    @POST("login")
    fun checkUser(@Body user: User?): Call<User?>?

    @PUT("update/{id}")
    fun updateUser(
        @Body user: User?,
        @Path("id") id: Long
    ): Call<User?>?
}
