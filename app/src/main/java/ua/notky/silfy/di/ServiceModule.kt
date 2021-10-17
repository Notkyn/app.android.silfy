package ua.notky.silfy.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ua.notky.base.validation.ValidationService
import ua.notky.silfy.validation.ValidationServiceImp
import javax.inject.Singleton

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 17.10.2021
 * @email evgeniy.zarechnyi@4k.com.ua
 */

@Module
@InstallIn(SingletonComponent::class)
object ServiceModule {

    @Singleton
    @Provides
    fun provideValidationService(
        @ApplicationContext context: Context
    ): ValidationService {
        return ValidationServiceImp(context)
    }
}