package com.example.adaptivelistviewapp

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.slidingpanelayout.widget.SlidingPaneLayout

class MainActivity : AppCompatActivity() {

    private lateinit var slidingPaneLayout: SlidingPaneLayout
    private lateinit var listView: ListView
    private lateinit var detailImage: ImageView
    private lateinit var detailTitle: TextView
    private lateinit var detailDescription: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        slidingPaneLayout = findViewById(R.id.slidingPaneLayout)
        listView = findViewById(R.id.listView)
        detailImage = findViewById(R.id.detailImage)
        detailTitle = findViewById(R.id.detailTitle)
        detailDescription = findViewById(R.id.detailDescription)

        // Adjust for system bars
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val fruits = listOf(
            Fruit("Apple", "Apples are high in fiber and Vitamin C.", R.drawable.ic_apple),
            Fruit("Banana", "Bananas are a great source of potassium.", R.drawable.ic_banana),
            Fruit("Orange", "Oranges are famous for their Vitamin C content.", R.drawable.ic_orange),
            Fruit("Grapes", "Grapes are often used to make wine or juice.", R.drawable.ic_grapes),
            Fruit("Mango", "Mangoes are known as the king of fruits.", R.drawable.ic_mango)
        )

        val adapter = object : ArrayAdapter<Fruit>(this, R.layout.list_item, fruits) {
            override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
                val view = convertView ?: layoutInflater.inflate(R.layout.list_item, parent, false)
                val fruit = getItem(position)!!
                
                view.findViewById<TextView>(R.id.itemName).text = fruit.name
                view.findViewById<ImageView>(R.id.itemImage).setImageResource(fruit.imageResourceId)
                
                return view
            }
        }

        listView.adapter = adapter
        listView.setOnItemClickListener { _, _, position, _ ->
            val fruit = fruits[position]
            updateDetail(fruit)
            slidingPaneLayout.openPane()
        }

        // Handle back press to close the detail pane if it's open (single-pane mode)
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(false) {
            override fun handleOnBackPressed() {
                slidingPaneLayout.closePane()
            }
        }.also { callback ->
            slidingPaneLayout.addPanelSlideListener(object : SlidingPaneLayout.PanelSlideListener {
                override fun onPanelSlide(panel: View, slideOffset: Float) {}
                override fun onPanelOpened(panel: View) {
                    callback.isEnabled = true
                }
                override fun onPanelClosed(panel: View) {
                    callback.isEnabled = false
                }
            })
        })
    }

    private fun updateDetail(fruit: Fruit) {
        detailImage.setImageResource(fruit.imageResourceId)
        detailTitle.text = fruit.name
        detailDescription.text = fruit.description
    }
}
