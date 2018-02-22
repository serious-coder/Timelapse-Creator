package io.github.sdsstudios.timelapsecreator

import android.content.Context
import android.net.Uri
import android.support.v7.widget.AppCompatImageView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Picasso

/**
 * Created by Seth on 21/02/18.
 */

class ImageAdapter(
        private val mAppCtx: Context,
        var uriList: MutableList<Uri>
) : RecyclerView.Adapter<ImageAdapter.ViewHolder>() {

    private var mImageWidthPx: Int = 0

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent!!.context).inflate(R.layout.adapter_image, null)

        view.layoutParams = RecyclerView.LayoutParams(mImageWidthPx, mImageWidthPx)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        holder!!.apply {
            Picasso.with(mAppCtx)
                    .load(uriList[position])
                    .resize(mImageWidthPx, mImageWidthPx)
                    .centerCrop()
                    .into(imageView)
        }
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView?) {
        super.onAttachedToRecyclerView(recyclerView)

        val columnSpan = ImageGridHelper.calculateColumnSpan(recyclerView!!.width)
        mImageWidthPx = ImageGridHelper.calculateImageWidthPx(columnSpan, recyclerView.width)
    }

    override fun getItemCount() = uriList.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView = itemView.findViewById<AppCompatImageView>(R.id.imageView)
    }
}