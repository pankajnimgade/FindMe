package com.search.findme.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.findme.businesslogic.model.Item
import com.search.findme.R
import com.squareup.picasso.Picasso

class SearchAdapter(val list: List<Item>, val listener: OnClickListener) :
    RecyclerView.Adapter<SearchAdapter.SearchItemViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_layout, parent, false)
        return SearchItemViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: SearchItemViewHolder, position: Int) {
        holder.onBind(list[position], listener)
    }


    class SearchItemViewHolder(val viewItem: View) : RecyclerView.ViewHolder(viewItem) {

        private val imageView = viewItem.findViewById<ImageView>(R.id.image_view_search_adapter)

        fun onBind(item: Item, listener: OnClickListener) {
            Picasso.with(imageView.context).load(item.media.m).placeholder(R.drawable.welcome_dog)
                .error(R.drawable.error_screen).into(imageView)
            viewItem.rootView.setOnClickListener {
                listener.onClick(item)
            }
        }

    }

    interface OnClickListener {
        fun onClick(item: Item)
    }
}