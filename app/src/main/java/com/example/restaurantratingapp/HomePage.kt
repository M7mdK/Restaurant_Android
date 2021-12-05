package com.example.restaurantratingapp

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class HomePage : AppCompatActivity() {

    lateinit var resList: RecyclerView
    lateinit var resAdapter: RestaurantAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_page)

            resList = findViewById(R.id.RecyclerRestaurants) as RecyclerView

            val resNames: Array<String> = arrayOf("KFC","Macdonalds","Burger King","Dominos Pizza","Subway","Pizza Hut","Papa","DQ")
            val resImages: Array<Int> = arrayOf(
                R.drawable.kfc_logo,
                R.drawable.mac_logo,
                R.drawable.burger_king_logo,
                R.drawable.dominos_pizza_logo,
                R.drawable.subway_logo,
                R.drawable.pizza_hut_logo,
                R.drawable.papa_logo,
                R.drawable.dq_logo


            )


            resAdapter = RestaurantAdapter(resImages , resNames,this)

            resList.adapter = resAdapter
            resList.setHasFixedSize(true)

        }
}