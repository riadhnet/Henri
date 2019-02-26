package net.riadh.henri.app

import android.app.Application
import net.riadh.henri.injection.DependencyModules
import org.koin.android.ext.android.startKoin

class MyApp : Application() {


    override fun onCreate() {
        super.onCreate()
        // start Koin context
        startKoin(
            this, listOf(
                DependencyModules.appModules
            )
        )
    }

}