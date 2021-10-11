package com.example.roomdatabaseexample

import androidx.lifecycle.LiveData
import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface SubscriberDAO {
    @Insert
    suspend fun insertSubscriber(subscriber: Subscriber): Long

    @Update
    suspend fun updateSubscriber(subscriber: Subscriber): Int

    @Delete
    suspend fun deleteSubscriber(subscriber: Subscriber): Int

    @Query("DELETE FROM subscriber_table")
    suspend fun deleteAllSubscriber() : Int

    @Query("SELECT * FROM subscriber_table")
    fun selectListOfSubscribers() : LiveData<List<Subscriber>>
}