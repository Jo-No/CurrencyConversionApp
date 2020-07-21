package com.jo_no.curencyconversionapp.di

import androidx.lifecycle.ViewModelProvider
import com.jo_no.curencyconversionapp.network.Network
import com.jo_no.curencyconversionapp.network.Service
import com.jo_no.curencyconversionapp.ui.main.MainRepo
import com.jo_no.curencyconversionapp.ui.main.MainRepoImpl
import com.jo_no.curencyconversionapp.ui.main.MainViewModel
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AssemblyModule {
    @Provides
    @Singleton
    fun provideViewModel(): MainViewModel {
        val repo: MainRepo = provideRepo()
        return MainViewModel(provideRepo())
    }

    @Provides
    @Singleton
    fun provideRepo(): MainRepo {
        return MainRepoImpl(provideService())
    }

    @Provides
    @Singleton
    fun provideService(): Service {
        return Network.createService(Service::class.java)
    }
}