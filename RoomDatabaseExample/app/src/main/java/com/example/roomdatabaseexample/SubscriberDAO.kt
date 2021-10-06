package com.example.roomdatabaseexample

import androidx.lifecycle.LiveData
import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface SubscriberDAO {
    @Insert
    suspend fun insertSubscriber(subscriber: Subscriber)

    @Update
    suspend fun updateSubscriber(subscriber: Subscriber)

    @Delete
    suspend fun deleteSubscriber(subscriber: Subscriber)

    @Query("DELETE FROM subscriber_table")
    suspend fun deleteAllSubscriber()

    @Query("SELECT * FROM subscriber_table")
    fun selectListOfSubscribers() : LiveData<List<Subscriber>>
}