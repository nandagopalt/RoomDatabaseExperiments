package com.example.roomdatabaseexample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.roomdatabaseexample.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var subscriberViewModel: SubscriberViewModel
    private lateinit var subscriberRecyclerAdapter: MyRecyclerViewAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        val subscriberDAO = SubscriberDataBase.getInstance(this).subscriberDAO
        val subscriberRepository = SubscriberRepository(subscriberDAO)
        val subscriberViewModelFactory = SubscriberViewModelFactory(subscriberRepository)
        subscriberViewModel = ViewModelProvider(this, subscriberViewModelFactory).get(SubscriberViewModel::class.java)
        binding.myViewModel = subscriberViewModel
        binding.lifecycleOwner = this
        initRecyclerView()

        subscriberViewModel.message.observe(this, Observer { it ->
            it.getContentIfNotHandled()?.let {
                Toast.makeText(this, it, Toast.LENGTH_LONG).show()
            }

        })
    }

    private fun initRecyclerView() {
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        subscriberRecyclerAdapter = MyRecyclerViewAdapter({selectedSubscriber: Subscriber -> selectedSubscriber(selectedSubscriber)})
        binding.recyclerView.adapter = subscriberRecyclerAdapter
        subscriberList()
    }

    private fun subscriberList() {
        subscriberViewModel.subscribers.observe(this, Observer {
            Log.i("MYTAG", it.toString())
            //binding.recyclerView.adapter = MyRecyclerViewAdapter(it)
            subscriberRecyclerAdapter.setSubscribersList(it)
        })
    }

    private fun selectedSubscriber(subscriber: Subscriber) {
        Log.i("MYTAG", "Clicked : ${subscriber.email}")
        //Toast.makeText(this, "Selected Subscriber is ${subscriber.name}", Toast.LENGTH_LONG).show()
        subscriberViewModel.initUpdateOrDeleteSubscriber(subscriber)
    }
}