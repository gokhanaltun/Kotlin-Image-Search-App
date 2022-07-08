package com.gkhn.imagesearch.model

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.gkhn.imagesearch.network.ImageService

class ImagePagingSource(
    private val imageService: ImageService,
    private val queryString: String
) : PagingSource<Int, ImageResult>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ImageResult> {
        val page = params.key ?: STARTING_PAGE_INDEX
        return try {
            val response = imageService.searchPhotos(queryString, page, params.loadSize)
            val photos = response.body()!!.images

            LoadResult.Page(
                data = photos,
                prevKey = if (page == STARTING_PAGE_INDEX) null else page.minus(1),
                nextKey = if (photos.isEmpty()) null else page.plus(1)
            )
        }catch (e: Exception){
            LoadResult.Error(e)
        }

    }

    override fun getRefreshKey(state: PagingState<Int, ImageResult>): Int? {
        TODO("Not yet implemented")
    }

    companion object {
        private const val STARTING_PAGE_INDEX = 1
    }
}