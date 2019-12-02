package com.verycreatives.popularmovies.database

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.verycreatives.popularmovies.models.Movie


@Database(entities = [Movie::class], version = 1)
@TypeConverters(MovConverters::class)

abstract class AppDatabase : RoomDatabase() {

    abstract fun moviesDao() : MoviesDao

    companion object {

        private const val DATABASE_NAME = "db_movies"

        private var instance: AppDatabase? = null


        private fun buildDatabase(context: Context) : AppDatabase {
            return Room.
                databaseBuilder( context, AppDatabase::class.java, DATABASE_NAME)
                .addCallback(object : Callback() {
                    override fun onOpen(db: SupportSQLiteDatabase) {
                        super.onOpen(db)
                        Log.d("AppDatabase","database opened")
                    }
                })
                .build()

        }

        fun getInstance(context: Context): AppDatabase {
            if (instance == null) {
                synchronized(AppDatabase::class.java) {
                    if (instance == null) {
                        instance = buildDatabase(context.applicationContext)
                    }
                }
            }

            return instance!!
        }

    }
}