package com.example.ecommerceapplication.ui.cart

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.data.roomdb.entities.OrderCartItem
import com.example.ecommerceapplication.R
import com.example.ecommerceapplication.extensions.getGlideImage
import java.util.*

class OrderRecyclerAdapter(private val orderList: List<OrderCartItem>, private val context: Context) :
    RecyclerView.Adapter<OrderRecyclerAdapter.ViewHolder>() {

    private val CARD_TITLE_MAXLEN = 20

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.order_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currOrder = orderList[position]
        val currProduct = currOrder.product!!

        holder.orderImage

        holder.orderImage.getGlideImage(context, currProduct.imageUrl)
        holder.orderTitle.text =
            if (currProduct.title.length > CARD_TITLE_MAXLEN)
                currProduct.title.substring(0, CARD_TITLE_MAXLEN) + "..."
            else currProduct.title
        holder.orderPrice.text =
            context.getString(R.string.price_holder, currProduct.discountPrice * currOrder.quantity)
        holder.orderQty.text = "Qty: ${currOrder.quantity}"
        holder.orderDate.text = "Ordered: ${getDateFromMillis(currOrder.createdAt)}"

        holder.status.text = if (currOrder.isSuccessful) {
            holder.status.setTextColor(ContextCompat.getColor(context, R.color.dark_green))
            "Success"
        } else {
            holder.status.setTextColor(ContextCompat.getColor(context, R.color.dark_red))
            "Failed"
        }
    }

    private fun getDateFromMillis(secs: Long): String {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = secs
        return "${calendar.get(Calendar.DAY_OF_MONTH)}/" +
                "${calendar.get(Calendar.MONTH)}/" +
                "${calendar.get(Calendar.YEAR)} " +
                "${calendar.get(Calendar.HOUR_OF_DAY)}: ${calendar.get(Calendar.MINUTE)}"
    }

    override fun getItemCount() = orderList.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val orderImage: ImageView = itemView.findViewById(R.id.order_item_image)
        val orderTitle: TextView = itemView.findViewById(R.id.order_item_title)
        val orderDate: TextView = itemView.findViewById(R.id.order_item_date)
        val orderQty: TextView = itemView.findViewById(R.id.order_item_qty)
        val orderPrice: TextView = itemView.findViewById(R.id.order_item_price)
        val status: TextView = itemView.findViewById(R.id.order_item_status)
    }
}