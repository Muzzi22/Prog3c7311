package com.example.flowfund

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class CategoryActivity : AppCompatActivity() {

    // This list holds all the category names
    private val categoryList = mutableListOf<String>()
    private lateinit var adapter: CategoryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category)

        val etCategoryName = findViewById<EditText>(R.id.etCategoryName)
        val btnAddCategory = findViewById<Button>(R.id.btnAddCategory)
        val lvCategories = findViewById<ListView>(R.id.lvCategories)

        // Add some default categories to start with
        categoryList.addAll(listOf("🛒 Food", "🚌 Transport", "💡 Utilities", "🎬 Entertainment"))

        // Set up the adapter (connects our list to the ListView)
        adapter = CategoryAdapter()
        lvCategories.adapter = adapter

        // When the user taps "+ Add"
        btnAddCategory.setOnClickListener {
            val name = etCategoryName.text.toString().trim()

            when {
                name.isEmpty() -> {
                    Toast.makeText(this, "Please enter a category name", Toast.LENGTH_SHORT).show()
                }
                categoryList.contains(name) -> {
                    Toast.makeText(this, "Category already exists", Toast.LENGTH_SHORT).show()
                }
                else -> {
                    categoryList.add(name)
                    adapter.notifyDataSetChanged() // refresh the list
                    etCategoryName.text.clear()
                    Toast.makeText(this, "'$name' added!", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    // Inner class that controls how each row looks
    inner class CategoryAdapter : BaseAdapter() {

        override fun getCount(): Int = categoryList.size
        override fun getItem(position: Int): Any = categoryList[position]
        override fun getItemId(position: Int): Long = position.toLong()

        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
            val view = convertView ?: layoutInflater.inflate(R.layout.item_category, parent, false)

            val tvName = view.findViewById<TextView>(R.id.tvCategoryName)
            val tvDelete = view.findViewById<TextView>(R.id.tvDelete)

            tvName.text = categoryList[position]

            // Delete a category when ✕ is tapped
            tvDelete.setOnClickListener {
                val removed = categoryList[position]
                categoryList.removeAt(position)
                adapter.notifyDataSetChanged()
                Toast.makeText(this@CategoryActivity, "'$removed' removed", Toast.LENGTH_SHORT).show()
            }

            return view
        }
    }
}