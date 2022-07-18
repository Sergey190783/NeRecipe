package ru.netology.nerecipe.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import ru.netology.nerecipe.dao.RecipeEntity
import ru.netology.nerecipe.dao.StepEntity
import ru.netology.nerecipe.data.dao.*

@Database(
    entities = [CategoryEntity::class, RecipeEntity::class, StepEntity::class],
    version = 1
)
abstract class AppDb : RoomDatabase() {
    abstract val categoryDao: CategoryDao
    abstract val recipeDao: RecipeDao
    abstract val stepDao: StepDao

    companion object {
        @Volatile
        private var instance: AppDb? = null

        fun getInstance(context: Context): AppDb {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): AppDb {
            return Room.databaseBuilder(context, AppDb::class.java, "app.db")
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build()
        }
    }

}