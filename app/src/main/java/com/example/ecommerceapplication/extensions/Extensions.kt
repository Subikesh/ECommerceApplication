package com.example.ecommerceapplication.extensions

import androidx.recyclerview.widget.RecyclerView

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
