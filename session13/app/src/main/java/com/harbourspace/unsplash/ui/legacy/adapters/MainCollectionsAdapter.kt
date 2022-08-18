package com.harbourspace.unsplash.ui.legacy.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.harbourspace.unsplash.R
import com.harbourspace.unsplash.model.UnsplashCollection
import kotlinx.android.synthetic.main.item_main.view.iv_preview
import kotlinx.android.synthetic.main.item_main.view.tv_author
import kotlinx.android.synthetic.main.item_main.view.tv_name

class MainCollectionsAdapter(private var images: List<UnsplashCollection>, val onClick: (String) -> Unit):
    RecyclerView.Adapter<MainCollectionsAdapter.MainCollectionsViewHolder>() {

    class MainCollectionsViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val image = itemView.iv_preview as ImageView
        val name = itemView.tv_name as TextView
        val author = itemView.tv_author as TextView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainCollectionsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return MainCollectionsViewHolder(inflater.inflate(R.layout.item_main, parent, false))
    }

    override fun onBindViewHolder(holder: MainCollectionsViewHolder, position: Int) {
        val image = images[position]
        holder.image.load(image.cover_photo.urls.regular) {
            crossfade(true)
            placeholder(R.drawable.ic_placeholder)
        }
        holder.image.setOnClickListener {
            onClick(image.cover_photo.urls.regular)
        }
        holder.name.text = image.description
        holder.author.text = image.user.name
    }

    fun submitList(newImages: List<UnsplashCollection>) {
        images = newImages
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return images.size
    }
}