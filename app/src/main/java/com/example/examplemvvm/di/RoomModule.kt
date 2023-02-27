package com.example.examplemvvm.di

import android.content.Context
import androidx.room.Room
import com.example.examplemvvm.data.database.QuoteDatabase
import com.example.examplemvvm.data.database.dao.QuoteDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    private const val QUOTE_DATABASE_NAME = "quote_database"

    @Singleton
    @Provides
    fun provideRoom(@ApplicationContext context: Context) =
        Room.databaseBuilder(context, QuoteDatabase::class.java, QUOTE_DATABASE_NAME).build()


    @Singleton
    @Provides
    fun provideQuoteDao(db: QuoteDatabase): QuoteDao = db.getQuoteDao()
}