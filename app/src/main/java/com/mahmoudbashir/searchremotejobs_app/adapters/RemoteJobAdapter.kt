package com.mahmoudbashir.searchremotejobs_app.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mahmoudbashir.searchremotejobs_app.databinding.SingleItemJobBinding
import com.mahmoudbashir.searchremotejobs_app.fragments.MainFragmentDirections
import com.mahmoudbashir.searchremotejobs_app.models.Job

class RemoteJobAdapter() : RecyclerView.Adapter<RemoteJobAdapter.ViewHolder>() {
    private var binding:SingleItemJobBinding?=null
    inner class ViewHolder(itemBinding: SingleItemJobBinding) :
            RecyclerView.ViewHolder(itemBinding.root)

    private val differCallback = object :DiffUtil.ItemCallback<Job>(){
        override fun areItemsTheSame(oldItem: Job, newItem: Job): Boolean {
           return oldItem.url == newItem.url
        }

        override fun areContentsTheSame(oldItem: Job, newItem: Job): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this,differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = SingleItemJobBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,false
        )
        return ViewHolder(binding!!)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentJob = differ.currentList[position]
        holder.itemView.apply {
            Glide.with(this)
                    .load(currentJob.companyLogoUrl)
                    .into(binding?.ivCompanyLogo!!)

            binding?.tvCompanyName?.text = currentJob.companyName
            binding?.tvJobLocation?.text = currentJob.candidateRequiredLocation
            binding?.tvJobTitle?.text = currentJob.title
            binding?.tvJobType?.text = currentJob.jobType
            val dateJob = currentJob.publicationDate?.split("T")
            binding?.tvDate?.text = dateJob?.get(0)
        }.setOnClickListener { mView ->
            mView.findNavController().navigate(MainFragmentDirections.actionMainFragmentToJobDetailsFragment(currentJob))
        }
    }

    override fun getItemCount(): Int = differ.currentList.size
}