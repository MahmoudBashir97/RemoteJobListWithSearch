package com.mahmoudbashir.searchremotejobs_app.models

data class JobToSave(

        val id: Int,
        val candidateRequiredLocation: String?,
        val category: String?,
        val companyLogoUrl: String?,
        val companyName: String?,
        val description: String?,
        val jobId: Int?,
        val jobType: String?,
        val publicationDate: String?,
        val salary: String?,
        val title: String?,
        val url: String?
)
