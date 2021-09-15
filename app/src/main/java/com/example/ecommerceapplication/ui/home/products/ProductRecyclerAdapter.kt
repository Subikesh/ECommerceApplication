package com.example.ecommerceapplication.ui.home.products

import android.content.Context
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.data.models.Product
import com.example.ecommerceapplication.R
import com.example.ecommerceapplication.extensions.getGlideImage
import kotlin.math.min

class ProductRecyclerAdapter(
    private val productList: Array<Product>,
    val context: Context,
    private val MAX_PRODUCTS: Int? = null
) :
    RecyclerView.Adapter<ProductRecyclerAdapter.ViewHolder>() {

    private val CARD_TITLE_MAXLEN = 40

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
        val currProduct = productList[position]

        // Set image from url to glide
        holder.productImage.getGlideImage(context, currProduct.imageUrl)

        // Set product title text. If length > CARD_TITLE_MAXLEN, strip and add '...' at the end
        holder.productTitle.text =
            if (currProduct.title.length > CARD_TITLE_MAXLEN) currProduct.title.substring(0, CARD_TITLE_MAXLEN) + "..."
            else currProduct.title
        // Set maximum retail price as strike text
        holder.productMrp.paintFlags =
            holder.productMrp.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
        holder.productMrp.text = "\u20B9${currProduct.maximumRetailPrice.value}"

        holder.productPrice.text = "\u20B9${currProduct.discountPrice.value}"
        if (currProduct.discountPercent < 20)
            holder.discount.visibility = GONE
        else
            holder.discount.text = "${currProduct.discountPercent.toInt()}% Off"

    }

    /**
     * Maximum count of product cards to be displayed in each category in homepages
     * If MAX_PRODUCTS is not specified: returns productsList size, else returns min of products' size and MAX_PRODUCTS
     * @return product count
     */
    override fun getItemCount() = MAX_PRODUCTS?.let { min(productList.size, it) } ?: productList.size

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