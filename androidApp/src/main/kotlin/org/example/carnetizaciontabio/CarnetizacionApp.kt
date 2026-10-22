package org.example.carnetizaciontabio

import android.app.Application
import android.os.Build
import androidx.annotation.RequiresApi
import org.example.carnetizaciontabio.di.initKoin

class CarnetizacionApp : Application() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate() {
        super.onCreate()
        initKoin()

    }
}