package com.example.roomdatabaseexample

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.roomdatabaseexample.databinding.SubscriptionListItemBinding

class MyRecyclerViewAdapter(private val clickListener:(Subscriber)->Unit): RecyclerView.Adapter<MyRecyclerViewAdapter.MyRecyclerViewHolder>() {

    private val subscribersList = ArrayList<Subscriber>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyRecyclerViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: SubscriptionListItemBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.subscription_list_item, parent, false)
        return MyRecyclerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyRecyclerViewHolder, position: Int) {
        holder.bind(subscribersList[position], clickListener)
    }

    override fun getItemCount(): Int {
        return subscribersList.size
    }

    fun setSubscribersList(subscribers: List<Subscriber>) {
        subscribersList.clear()
        subscribersList.addAll(subscribers)
        notifyDataSetChanged()
    }

    class MyRecyclerViewHolder(private val binding: SubscriptionListItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(subscriber: Subscriber, clickListener:(Subscriber)->Unit) {
            binding.apply {
                binding.textViewName.text = subscriber.name
                binding.textViewEmail.text = subscriber.email
                binding.listItemLayout.setOnClickListener {
                    Log.i("MYTAG", "Clicked : ${subscriber.name}")
                    clickListener(subscriber)
                }
            }
        }
    }

}