package com.brainer.mvvmcoroutines

import com.brainer.mvvmcoroutines.model.User
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class NetworkLayer() {
    private var retrofit: Retrofit? = null
    private val IS_DEBUGGABLE = true
    private val BASE_URL = "https://gorest.co.in/"
    // Function that makes the network request, blocking the current thread

    fun getClient(): Retrofit? {

        val interceptor = Interceptor { chain ->
            val newRequest: Request =
                chain.request().newBuilder().addHeader("Content-Type", "application/json")
                    .build()

            chain.proceed(newRequest)
        }

        val builder = OkHttpClient.Builder()
        builder.interceptors().add(interceptor)
        if (IS_DEBUGGABLE) {
            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
            builder.addInterceptor(loggingInterceptor)
        }
        val client = builder.build()

        retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
            .client(client)
            .build()

        return retrofit
    }

    fun getClientWithHeader(headerToken: String): Retrofit? {

        val interceptor = object : Interceptor {
            override fun intercept(chain: Interceptor.Chain): Response {
                val newRequest: Request =
                    chain.request().newBuilder().addHeader("Content-Type", "application/json")
                        .addHeader("Authorization", headerToken)
                        .build()

                return chain.proceed(newRequest)
            }
        }

        val builder = OkHttpClient.Builder()
        builder.interceptors().add(interceptor)
        if (IS_DEBUGGABLE) {
            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
            builder.addInterceptor(loggingInterceptor)
        }
        val client = builder.build()

        retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
            .client(client)
            .build()

        return retrofit
    }
}
