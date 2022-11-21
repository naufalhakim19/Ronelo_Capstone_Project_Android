package com.example.ronelo.consultMenu.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.ronelo.consultMenu.model.ChatModel

@Database(entities = [ChatModel::class], version = 1)
abstract class ChatDatabase : RoomDatabase() {

    abstract fun chatDao() : DAO

    companion object {
        @Volatile
        private var instance: ChatDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: createDatabase(context).also { instance = it }
        }

        @JvmStatic
        private fun createDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                ChatDatabase::class.java,
                "chat_db.db"
            ).fallbackToDestructiveMigration().allowMainThreadQueries().build()
    }

}