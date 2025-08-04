package com.esraeker.inspiredaily.data.remote

import retrofit2.http.GET
import retrofit2.http.Url

interface QuoteApiService {

    @GET
    suspend fun getQuotes(
        @Url url: String = "https://mocki.io/v1/827730cd-c689-40f4-ab0b-0210a36b2e31"
    ): List<QuoteDto>
}
