package com.gkhn.imagesearch.util

import android.widget.ImageView
import com.squareup.picasso.Picasso

fun ImageView.loadImageFromUrl(view: ImageView, url: String?){
    Picasso.get().load(url).into(view)
}