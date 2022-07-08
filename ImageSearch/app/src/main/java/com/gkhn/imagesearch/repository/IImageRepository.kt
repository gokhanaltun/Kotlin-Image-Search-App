package com.gkhn.imagesearch.repository

import androidx.paging.PagingData
import com.gkhn.imagesearch.model.ImageResult
import kotlinx.coroutines.flow.Flow

interface IImageRepository {

    fun searchImage(queryString: String = ""): Flow<PagingData<ImageResult>>
}