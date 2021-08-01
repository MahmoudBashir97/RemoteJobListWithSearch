package com.mahmoudbashir.searchremotejobs_app.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mahmoudbashir.searchremotejobs_app.api.RetrofitInstace
import com.mahmoudbashir.searchremotejobs_app.models.FavouriteJob
import com.mahmoudbashir.searchremotejobs_app.models.RemoteJob
import com.mahmoudbashir.searchremotejobs_app.roomdb.favJobDatabase
import com.mahmoudbashir.searchremotejobs_app.utils.Constants
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class RemoteJobRepository(val db:favJobDatabase) {

    private val remoteJobService = RetrofitInstace.apiService
    private val remoteJobResponseLiveData : MutableLiveData<RemoteJob> = MutableLiveData()

    init {
        getRemoteJobResponse()
    }
    private fun getRemoteJobResponse(){
        remoteJobService.getRemoteJobResponse()
                .enqueue(
                        object :Callback<RemoteJob>{
                            override fun onResponse(call: Call<RemoteJob>, response: Response<RemoteJob>) {
                                remoteJobResponseLiveData.postValue(response.body())
                            }

                            override fun onFailure(call: Call<RemoteJob>, t: Throwable) {
                               remoteJobResponseLiveData.postValue(null)
                                Log.e(Constants.TAG,"${t.message}")
                            }
                        }
                )
    }

    fun remoteJobResult():LiveData<RemoteJob>{
        return remoteJobResponseLiveData
    }

    suspend fun addFavJob(job: FavouriteJob) = db.getDao().addFavouriteJob(job)

    suspend fun deleteFavJob(job: FavouriteJob) = db.getDao().deleteFaveJob(job)

    fun getAllFavJobs() = db.getDao().getAllFav()
}