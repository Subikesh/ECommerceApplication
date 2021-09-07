package com.example.ecommerceapplication.ui.home.products

import android.content.Context
import android.graphics.Paint
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

    companion object {
        private const val MAX_PRODUCT_COUNT = 6
    }

    /**
     * Inflates UI for single product card
     * @return ViewHolder instance of productsRV
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.product_card, parent, false)
        return ViewHolder(view)
    }

    /**
     * Sets the text for each products including MRP, offer price, product image, etc.
     */
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (productList != null) {
            val currProduct = productList[position]
            // Set product title text. If length > 30, strip and add '...' at the end
            holder.productTitle.text =
                if (currProduct.title.length > 30) currProduct.title.substring(0, 30) + "..."
                else currProduct.title
            // Set maximum retail price as strike text
            holder.productMrp.text = "\u20B9${currProduct.maximumRetailPrice.value}"
            holder.productMrp.paintFlags =
                holder.productMrp.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG

            holder.productPrice.text = "\u20B9${currProduct.discountPrice.value}"
            holder.discount.text = context.resources.getString(
                R.string.show_discount,
                currProduct.discountPercent.toInt()
            )
        }
    }

    /**
     * Maximum count of product cards to be displayed in each category in homepages
     * @return product count
     */
    override fun getItemCount() = min(productList?.size ?: 0, MAX_PRODUCT_COUNT)

    /**
     * Class contains all child views of a product card
     */
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val productImage: ImageView = itemView.findViewById(R.id.product_image)
        val productTitle: TextView = itemView.findViewById(R.id.product_title)
        val productMrp: TextView = itemView.findViewById(R.id.product_strike_price)
        val productPrice: TextView = itemView.findViewById(R.id.product_price)
        val discount: TextView = itemView.findViewById(R.id.discount_text)
    }
}