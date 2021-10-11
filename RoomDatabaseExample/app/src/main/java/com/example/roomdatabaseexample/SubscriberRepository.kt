package com.example.roomdatabaseexample

class SubscriberRepository(private val subscriberDAO:SubscriberDAO) {
    val subscribers = subscriberDAO.selectListOfSubscribers()

    /**
     *
     */
    suspend fun insertSubscriber(subscriber: Subscriber):Long {
         return subscriberDAO.insertSubscriber(subscriber)
    }

    suspend fun deleteSubscriber(subscriber: Subscriber): Int {
        return subscriberDAO.deleteSubscriber(subscriber)
    }

    suspend fun updateSubscriber(subscriber: Subscriber): Int {
        return subscriberDAO.updateSubscriber(subscriber)
    }

    suspend fun deleteAllSubscriber(): Int {
        return subscriberDAO.deleteAllSubscriber()
    }
}