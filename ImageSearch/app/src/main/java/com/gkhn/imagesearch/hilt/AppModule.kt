package com.gkhn.imagesearch.hilt

import com.gkhn.imagesearch.network.ImageService
import com.gkhn.imagesearch.repository.IImageRepository
import com.gkhn.imagesearch.repository.ImageRepository
import com.gkhn.imagesearch.util.ApiConstants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun provideImageService(retrofit: Retrofit): ImageService =
        retrofit.create(ImageService::class.java)

    @Singleton
    @Provides
    fun provideImageRepository(imageService: ImageService): IImageRepository =
        ImageRepository(imageService)
}