package com.mahmoudbashir.searchremotejobs_app.roomdb

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.mahmoudbashir.searchremotejobs_app.models.FavouriteJob

@Database(entities = [FavouriteJob::class],version = 1)
@TypeConverters(Converters::class)
abstract class favJobDatabase :RoomDatabase(){

    abstract fun getDao():FavDao

    companion object{
        @Volatile
        private var instance:favJobDatabase?=null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance?: synchronized(LOCK){
            instance?: createDatabase(context).also { instance = it }
        }

        private fun createDatabase(context: Context) =
                Room.databaseBuilder(
                        context.applicationContext,
                        favJobDatabase::class.java,
                        "fav_job_db"
                ).build()
    }
}