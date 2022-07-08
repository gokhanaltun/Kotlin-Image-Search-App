package com.gkhn.imagesearch.model

data class ImageResult(
    val pageUrl: String,
    val previewURL: String,
    val webformatURL: String,
    val largeImageURL: String,
    val fullHDURL: String,
    val imageURL: String,
    val user: String
)
