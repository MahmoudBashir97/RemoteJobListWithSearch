package com.mahmoudbashir.searchremotejobs_app.api

import com.mahmoudbashir.searchremotejobs_app.models.RemoteJob
import retrofit2.Call
import retrofit2.http.GET

interface RemoteJobsApiInterface {

    @GET("api/remote-jobs")
    fun getRemoteJobResponse():Call<RemoteJob>


    
}