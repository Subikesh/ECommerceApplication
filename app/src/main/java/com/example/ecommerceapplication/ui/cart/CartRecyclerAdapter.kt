package com.example.ecommerceapplication.ui.cart

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.text.isDigitsOnly
import androidx.recyclerview.widget.RecyclerView
import com.example.data.roomdb.entities.CartItem
import com.example.data.roomdb.entities.Product
import com.example.data.roomdb.relations.CartItemAndProduct
import com.example.ecommerceapplication.R
import com.example.ecommerceapplication.extensions.getGlideImage

class CartRecyclerAdapter(
    private val cartProductList: MutableList<CartItemAndProduct>,
    val context: Context,
    // Redirect to product page
    private val onItemClicked: (Product) -> Unit,
    // Saving cart item quantity
    private val saveItemQuantity: (CartItem, Int) -> Unit,
    // Deleting cart item
    private val deleteCartItem: (CartItem) -> Unit,
    // Updating the total cost of cart
    private val updateTotal: (MutableList<CartItemAndProduct>, Int) -> Unit
) :
    RecyclerView.Adapter<CartRecyclerAdapter.ViewHolder>() {

    /** Maximum length of product title */
    private val CARD_TITLE_MAXLEN = 60

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.cart_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val absPosition = holder.absoluteAdapterPosition
        val currCart = cartProductList[absPosition]
        val currItem = cartProductList[absPosition].cartItem
        val currProduct = cartProductList[absPosition].product

        // Set image from url to glide
        holder.productImage.getGlideImage(context, currProduct.imageUrl)
        holder.productTitle.text =
            if (currProduct.title.length > CARD_TITLE_MAXLEN) currProduct.title.substring(0, CARD_TITLE_MAXLEN) + "..."
            else currProduct.title
        holder.productPrice.text =
            context.getString(R.string.price_holder, currProduct.discountPrice * currItem.quantity)
        holder.quantity.text = currItem.quantity.toString()
        if (currItem.quantity == 1)
            holder.reduceProductButton.isEnabled = false
        else if (currItem.quantity == 10)
            holder.addProductButton.isEnabled = false

        holder.productTitle.setOnClickListener {
            onItemClicked(currProduct)
        }

        holder.productImage.setOnClickListener {
            onItemClicked(currProduct)
        }

        // Increase item quantity
        holder.addProductButton.setOnClickListener {
            var quantity = Integer.valueOf(holder.quantity.text.toString())

            holder.reduceProductButton.isEnabled = true
            if (++quantity == 10)
                holder.addProductButton.isEnabled = false

            holder.productPrice.text = "${currProduct.discountPrice * quantity}"
            holder.quantity.text = "$quantity"
            holder.saveButton.isEnabled = true
        }

        // Decrease item quantity
        holder.reduceProductButton.setOnClickListener {
            var quantity = Integer.valueOf(holder.quantity.text.toString())

            holder.addProductButton.isEnabled = true
            if (--quantity == 1)
                holder.reduceProductButton.isEnabled = false

            holder.productPrice.text = "${currProduct.discountPrice * quantity}"
            holder.quantity.text = "$quantity"
            holder.saveButton.isEnabled = true
        }

        // Save the item quantity
        holder.saveButton.setOnClickListener {
            val quantity = Integer.valueOf(holder.quantity.text.toString())
            saveItemQuantity(currItem, quantity)
            holder.saveButton.isEnabled = false
            updateTotal(cartProductList, currItem.cartId)
        }

        holder.deleteButton.setOnClickListener {
            deleteCartItem(currItem)
            cartProductList.remove(currCart)
            updateTotal(cartProductList, currItem.cartId)
            notifyItemRemoved(absPosition)
        }
    }

    override fun getItemCount() = cartProductList.size

    /**
     * Class contains all child views of a product card
     */
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val productImage: ImageView = itemView.findViewById(R.id.cart_product_image)
        val productTitle: TextView = itemView.findViewById(R.id.cart_product_title)
        val productPrice: TextView = itemView.findViewById(R.id.cart_product_price)
        val quantity: TextView = itemView.findViewById(R.id.item_quantity)
        val addProductButton: ImageButton = itemView.findViewById(R.id.add_quantity)
        val reduceProductButton: ImageButton = itemView.findViewById(R.id.reduce_quantity)
        val saveButton: Button = itemView.findViewById(R.id.save_changes)
        val deleteButton: Button = itemView.findViewById(R.id.delete_button)
    }
}