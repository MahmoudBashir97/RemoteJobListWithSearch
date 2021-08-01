package com.mahmoudbashir.searchremotejobs_app.viewModel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mahmoudbashir.searchremotejobs_app.repository.RemoteJobRepository

class ViewModelFactory(
         val app:Application,
    private val remoteRepo:RemoteJobRepository
    ): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return RemoteJobViewModel(app,remoteRepo) as T
    }
}