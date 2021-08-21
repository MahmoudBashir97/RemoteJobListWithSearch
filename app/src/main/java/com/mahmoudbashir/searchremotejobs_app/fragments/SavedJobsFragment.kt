package com.mahmoudbashir.searchremotejobs_app.fragments

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.mahmoudbashir.searchremotejobs_app.MainActivity
import com.mahmoudbashir.searchremotejobs_app.R
import com.mahmoudbashir.searchremotejobs_app.adapters.FavJobAdapter
import com.mahmoudbashir.searchremotejobs_app.databinding.FragmentSavedJobsBinding
import com.mahmoudbashir.searchremotejobs_app.models.FavouriteJob
import com.mahmoudbashir.searchremotejobs_app.viewModel.RemoteJobViewModel


class SavedJobsFragment : Fragment(R.layout.fragment_saved_jobs),
FavJobAdapter.OnItemClickListenerInterface{

    private var _binding:FragmentSavedJobsBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel:RemoteJobViewModel
    private lateinit var favAdapter:FavJobAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSavedJobsBinding.inflate(
                inflater,container,false
        )


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = (activity as MainActivity).viewModel
        setUpRecyclerView()
    }

    private fun setUpRecyclerView() {
        favAdapter = FavJobAdapter(this)

        binding.rvJobsSaved.apply {
            layoutManager = LinearLayoutManager(activity)
            setHasFixedSize(true)
            addItemDecoration(object : DividerItemDecoration(
                    activity,LinearLayout.VERTICAL
            ){})
            adapter = favAdapter
        }
        viewModel.getAllFavJobs().observe(viewLifecycleOwner,{favJob->
            favAdapter.differ.submitList(favJob)
            updateUI(favJob)
        })
    }

    private fun updateUI(favJob: List<FavouriteJob>?) {

        if (favJob!!.isNotEmpty()){
            binding.rvJobsSaved.visibility = View.VISIBLE
            binding.cardNoAvailable.visibility = View.GONE
        }else{
            binding.rvJobsSaved.visibility = View.GONE
            binding.cardNoAvailable.visibility = View.VISIBLE
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onItemClick(job: FavouriteJob, view: View, position: Int) {
        deleteJob(job)
    }

    private fun deleteJob(job: FavouriteJob) {
        AlertDialog.Builder(activity).apply {
            setTitle("Delete job")
            setMessage("Are you sure u want to permanently delete this job")
            setPositiveButton("DELETE"){_,_->
                viewModel.deleteFavJob(job)
                Toast.makeText(activity,
                "job deleted",Toast.LENGTH_SHORT).show()
            }
            setNegativeButton("CANCEL",null)
        }.create().show()
    }


}