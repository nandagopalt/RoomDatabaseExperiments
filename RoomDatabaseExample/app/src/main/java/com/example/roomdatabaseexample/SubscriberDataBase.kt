package com.example.roomdatabaseexample

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Subscriber::class], version = 1, exportSchema = false)
abstract class SubscriberDataBase: RoomDatabase() {
    abstract val subscriberDAO: SubscriberDAO
    companion object {
        @Volatile // Makes the field immediately available to other threads
        private var INSTANCE: SubscriberDataBase? = null
        fun getInstance(context: Context): SubscriberDataBase {
            synchronized(this) {
                var instance = INSTANCE
                if(instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        SubscriberDataBase::class.java,
                        "subscriber_database")
                    .build()
                }
                return instance
            }
        }
    }
}