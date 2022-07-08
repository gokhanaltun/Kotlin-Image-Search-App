package com.gkhn.imagesearch.util

import android.widget.ImageView
import androidx.databinding.BindingAdapter

@BindingAdapter("android:urlSrc")
fun urlSrc(view: ImageView, url: String?){
    view.loadImageFromUrl(view, url)
}