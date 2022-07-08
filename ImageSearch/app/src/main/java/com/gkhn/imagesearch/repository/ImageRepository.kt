package com.gkhn.imagesearch.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.gkhn.imagesearch.model.ImagePagingSource
import com.gkhn.imagesearch.model.ImageResult
import com.gkhn.imagesearch.network.ImageService
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ImageRepository @Inject constructor(
    private val imageService: ImageService
) : IImageRepository {

    override fun searchImage(queryString: String) =
        Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
                maxSize = 60,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { ImagePagingSource(imageService, queryString) }
        ).flow

    companion object {
        const val PAGE_SIZE = 20
    }
}