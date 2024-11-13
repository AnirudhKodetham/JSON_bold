package com.example.myapplication


import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.widget.EditText

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var searchBar: EditText
    private lateinit var adapter: SearchAdapter

    // Sample data list
    private val itemList = listOf("iPhone", "iPhone 15", "iPhone 13 Pro", "Samsung Galaxy", "OnePlus 9",
        "Pixel 6", "Nokia", "Sony Xperia", "Motorola Razr", "Huawei P30")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        searchBar = findViewById(R.id.search_bar)
        recyclerView = findViewById(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Set up the adapter
        adapter = SearchAdapter(itemList)
        recyclerView.adapter = adapter

        // Initially hide the RecyclerView
        recyclerView.visibility = RecyclerView.GONE

        // Add a text listener to the search bar
        searchBar.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val query = s.toString()
                if (query.isNotEmpty()) {
                    adapter.filter(query)
                    recyclerView.visibility = RecyclerView.VISIBLE // Show when there’s a query
                } else {
                    recyclerView.visibility = RecyclerView.GONE // Hide when query is empty
                    adapter.clearList()
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
    }
}
