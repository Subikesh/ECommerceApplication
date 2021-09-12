package com.example.ecommerceapplication.extensions

import android.content.Context
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.ecommerceapplication.R

/**
 * Utility function to initialize recycler view with layout manager and adapter
 * @param layoutManager Layout manager of recyclerView
 * @param adapter       Adapter class for the recyclerView
 * @param hasFixedSize  If RV size if fixed or dynamic
 */
fun RecyclerView.initRecyclerView(
    layoutManager: RecyclerView.LayoutManager,
    adapter: RecyclerView.Adapter<out RecyclerView.ViewHolder>,
    hasFixedSize: Boolean = false
) {
    this.layoutManager = layoutManager
    this.adapter = adapter
    setHasFixedSize(hasFixedSize)
}

fun ImageView.getGlideImage(context: Context, imgUrl: String, error: Int = R.drawable.img_not_loaded1_4x) {
    val circularProgressDrawable = CircularProgressDrawable(context)
    circularProgressDrawable.strokeWidth = 5f
    circularProgressDrawable.centerRadius = 30f
    circularProgressDrawable.start()

    val requestOptions = RequestOptions().apply {
        placeholder(circularProgressDrawable)
        error(error)
    }

    Glide.with(context)
        .setDefaultRequestOptions(requestOptions)
        .load(imgUrl)
        .centerCrop()
        .into(this)
}