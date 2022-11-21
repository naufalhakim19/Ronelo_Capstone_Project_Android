package com.example.ronelo.consultMenu.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.ronelo.consultMenu.model.ChatModel

@Dao
interface DAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(chat: ChatModel): Long

    @Query("SELECT * FROM tb_chat")
    fun getListChat(): LiveData<List<ChatModel>>

    @Query("SELECT id_chat from tb_chat WHERE id_chat == :id")
    fun getChatId(id: Int): Int

    @Query("DELETE FROM tb_chat")
    suspend fun nukeChatList()

}