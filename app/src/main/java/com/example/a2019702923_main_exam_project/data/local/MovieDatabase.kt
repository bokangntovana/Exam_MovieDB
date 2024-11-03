package com.example.a2019702923_main_exam_project.data.local

import androidx.room.Database
import androidx.room.Entity
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

/*
Bokang Ntovana
Main Exam Project
 */
@Database(
    entities = [MovieEntity::class],
    version = 2 // Increment the version number
)
abstract class MovieDatabase : RoomDatabase() {
    abstract val movieDao: MovieDao

    companion object {
        val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                // Provide the migration strategy here, e.g., adding a new column
                database.execSQL("ALTER TABLE MovieEntity ADD COLUMN isFavorite INTEGER DEFAULT 0 NOT NULL")
            }
        }
    }
}