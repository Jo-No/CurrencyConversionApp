package com.jo_no.curencyconversionapp.di

import com.jo_no.curencyconversionapp.ui.main.MainFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AssemblyModule::class])
interface AppComponent {
    fun injectIntoFragment(frag: MainFragment)
}