package com.example.ecommerceapplication.extensions

import android.content.Context
import android.widget.ImageView
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
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

/**
 * Set Glide image from imgUrl to imageView with a circular progress gif while loading
 * @param context   Context of activity
 * @param imgUrl    Url of image
 * @param error     Error image to be shown if image not loaded
 */
fun ImageView.getGlideImage(context: Context, imgUrl: String?, error: Int = R.drawable.img_not_loaded1_4x) {
    val circularProgress = getCircularLoader(context)

    val requestOptions = RequestOptions().apply {
        placeholder(circularProgress)
        error(error)
    }

    Glide.with(context)
        .setDefaultRequestOptions(requestOptions)
        .load(imgUrl)
        .into(this)
}

/**
 * Return circular loader gif
 */
fun getCircularLoader(context: Context): CircularProgressDrawable {
    val circularProgressDrawable = CircularProgressDrawable(context)
    circularProgressDrawable.strokeWidth = 5f
    circularProgressDrawable.centerRadius = 30f
    circularProgressDrawable.start()
    return circularProgressDrawable
}

/**
 * Observe livedata and do operations only once
 */
fun <T> LiveData<T>.observeOnce(lifecycleOwner: LifecycleOwner, observer: Observer<T>) {
    observe(lifecycleOwner, object : Observer<T> {
        override fun onChanged(t: T?) {
            observer.onChanged(t)
            removeObserver(this)
        }
    })
}