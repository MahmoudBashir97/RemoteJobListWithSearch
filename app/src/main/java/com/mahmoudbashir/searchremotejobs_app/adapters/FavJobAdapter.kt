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
import com.mahmoudbashir.searchremotejobs_app.models.FavouriteJob
import com.mahmoudbashir.searchremotejobs_app.models.Job

class FavJobAdapter constructor(
        private val itemClick:OnItemClickListenerInterface
) : RecyclerView.Adapter<FavJobAdapter.ViewHolder>() {
    private var binding:SingleItemJobBinding?=null
    inner class ViewHolder(itemBinding: SingleItemJobBinding) :
            RecyclerView.ViewHolder(itemBinding.root)

    private val differCallback = object :DiffUtil.ItemCallback<FavouriteJob>(){
        override fun areItemsTheSame(oldItem: FavouriteJob, newItem: FavouriteJob): Boolean {
           return oldItem.url == newItem.url
        }

        override fun areContentsTheSame(oldItem: FavouriteJob, newItem: FavouriteJob): Boolean {
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
            binding?.ibDelete?.visibility = View.VISIBLE
        }.setOnClickListener { mView ->
            val tags = arrayListOf<String>()
            val job = Job(
                    currentJob.candidateRequiredLocation,
                    currentJob.category,
                    currentJob.companyLogoUrl,
                    currentJob.companyName,
                    currentJob.description,
                    currentJob.id,
                    currentJob.jobType,
                    currentJob.publicationDate,
                    currentJob.salary,
                    tags,
                    currentJob.title,
                    currentJob.url
            )
            mView.findNavController().navigate(MainFragmentDirections.actionMainFragmentToJobDetailsFragment(job))
        }
        holder.itemView.apply{
            binding?.ibDelete?.setOnClickListener {
                itemClick.onItemClick(currentJob,binding?.ibDelete!!,position)
            }
        }
    }

    override fun getItemCount(): Int = differ.currentList.size

    interface OnItemClickListenerInterface{
        fun onItemClick(
                job: FavouriteJob,
                view:View,
                position: Int
        )
    }
}