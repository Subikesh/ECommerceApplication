package com.example.ecommerceapplication.ui.home.products

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.data.entities.Category
import com.example.ecommerceapplication.R
import kotlin.math.min

/**
 * Adapter for recycler view to display products in home page
 * @param categoryList List of strings showing the category title
 */
class CategoryRecyclerAdapter(private val categoryList: Array<Category>, val context: Context) :
    RecyclerView.Adapter<CategoryRecyclerAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.category_row_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currCategory = categoryList[position]
        holder.textView.text = currCategory.title
        holder.productsView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        holder.productsView.setHasFixedSize(true)
        holder.productsView.adapter =
            ProductRecyclerAdapter(currCategory.productList, holder.productsView.context)
    }

    override fun getItemCount() = categoryList.size
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textView: TextView = view.findViewById(R.id.category_row_title)
        val showAllButton: ImageButton = view.findViewById(R.id.show_all_button)
        val productsView: RecyclerView = view.findViewById(R.id.child_products_rv)

        init {
            showAllButton.setOnClickListener {
                val bundle = bundleOf("title" to textView.text)
                view.findNavController()
                    .navigate(R.id.action_navigation_home_to_categoryFragment, bundle)
            }
        }
    }
}