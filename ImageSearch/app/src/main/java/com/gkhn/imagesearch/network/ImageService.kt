package com.gkhn.imagesearch.network

import com.gkhn.imagesearch.model.ImageResponse
import com.gkhn.imagesearch.util.ApiConstants.API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ImageService {

    @GET("api/")
    suspend fun searchPhotos(
        @Query("q") queryString: String = "",
        @Query("page") page: Int,
        @Query("per_page") per_page: Int,
        @Query("key") key: String = API_KEY
    ): Response<ImageResponse>
}