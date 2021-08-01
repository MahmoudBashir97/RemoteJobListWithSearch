package com.mahmoudbashir.searchremotejobs_app.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.mahmoudbashir.searchremotejobs_app.models.FavouriteJob
import com.mahmoudbashir.searchremotejobs_app.models.RemoteJob
import com.mahmoudbashir.searchremotejobs_app.repository.RemoteJobRepository
import kotlinx.coroutines.launch

class RemoteJobViewModel(
         app:Application,
        private val remoteRepository:RemoteJobRepository
):AndroidViewModel(app) {
    fun remoteJobResult():LiveData<RemoteJob> =remoteRepository.remoteJobResult()

    fun addFavJob(job: FavouriteJob) = viewModelScope.launch {
        remoteRepository.addFavJob(job)
    }

    fun deleteFavJob(job: FavouriteJob) = viewModelScope.launch {
        remoteRepository.deleteFavJob(job)
    }

    fun getAllFavJobs() =remoteRepository.getAllFavJobs()
}