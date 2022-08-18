package com.harbourspace.unsplash.ui.legacy.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.harbourspace.unsplash.R
import com.harbourspace.unsplash.model.UnsplashItem
import kotlinx.android.synthetic.main.item_main.view.iv_preview
import kotlinx.android.synthetic.main.item_main.view.tv_author
import kotlinx.android.synthetic.main.item_main.view.tv_name

class MainImagesAdapter(private var images: List<UnsplashItem>, val onClick: (String) -> Unit):
    RecyclerView.Adapter<MainImagesAdapter.MainImagesViewHolder>() {

    class MainImagesViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val image = itemView.iv_preview as ImageView
        val name = itemView.tv_name as TextView
        val author = itemView.tv_author as TextView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainImagesViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return MainImagesViewHolder(inflater.inflate(R.layout.item_main, parent, false))
    }

    override fun onBindViewHolder(holder: MainImagesViewHolder, position: Int) {
        val image = images[position]
        holder.image.load(image.urls.regular) {
            crossfade(true)
            placeholder(R.drawable.ic_placeholder)
        }
        holder.image.setOnClickListener {
            onClick(image.urls.regular)
        }
        holder.name.text = image.description
        holder.author.text = image.user.name
    }

    fun submitList(newImages: List<UnsplashItem>) {
        images = newImages
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return images.size
    }
}