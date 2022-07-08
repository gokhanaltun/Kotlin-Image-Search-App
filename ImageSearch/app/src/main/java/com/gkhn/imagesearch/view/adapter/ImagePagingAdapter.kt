package com.gkhn.imagesearch.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.gkhn.imagesearch.R
import com.gkhn.imagesearch.databinding.DesignImageItemBinding
import com.gkhn.imagesearch.model.ImageResult
import javax.inject.Inject

class ImagePagingAdapter @Inject constructor() :
    PagingDataAdapter<ImageResult, ImagePagingAdapter.ImageViewHolder>(
        IMAGE_COMPARATOR
    ) {

    class ImageViewHolder(private val binding: DesignImageItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(imageResult: ImageResult?) {
            binding.imageResult = imageResult
        }
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val binding = DataBindingUtil.inflate<DesignImageItemBinding>(
            LayoutInflater.from(parent.context),
            R.layout.design_image_item,
            parent,
            false
        )
        return ImageViewHolder(binding)
    }

    companion object {
        private val IMAGE_COMPARATOR = object : DiffUtil.ItemCallback<ImageResult>() {
            override fun areItemsTheSame(oldItem: ImageResult, newItem: ImageResult): Boolean {
                return oldItem.imageURL == newItem.imageURL
            }

            override fun areContentsTheSame(oldItem: ImageResult, newItem: ImageResult): Boolean {
                return oldItem == newItem
            }
        }
    }
}