package com.gkhn.imagesearch.view.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.gkhn.imagesearch.model.ImageResult
import com.gkhn.imagesearch.repository.IImageRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainFragmentViewModel @Inject constructor(private val userRepository: IImageRepository): ViewModel() {

    private val _images = MutableLiveData<PagingData<ImageResult>>()
    val images: LiveData<PagingData<ImageResult>>
        get() = _images
    var lastQuery: String = ""

    fun searchImage(queryString: String = ""){
        val images = userRepository.searchImage(queryString)
        lastQuery = queryString

        viewModelScope.launch {
            images.collect{
                _images.value = it
            }
        }
    }
}