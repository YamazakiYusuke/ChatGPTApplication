package com.example.myexpert.hilt

import com.example.myexpert.repositories.ChatGPTRepository
import com.example.myexpert.repositories.ChatRepository
import com.example.myexpert.repositories.ChatThreadRepository
import com.example.myexpert.repositories.SharedPreferencesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object HiltModule {
    @Provides
    @Singleton
    fun provideChatGPTRepository(): ChatGPTRepository {
        return ChatGPTRepository()
    }

    @Provides
    @Singleton
    fun provideChatRepository(): ChatRepository {
        return ChatRepository()
    }

    @Provides
    @Singleton
    fun provideChatThreadRepository(): ChatThreadRepository {
        return ChatThreadRepository()
    }

    @Provides
    @Singleton
    fun provideSharedPreferencesRepository(): SharedPreferencesRepository {
        return SharedPreferencesRepository()
    }
}