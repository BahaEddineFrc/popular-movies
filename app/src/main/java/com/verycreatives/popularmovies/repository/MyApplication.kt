package com.verycreatives.popularmovies.repository

import android.app.Application
import com.verycreatives.popularmovies.database.AppDatabase

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        database = AppDatabase.getInstance(applicationContext)

    }

    companion object {

        lateinit var database: AppDatabase
            private set

    }
}