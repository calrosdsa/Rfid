package com.teclu.mobility2.inject

import com.teclu.mobility2.util.impl.AppPreferencesImpl
import com.teclu.mobility2.util.interfaces.AppUtil
import com.teclu.mobility2.util.impl.AppUtilImpl
import com.teclu.mobility2.util.interfaces.AppPreferences
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {


    @Binds
    @Singleton
    abstract fun bindAppUtil(utilImpl: AppUtilImpl): AppUtil

    @Binds
    @Singleton
    abstract fun bindAppPreferences(appPreferencesImpl: AppPreferencesImpl):AppPreferences
}