package com.example.ecommerceapplication.extensions

import androidx.recyclerview.widget.RecyclerView

fun RecyclerView.initRecyclerView(layoutManager: RecyclerView.LayoutManager,
                                  adapter: RecyclerView.Adapter<out RecyclerView.ViewHolder>,
                                  hasFixedSize: Boolean = true) {
    this.layoutManager = layoutManager
    this.adapter = adapter
    setHasFixedSize(hasFixedSize)
}