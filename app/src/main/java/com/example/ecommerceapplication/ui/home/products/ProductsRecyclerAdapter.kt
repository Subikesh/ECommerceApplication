package com.example.ecommerceapplication.ui.home.products

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.ecommerceapplication.R

/**
 * Adapter for recycler view to display products in home page
 * @param categoryList List of strings showing the category title
 */
class ProductsRecyclerAdapter(private val categoryList: Array<String>) :
    RecyclerView.Adapter<ProductsRecyclerAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.products_row_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.textView.text = categoryList[position]
    }

    override fun getItemCount() = categoryList.size

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textView: TextView = view.findViewById(R.id.category_row_title)
        val showAllButton: Button = view.findViewById(R.id.show_all_button)

        init {
            showAllButton.setOnClickListener {
                val bundle = bundleOf("title" to textView.text)
                view.findNavController().navigate(R.id.action_navigation_home_to_categoryFragment, bundle)
            }
        }
    }
}