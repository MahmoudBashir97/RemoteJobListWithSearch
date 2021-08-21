package com.mahmoudbashir.searchremotejobs_app.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebSettings
import android.webkit.WebViewClient
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import com.mahmoudbashir.searchremotejobs_app.MainActivity
import com.mahmoudbashir.searchremotejobs_app.R
import com.mahmoudbashir.searchremotejobs_app.databinding.FragmentJobDetailsBinding
import com.mahmoudbashir.searchremotejobs_app.models.FavouriteJob
import com.mahmoudbashir.searchremotejobs_app.models.Job
import com.mahmoudbashir.searchremotejobs_app.viewModel.RemoteJobViewModel


class jobDetailsFragment : Fragment(R.layout.fragment_job_details) {

    private var _binding: FragmentJobDetailsBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel:RemoteJobViewModel
    private  lateinit var currentJob:Job
    val args:jobDetailsFragmentArgs by navArgs()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentJobDetailsBinding.inflate(
                inflater,container,false
        )



        return binding.root

    }

    private fun addFavJob(view: View?) {
        val favJob = FavouriteJob(
                0,
                currentJob.candidateRequiredLocation,
                currentJob.category,
                currentJob.companyLogoUrl,
                currentJob.companyName,
                currentJob.description,
                currentJob.id,
                currentJob.jobType,
                currentJob.publicationDate,
                currentJob.salary,
                currentJob.tags,
                currentJob.title,
                currentJob.url
        )
        viewModel.addFavJob(favJob)
        if (view != null) {
            Snackbar.make(view,"Job Saved Successfully",Snackbar.LENGTH_LONG).show()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as MainActivity).viewModel
        currentJob = args.job!!
        setupWebView()

        binding.fabAddFavorite.setOnClickListener{
            addFavJob(it)
        }

    }

    private fun setupWebView() {
        binding.webView.apply {
            this.webViewClient = WebViewClient()
            currentJob.url?.let{loadUrl(it)}
        }
        val settings = binding.webView.settings
        settings.javaScriptEnabled = true
        settings.setAppCacheEnabled(true)
        settings.cacheMode = WebSettings.LOAD_DEFAULT
        settings.setSupportZoom(false)
        settings.builtInZoomControls = false
        settings.displayZoomControls = false
        settings.textZoom = 100
        settings.blockNetworkImage = false
        settings.loadsImagesAutomatically = true
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}