package com.practice.openinapp.fragments.dashboard.links

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.practice.openinapp.R
import com.practice.openinapp.databinding.LinksCardLayoutBinding
import com.practice.openinapp.model_class.LinkOfAPI
import com.practice.openinapp.utils.CommonObjects

class LinksSectionAdapter(private var linkList: List<LinkOfAPI>, val callback: (text: String) -> Unit):
    RecyclerView.Adapter<LinksSectionAdapter.ViewHolder>() {

    private val emptyState = 0
    private val nonEmptyState = 1
    private lateinit var context :  Context

    fun setLinkList(list: List<LinkOfAPI>){
        linkList = list
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

    override fun getItemViewType(position: Int): Int {
        if(linkList.isEmpty()) return emptyState
        return nonEmptyState
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        return ViewHolder(LayoutInflater.from(parent.context).inflate(
            when(viewType){
                nonEmptyState -> R.layout.links_card_layout
                else -> R.layout.empty_links_layout
            },
            parent,
            false
        ))
    }

    override fun getItemCount(): Int {
        if(linkList.isEmpty()){
            return 1
        }
        return linkList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when(getItemViewType(position)){
            emptyState ->{

            }
            else -> onBindData(holder, position)
        }
    }

    private fun onBindData(holder: RecyclerView.ViewHolder, position: Int){
        try {
            val data = linkList[position]
            val bindingItem: LinksCardLayoutBinding =
                DataBindingUtil.bind(holder.itemView)!!
            Glide.with(context)
                .load(data.originalImage)
                .fitCenter()
                .centerCrop()
                .into(bindingItem.ivLinkVendorImageView)
            bindingItem.tvLinkNameTextView.text = data.title
            bindingItem.tvLinkDateTextView.text = CommonObjects.getDateFormatted(data.createdAt)
            bindingItem.tvClickCountTextView.text = data.totalClicks.toString()
            bindingItem.tvLinkURL.text = data.smartLink
            bindingItem.tvLinkURL.setOnClickListener {
                callback(data.smartLink)
            }
        }catch (e:Exception){
            CommonObjects.errorLogs("$e \n ${e.printStackTrace()}")
        }
    }
}