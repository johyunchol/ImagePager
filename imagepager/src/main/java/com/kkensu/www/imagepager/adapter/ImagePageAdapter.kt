package com.kkensu.www.imagepager.adapter

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.github.chrisbanes.photoview.PhotoView
import com.kkensu.www.imagepager.R
import com.kkensu.www.imagepager.interfaces.OnSelectedImageCallback
import com.kkensu.www.imagepager.model.ImageData

class ImagePageAdapter(
        private val context: Context,
        private val imageList: MutableList<ImageData>,
        private val onSelectedImageCallback: OnSelectedImageCallback,
        private val onLongClickListener: View.OnLongClickListener)
    : RecyclerView.Adapter<PagerViewHolder>() {
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagerViewHolder =
            PagerViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.activity_imageview, parent, false))

    override fun onBindViewHolder(holder: PagerViewHolder, position: Int) {
        holder.bind(context, imageList[position], position, onSelectedImageCallback, onLongClickListener)
    }

    override fun getItemCount(): Int = imageList.size
}

class PagerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val imageContent: PhotoView = itemView.findViewById(R.id.imageContent)
    private val progressBar: ProgressBar = itemView.findViewById(R.id.progressBar)

    fun bind(context: Context, imageData: ImageData, position: Int, onSelectedImageCallback: OnSelectedImageCallback, onLongClickListener: View.OnLongClickListener) {
        val options = RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.ALL)

        Glide.with(context)
                .load(imageData.image)
                .listener(object : RequestListener<Drawable?> {
                    override fun onLoadFailed(e: GlideException?, model: Any, target: Target<Drawable?>, isFirstResource: Boolean): Boolean {
                        progressBar.visibility = View.GONE
                        return false
                    }

                    override fun onResourceReady(resource: Drawable?, model: Any, target: Target<Drawable?>, dataSource: DataSource, isFirstResource: Boolean): Boolean {
                        progressBar.visibility = View.GONE
                        return false
                    }
                })
                .apply(options)
                .into(imageContent)

        imageContent.setOnClickListener(View.OnClickListener {
            onSelectedImageCallback.onSelectedImage(imageData)
        })
        imageContent.setOnLongClickListener(onLongClickListener)
    }
}