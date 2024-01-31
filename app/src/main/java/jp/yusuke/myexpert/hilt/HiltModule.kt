package jp.yusuke.myexpert.hilt

import jp.yusuke.myexpert.repositories.ChatGPTRepository
import jp.yusuke.myexpert.repositories.ChatRepository
import jp.yusuke.myexpert.repositories.ChatThreadRepository
import jp.yusuke.myexpert.repositories.SharedPreferencesRepository
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