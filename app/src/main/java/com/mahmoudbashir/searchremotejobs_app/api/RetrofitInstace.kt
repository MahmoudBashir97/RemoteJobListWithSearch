package com.mahmoudbashir.searchremotejobs_app.api

import com.mahmoudbashir.searchremotejobs_app.utils.Constants
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitInstace {

    companion object{
        private val retrofit by lazy{
            val logging  = HttpLoggingInterceptor()
            logging.setLevel(HttpLoggingInterceptor.Level.BODY)

            val client = OkHttpClient.Builder()
                    .addInterceptor(logging)
                    .build()

            Retrofit.Builder()
                    .baseUrl(Constants.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build()

        }

        val apiService : RemoteJobsApiInterface by lazy {
            retrofit.create(RemoteJobsApiInterface::class.java)
        }
    }
}