package org.tech.ebookcmp
import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.tech.ebookcmp.di.initKoin

class BookApplication : Application(){
    override fun onCreate() {
        super.onCreate()
        initKoin {
            androidContext(this@BookApplication)
        }
    }
}