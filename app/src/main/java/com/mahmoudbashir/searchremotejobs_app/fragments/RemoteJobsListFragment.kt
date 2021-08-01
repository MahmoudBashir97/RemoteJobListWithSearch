package com.mahmoudbashir.searchremotejobs_app.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.mahmoudbashir.searchremotejobs_app.MainActivity
import com.mahmoudbashir.searchremotejobs_app.R
import com.mahmoudbashir.searchremotejobs_app.adapters.RemoteJobAdapter
import com.mahmoudbashir.searchremotejobs_app.databinding.FragmentRemoteJobsListBinding
import com.mahmoudbashir.searchremotejobs_app.viewModel.RemoteJobViewModel

class RemoteJobsListFragment : Fragment(R.layout.fragment_remote_jobs_list) {

    private var _binding:FragmentRemoteJobsListBinding?=null
    val binding get() = _binding!!
    private lateinit var viewModel:RemoteJobViewModel
    private lateinit var remoteJobAdapter:RemoteJobAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       _binding = FragmentRemoteJobsListBinding.inflate(inflater,container,false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = (activity as MainActivity).viewModel

        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        remoteJobAdapter = RemoteJobAdapter()
        binding.rvRemoteJobs.apply {
            layoutManager = LinearLayoutManager(activity)
            setHasFixedSize(true)
            addItemDecoration(object : DividerItemDecoration(activity,LinearLayout.VERTICAL){})

            adapter = remoteJobAdapter
        }
        fetchingData()
    }

    private fun fetchingData() {
        viewModel.remoteJobResult().observe(viewLifecycleOwner,{remoteJob ->
            if (remoteJob != null){
                remoteJobAdapter.differ.submitList(remoteJob.jobs)
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}