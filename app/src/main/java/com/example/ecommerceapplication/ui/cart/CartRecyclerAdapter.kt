package com.example.ecommerceapplication.ui.cart

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
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
    private val deleteCartItem: (CartItem) -> Unit
) :
    RecyclerView.Adapter<CartRecyclerAdapter.ViewHolder>() {

    /** Maximum length of product title */
    private val CARD_TITLE_MAXLEN = 40

    /**
     * Inflates UI for single product card
     * @return ViewHolder instance of productsRV
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.product_card, parent, false)
        return ViewHolder(view) {
            onItemClicked(cartProductList[it].product)
        }
    }

    /**
     * Sets the text for each products including MRP, offer price, product image, etc.
     */
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currItem = cartProductList[position].cartItem
        val currProduct = cartProductList[position].product

        // Set image from url to glide
        holder.productImage.getGlideImage(context, currProduct.imageUrl)

        holder.productTitle.text =
            if (currProduct.title.length > CARD_TITLE_MAXLEN) currProduct.title.substring(0, CARD_TITLE_MAXLEN) + "..."
            else currProduct.title

        holder.productPrice.text = context.getString(R.string.price_holder, currProduct.discountPrice)

        holder.quantity.setText(currItem.quantity)

        /** Limiting the item quantity from 1 to 10 */
        holder.quantity.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (p0?.isDigitsOnly() == true) {
                    val quantity = Integer.valueOf(p0.toString())
                    if (quantity <= 1) {
                        holder.quantity.setText("1")
                        holder.reduceProductButton.isEnabled = false
                    } else if (quantity >= 10) {
                        holder.quantity.setText("10")
                        holder.addProductButton.isEnabled = false
                    }
                } else {
                    holder.quantity.error = "Please enter a number between 1 to 10"
                }
            }

            override fun afterTextChanged(p0: Editable?) {}
        })

        holder.saveButton.setOnClickListener {
            val quantity = Integer.valueOf(holder.quantity.text.toString())
            saveItemQuantity(currItem, quantity)
        }

        holder.deleteButton.setOnClickListener {
            deleteCartItem(currItem)
            cartProductList.removeAt(position)
            notifyItemRemoved(position)
        }
    }

    /**
     * Maximum count of product cards to be displayed in each category in homepages
     * If MAX_PRODUCTS is not specified: returns productsList size, else returns min of products' size and MAX_PRODUCTS
     * @return product count
     */
    override fun getItemCount() = cartProductList.size

    /**
     * Class contains all child views of a product card
     */
    class ViewHolder(itemView: View, onItemClick: (Int) -> Unit) : RecyclerView.ViewHolder(itemView) {
        val productImage: ImageView = itemView.findViewById(R.id.cart_product_image)
        val productTitle: TextView = itemView.findViewById(R.id.cart_product_title)
        val productPrice: TextView = itemView.findViewById(R.id.cart_product_price)
        val quantity: EditText = itemView.findViewById(R.id.item_quantity)
        val addProductButton: Button = itemView.findViewById(R.id.add_quantity)
        val reduceProductButton: Button = itemView.findViewById(R.id.reduce_quantity)
        val saveButton: Button = itemView.findViewById(R.id.save_changes)
        val deleteButton: Button = itemView.findViewById(R.id.delete_button)

        init {
            itemView.setOnClickListener {
                onItemClick(absoluteAdapterPosition)
            }
        }
    }
}