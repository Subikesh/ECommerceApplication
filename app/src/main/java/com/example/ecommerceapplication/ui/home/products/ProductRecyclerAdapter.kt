package com.example.ecommerceapplication.ui.home.products

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.data.entities.Product
import com.example.ecommerceapplication.R
import kotlin.math.min

class ProductRecyclerAdapter(val productList: Array<Product>?, val context: Context) :
    RecyclerView.Adapter<ProductRecyclerAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.product_card, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (productList != null) {
            val currProduct = productList[position]
            holder.productTitle.text = currProduct.title
        }
    }

    override fun getItemCount() = min(productList?.size ?: 0, 6)

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val productImage: ImageView = itemView.findViewById(R.id.product_image)
        val productTitle: TextView = itemView.findViewById(R.id.product_title)
    }
}