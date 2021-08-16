package com.example.ecommerceapplication.ui.home.filterplaceholder

import java.util.ArrayList
import java.util.HashMap

object FilterPlaceholderContent {

    /**
     * An array of sample (placeholder) items.
     */
    val FILTERS: MutableList<Filter> = ArrayList()

    /**
     * A map of sample (placeholder) items, by ID.
     */
    val FILTER_MAP: MutableMap<String, Filter> = HashMap()

    private val COUNT = 25

    init {
        // Add some sample items.
        for (i in 1..COUNT) {
            addItem(createPlaceholderItem(i))
        }
    }

    private fun addItem(item: Filter) {
        FILTERS.add(item)
        FILTER_MAP[item.id] = item
    }

    private fun createPlaceholderItem(position: Int): Filter {
        return Filter(position.toString(), "Filter " + position, makeDetails(position))
    }

    private fun makeDetails(position: Int): String {
        val builder = StringBuilder()
        builder.append("Details about Filter: ").append(position)
        for (i in 0 until position) {
            builder.append("\nMore details information here.")
        }
        return builder.toString()
    }

    /**
     * A placeholder item representing a piece of content.
     */
    data class Filter(val id: String, val content: String, val details: String) {
        override fun toString(): String = content
    }
}