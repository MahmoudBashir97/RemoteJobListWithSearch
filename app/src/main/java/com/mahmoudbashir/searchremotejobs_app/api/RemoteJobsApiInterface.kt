package com.mahmoudbashir.searchremotejobs_app.api

import com.mahmoudbashir.searchremotejobs_app.models.RemoteJob
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface RemoteJobsApiInterface {

    @GET("api/remote-jobs?limit=200")
    fun getRemoteJobResponse():Call<RemoteJob>


    @GET("api/remote-jobs")
    fun searchRemoteJobResponse(@Query("search") query:String?):Call<RemoteJob>

    
}