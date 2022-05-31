package com.brainer.mvvmcoroutines.apiutils

import com.brainer.mvvmcoroutines.model.User
import retrofit2.Call
import retrofit2.http.GET

interface API {

    @GET("public/v2/posts")
    fun userApiCall(): Call<User>
}