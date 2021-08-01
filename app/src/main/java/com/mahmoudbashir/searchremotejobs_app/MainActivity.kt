package com.mahmoudbashir.searchremotejobs_app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import androidx.lifecycle.ViewModelProvider
import com.mahmoudbashir.searchremotejobs_app.databinding.ActivityMainBinding
import com.mahmoudbashir.searchremotejobs_app.models.RemoteJob
import com.mahmoudbashir.searchremotejobs_app.repository.RemoteJobRepository
import com.mahmoudbashir.searchremotejobs_app.roomdb.favJobDatabase
import com.mahmoudbashir.searchremotejobs_app.viewModel.RemoteJobViewModel
import com.mahmoudbashir.searchremotejobs_app.viewModel.ViewModelFactory

class MainActivity : AppCompatActivity() {
    lateinit var binding : ActivityMainBinding
    lateinit var viewModel:RemoteJobViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_main)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.title = ""

        setupViewModel()
    }

    private fun setupViewModel() {
        val repos = RemoteJobRepository(favJobDatabase.invoke(this))
        val viewModelFactory = ViewModelFactory(application,repos)
        viewModel = ViewModelProvider(this,viewModelFactory).get(RemoteJobViewModel::class.java)
    }
}