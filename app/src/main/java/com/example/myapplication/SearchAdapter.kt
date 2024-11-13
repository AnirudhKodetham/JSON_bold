package com.example.myapplication

import android.graphics.Typeface
import android.text.SpannableString
import android.text.Spanned
import android.text.style.StyleSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class SearchAdapter(private val originalList: List<String>) :
    RecyclerView.Adapter<SearchAdapter.ViewHolder>() {

    private var filteredList: List<String> = originalList
    private var query: String = ""

    // ViewHolder for RecyclerView items
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val itemText: TextView = view.findViewById(R.id.item_text)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_view, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = filteredList[position]
        holder.itemText.text = getHighlightedText(item, query)
    }

    override fun getItemCount(): Int = filteredList.size

    // Method to filter the list based on the query
    fun filter(query: String) {
        this.query = query
        filteredList = if (query.isEmpty()) {
            emptyList()
        } else {
            originalList.filter { it.contains(query, ignoreCase = true) }
        }
        notifyDataSetChanged()
    }

    // Method to clear the list when search is empty
    fun clearList() {
        filteredList = emptyList()
        notifyDataSetChanged()
    }

    // Method to apply bold styling to matched parts of the text
    private fun getHighlightedText(text: String, query: String): SpannableString {
        val spannable = SpannableString(text)
        val startIndex = text.indexOf(query, ignoreCase = true)
        if (startIndex >= 0) {
            spannable.setSpan(
                StyleSpan(Typeface.BOLD),
                startIndex,
                startIndex + query.length,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        }
        return spannable
    }
}
