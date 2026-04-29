package com.example.flowfund

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class CategoryActivity : AppCompatActivity() {

    private val categoryList = mutableListOf<String>()
    private lateinit var adapter: CategoryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category)

        val etCategoryName = findViewById<EditText>(R.id.etCategoryName)
        val btnAddCategory = findViewById<Button>(R.id.btnAddCategory)
        val lvCategories   = findViewById<ListView>(R.id.lvCategories)

        // Default categories
        categoryList.addAll(listOf("🛒 Food", "🚌 Transport", "💡 Utilities", "🎬 Entertainment"))

        adapter = CategoryAdapter()
        lvCategories.adapter = adapter

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
                    adapter.notifyDataSetChanged()
                    etCategoryName.text.clear()
                    Toast.makeText(this, "'$name' added!", Toast.LENGTH_SHORT).show()
                }
            }
        }

        // Bottom nav — go back to Home
        findViewById<LinearLayout>(R.id.navHome).setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
            startActivity(intent)
        }

        // Bottom nav — already on Categories
        findViewById<LinearLayout>(R.id.navCategories).setOnClickListener {
            // Already here
        }
    }

    inner class CategoryAdapter : BaseAdapter() {

        override fun getCount(): Int = categoryList.size
        override fun getItem(position: Int): Any = categoryList[position]
        override fun getItemId(position: Int): Long = position.toLong()

        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
            val view = convertView ?: layoutInflater.inflate(R.layout.item_category, parent, false)

            val tvName   = view.findViewById<TextView>(R.id.tvCategoryName)
            val tvDelete = view.findViewById<TextView>(R.id.tvDelete)

            tvName.text = categoryList[position]

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