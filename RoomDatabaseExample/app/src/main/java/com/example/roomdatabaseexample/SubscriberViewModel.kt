package com.example.roomdatabaseexample
import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class SubscriberViewModel(private val repository: SubscriberRepository) : ViewModel(), Observable {

    private var updateOrDelete = false
    private lateinit var subscriberUpdateOrDelete:Subscriber

    private var statusMessage = MutableLiveData<Event<String>>()
    val message: LiveData<Event<String>>
    get() = statusMessage

    val subscribers = repository.subscribers

    @Bindable
    val inputName = MutableLiveData<String>()
    @Bindable
    val inputEmail = MutableLiveData<String>()
    @Bindable
    val addOrUpdateButton = MutableLiveData<String>()
    @Bindable
    val deleteOrClearAllButton = MutableLiveData<String>()

    init {
        addOrUpdateButton.value = "Add"
        deleteOrClearAllButton.value = "Clear All"
    }

    fun addOrUpdateSubscriber() {
        if(updateOrDelete) {
            subscriberUpdateOrDelete.name = inputName.value!!
            subscriberUpdateOrDelete.email = inputEmail.value!!
            updateSubscriber(subscriberUpdateOrDelete)
            addOrUpdateButton.value = "Add"
            deleteOrClearAllButton.value = "Clear All"
            updateOrDelete = false
        } else {
            val name = inputName.value!!
            val email = inputEmail.value!!
            val subscriber = Subscriber(0, name, email)
            insertSubscriber(subscriber)
        }
        clearTextFields()
    }

    fun deleteOrClearAllSubscriber() {
        if(updateOrDelete) {
            subscriberUpdateOrDelete.name = inputName.value!!
            subscriberUpdateOrDelete.email = inputEmail.value!!
            deleteSubscriber(subscriberUpdateOrDelete)
            updateOrDelete = false
            addOrUpdateButton.value = "Add"
            deleteOrClearAllButton.value = "Clear All"
        } else {
            deleteAllSubscriber()
        }
        clearTextFields()
    }

    private fun clearTextFields() {
        inputName.value = null
        inputEmail.value = null
    }

    fun initUpdateOrDeleteSubscriber(subscriber: Subscriber) {
        inputName.value = subscriber.name
        inputEmail.value = subscriber.email
        updateOrDelete = true
        subscriberUpdateOrDelete = subscriber
        addOrUpdateButton.value = "Update"
        deleteOrClearAllButton.value = "Delete"
    }

    private fun insertSubscriber(subscriber: Subscriber) : Job = viewModelScope.launch {
            val result = repository.insertSubscriber(subscriber)
            if(result > 0)
                statusMessage.value = Event("Subscriber Inserted Successfully @ $result !")
            else
                statusMessage.value = Event("Error occurred !")
    }

    private fun deleteSubscriber(subscriber: Subscriber) : Job = viewModelScope.launch {
            val result = repository.deleteSubscriber(subscriber)
            if(result > 0)
                statusMessage.value = Event("Subscriber Deleted Successfully from @ $result !")
            else
                statusMessage.value = Event("Error occurred !")
    }

    private fun updateSubscriber(subscriber: Subscriber) : Job = viewModelScope.launch {
            val result = repository.updateSubscriber(subscriber)
            if(result > 0)
                statusMessage.value = Event("Subscriber Updated Successfully @ $result !")
            else
                statusMessage.value = Event("Error occurred !")
    }

    private fun deleteAllSubscriber() : Job = viewModelScope.launch {
        val result = repository.deleteAllSubscriber()
        if(result > 0)
            statusMessage.value = Event("All $result Subscribers Deleted Successfully !")
        else
            statusMessage.value = Event("Error occurred !")
    }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }
}
