package com.ndronina.sample.tradernet.di

import com.ndronina.sample.tradernet.data.repository.TickersRepositoryImpl
import com.ndronina.sample.tradernet.domain.usecase.GetSampleTickersUseCase
import com.ndronina.sample.tradernet.domain.TickersRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {

    @Binds
    @Singleton
    abstract fun bindTickersRepository(tickersRepositoryImpl: TickersRepositoryImpl): TickersRepository

    companion object {

        @Provides
        @Singleton
        fun provideOkHttpClient(): OkHttpClient {
            return OkHttpClient.Builder()
                .readTimeout(0, TimeUnit.MILLISECONDS)
                .build()
        }

        @Provides
        @Singleton
        fun provideGetSampleTickersUseCase(repository: TickersRepository): GetSampleTickersUseCase {
            return GetSampleTickersUseCase(repository)
        }
    }
}