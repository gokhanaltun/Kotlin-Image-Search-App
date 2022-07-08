package com.gkhn.imagesearch.model

import com.google.gson.annotations.SerializedName

data class ImageResponse(
    @SerializedName("hits")
    val images: List<ImageResult>
)