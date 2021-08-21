package com.mahmoudbashir.searchremotejobs_app.fragments

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.mahmoudbashir.searchremotejobs_app.MainActivity
import com.mahmoudbashir.searchremotejobs_app.R
import com.mahmoudbashir.searchremotejobs_app.adapters.RemoteJobAdapter
import com.mahmoudbashir.searchremotejobs_app.databinding.FragmentRemoteJobsListBinding
import com.mahmoudbashir.searchremotejobs_app.utils.Constants
import com.mahmoudbashir.searchremotejobs_app.viewModel.RemoteJobViewModel

@RequiresApi(Build.VERSION_CODES.M)
class RemoteJobsListFragment : Fragment(R.layout.fragment_remote_jobs_list),
SwipeRefreshLayout.OnRefreshListener{

    private var _binding:FragmentRemoteJobsListBinding?=null
    val binding get() = _binding!!
    private lateinit var viewModel:RemoteJobViewModel
    private lateinit var remoteJobAdapter:RemoteJobAdapter
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       _binding = FragmentRemoteJobsListBinding.inflate(inflater,container,false)

        swipeRefreshLayout = binding.swipeContainer
        swipeRefreshLayout.setOnRefreshListener(this)
        swipeRefreshLayout.setColorSchemeColors(
                Color.GREEN,Color.RED,
                Color.BLUE,Color.DKGRAY
        )

        swipeRefreshLayout.post{
            swipeRefreshLayout.isRefreshing =true
            setupRecyclerView()
        }

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
        if (Constants.isNetworkAvailable(requireContext())){
        viewModel.remoteJobResult().observe(viewLifecycleOwner,{remoteJob ->
            if (remoteJob != null){
                remoteJobAdapter.differ.submitList(remoteJob.jobs)
                swipeRefreshLayout.isRefreshing = false
            }
        })
        }else{
            Toast.makeText(activity, "No internet connection", Toast.LENGTH_SHORT).show()
            swipeRefreshLayout.isRefreshing = false
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onRefresh() {
        setupRecyclerView()
    }

}