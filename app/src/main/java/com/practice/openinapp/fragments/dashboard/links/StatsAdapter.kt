package com.practice.openinapp.fragments.dashboard.links

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.practice.openinapp.R
import com.practice.openinapp.databinding.StatsCardLayoutBinding
import com.practice.openinapp.model_class.Stats
import com.practice.openinapp.utils.CommonObjects

class StatsAdapter(private var statList: List<Stats>, val callback: (stat: Stats) -> Unit):
    RecyclerView.Adapter<StatsAdapter.ViewHolder>(){

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(
            R.layout.stats_card_layout,
            parent,
            false
        ))
    }

    override fun getItemCount(): Int {
        return statList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        try {
            val data = statList[position]
            val bindingItem: StatsCardLayoutBinding =
                DataBindingUtil.bind(holder.itemView)!!
            bindingItem.ivStatImageView.setImageResource(data.imageResourceId)
            bindingItem.tvStatNameTextView.text = data.statName
            bindingItem.tvStatValueTextView.text = data.statValue
            bindingItem.root.setOnClickListener {
                callback(data)
            }
        }catch (e: Exception){
            CommonObjects.errorLogs(e.toString())
        }
    }


}