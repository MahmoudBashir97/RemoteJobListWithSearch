package com.mahmoudbashir.searchremotejobs_app.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.navigation.fragment.navArgs
import com.mahmoudbashir.searchremotejobs_app.R
import com.mahmoudbashir.searchremotejobs_app.databinding.FragmentJobDetailsBinding
import com.mahmoudbashir.searchremotejobs_app.models.Job


class jobDetailsFragment : Fragment(R.layout.fragment_job_details) {

    private var _binding: FragmentJobDetailsBinding? = null
    private val binding get() = _binding!!

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        currentJob = args.job!!
        setupWebView()
    }

    private fun setupWebView() {
        binding?.webView.apply {
            this.webViewClient = WebViewClient()
            currentJob.url?.let{loadUrl(it)}
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}