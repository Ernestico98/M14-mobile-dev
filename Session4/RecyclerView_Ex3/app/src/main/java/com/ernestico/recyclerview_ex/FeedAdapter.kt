package com.ernestico.recyclerview_ex

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.marginBottom
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_main.view.*

class FeedAdapter constructor (private val cats: List<Int>, val onClick: (Int) -> Unit) : RecyclerView.Adapter<FeedAdapter.MainViewHolder>() {
        class MainViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
                val image = itemView.iv_image_placeholder!!
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                return MainViewHolder(inflater.inflate(R.layout.item_main, parent, false))
        }

        override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
                val feed = cats[position]
                holder.image.setImageResource(feed)
                holder.image.setOnClickListener{
                        onClick(position)
                }
        }

        override fun getItemCount(): Int {
                return cats.size
        }
}