package com.healyks.app.data.local.periods

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import android.content.Context
import com.healyks.app.data.local.Converters

@Database(entities = [Cycle::class, CyclePrediction::class], version = 2, exportSchema = false)
@TypeConverters(Converters::class) // Add this line
abstract class CycleDatabase : RoomDatabase() {

    abstract fun cycleDao(): CycleDao

    companion object {
        @Volatile
        private var INSTANCE: CycleDatabase? = null

        fun getDatabase(context: Context): CycleDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    CycleDatabase::class.java,
                    "cycle_database"
                )
                    .fallbackToDestructiveMigration()
                    .fallbackToDestructiveMigrationOnDowngrade() // Add this
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}