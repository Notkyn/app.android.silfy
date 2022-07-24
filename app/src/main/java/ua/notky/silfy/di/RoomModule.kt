package ua.notky.silfy.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ua.notky.silfy.repository.db.AppDataBase
import ua.notky.silfy.repository.db.AppDataBase.Companion.DATABASE_NAME
import javax.inject.Singleton

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 16.07.2022
 * @email evgeniy.zarechnyi@4k.com.ua
 */

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    @Provides
    @Singleton
    fun provideAppDataBase(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(
        context,
        AppDataBase::class.java,
        DATABASE_NAME
    ).build()

    @Provides
    @Singleton
    fun provideProfileDao(appDataBase: AppDataBase) = appDataBase.profileDao()

    @Provides
    @Singleton
    fun provideWordDao(appDataBase: AppDataBase) = appDataBase.wordDao()

    @Provides
    @Singleton
    fun provideWordAllSortDao(appDataBase: AppDataBase) = appDataBase.wordAllSortDao()

    @Provides
    @Singleton
    fun provideWordFavouriteSortDao(appDataBase: AppDataBase) = appDataBase.wordFavouriteSortDao()

    @Provides
    @Singleton
    fun provideWordBlackSortDao(appDataBase: AppDataBase) = appDataBase.wordBlackSortDao()

    @Provides
    @Singleton
    fun provideCategoryDao(appDataBase: AppDataBase) = appDataBase.categoryDao()

    @Provides
    @Singleton
    fun provideCrossWordCategoryDao(appDataBase: AppDataBase) = appDataBase.crossWordCategoryDao()

    @Provides
    @Singleton
    fun provideDictionaryDao(appDataBase: AppDataBase) = appDataBase.dictionaryDao()
}