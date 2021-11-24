package com.oskr19.breakingbad.di

import android.app.Application
import androidx.room.Room
import com.oskr19.breakingbad.core.Constants.DATABASE_NAME
import com.oskr19.breakingbad.core.data.LocalDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideDatabase(application: Application): LocalDatabase {
        return Room.databaseBuilder(
            application,
            LocalDatabase::class.java,
            DATABASE_NAME
        ).build()
    }

}