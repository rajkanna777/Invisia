package com.example.invisia.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.invisia.model.Movie

@Database(entities = [Movie::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
}