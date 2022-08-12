package ua.notky.silfy.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ua.notky.silfy.config.ResourceProvider
import javax.inject.Singleton

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 12.08.2022
 * @email evgeniy.zarechnyi@4k.com.ua
 */

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun bindResourceProvider(@ApplicationContext context: Context): ResourceProvider {
        return ResourceProvider(
            context
        )
    }
}