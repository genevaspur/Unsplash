package com.example.unsplash.retrofit

import android.util.Log
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitClient {

    private const val BASE_URL = "https://api.unsplash.com"

    private var instance: Retrofit? = null
    private val gson = GsonBuilder().setLenient().create()

    fun getInstance(url: String = BASE_URL): Retrofit {
        instance = Retrofit.Builder()
                           .baseUrl(url)
                           .addConverterFactory(GsonConverterFactory.create(gson))
                           .build()
        return instance!!
    }

    private class OkHttpClientManager() {


        private var newUrl: String?= null
        private var useLogger: Boolean = false
        private var downloadListener: ((Int) -> Void)? = null

        private val CONNECT_TIMEOUT = 10L
        private val READ_TIMEOUT = 10L
        private val WRITE_TIMEOUT = 10L

        lateinit var okHttpClient: OkHttpClient

        fun getOkHttpClient(useLogger: Boolean, downloadListener: ((Int) -> Void)?): OkHttpClient {
            this.useLogger = useLogger
            this.downloadListener = downloadListener

            okHttpClient =  OkHttpClient.Builder()
                .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
                .apply {

                    if(useLogger) {
                        val logger = HttpLoggingInterceptor()
                        logger.setLevel(HttpLoggingInterceptor.Level.BODY)
                        addInterceptor(logger)
                    }
                }
                .build()

            return  okHttpClient
        }

    }



}