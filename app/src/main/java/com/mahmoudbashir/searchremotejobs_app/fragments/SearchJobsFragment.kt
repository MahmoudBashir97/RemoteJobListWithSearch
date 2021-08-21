package com.mahmoudbashir.searchremotejobs_app.fragments

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.widget.addTextChangedListener
import com.mahmoudbashir.searchremotejobs_app.MainActivity
import com.mahmoudbashir.searchremotejobs_app.R
import com.mahmoudbashir.searchremotejobs_app.adapters.RemoteJobAdapter
import com.mahmoudbashir.searchremotejobs_app.databinding.FragmentSearchJobsBinding
import com.mahmoudbashir.searchremotejobs_app.utils.Constants
import com.mahmoudbashir.searchremotejobs_app.viewModel.RemoteJobViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.log


class SearchJobsFragment : Fragment(R.layout.fragment_search_jobs) {

    private var _binding: FragmentSearchJobsBinding?=null
    private val binding get() = _binding!!
    lateinit var viewModel:RemoteJobViewModel
    lateinit var jobAdapter: RemoteJobAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding =  FragmentSearchJobsBinding.inflate( inflater,container, false)

        //searchJobs()


        return binding.root
    }

    private fun searchJobs() {
        var job: Job? = null
        binding.etSearch.addTextChangedListener { editable ->
            job?.cancel()
            job = MainScope().launch {
                delay(500L)
                editable?.let {
                    if (editable.toString().isNotEmpty()) {
                        viewModel.searcResultQurey(editable.toString())
                    }
                }
            }
        }
        setupRecyclerView()
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        doInitialize()
        if (Constants.isNetworkAvailable(requireContext())) {
            searchJobs()
        } else {
            Toast.makeText(activity,"No internet connection", Toast.LENGTH_SHORT).show()
        }

    }

    private fun setupRecyclerView(){
        jobAdapter = RemoteJobAdapter()
        binding.rvSearchJobs.apply {
            setHasFixedSize(true)
            adapter = jobAdapter
        }

        viewModel.searchResultLiveData().observe(viewLifecycleOwner,{remotejob ->

            if (remotejob != null){
                jobAdapter.differ.submitList(remotejob.jobs)
            }
        })
    }

    private fun doInitialize() {
        viewModel = (activity as MainActivity).viewModel
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}