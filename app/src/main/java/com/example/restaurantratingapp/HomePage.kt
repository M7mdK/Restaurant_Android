package com.example.restaurantratingapp

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

var ratingMap : HashMap<String, Array<Float>> = HashMap<String, Array<Float>> ()

class HomePage : AppCompatActivity() {

    lateinit var resList: RecyclerView
    lateinit var resAdapter: RestaurantAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_page)

        setSupportActionBar(findViewById(R.id.toolbar))
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

        //ratingMap filled with restaurant name as a key and count,rating_average as values
        //It will be used to calculate the average rating in the RestaurantSpecificPage
        for(res in resNames){
            ratingMap.put(res, arrayOf(0f,0f))
        }

            resAdapter = RestaurantAdapter(resImages , resNames,this)

            resList.adapter = resAdapter
            resList.setHasFixedSize(true)

        }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.myAccount -> {
                val intent = Intent(this, LoginPage::class.java)
                startActivity(intent)
                true
            }
            R.id.logout -> {
                //remove user from sp
                val intent = Intent(this, LoginPage::class.java)
                startActivity(intent)
                true
            }

            else -> super.onOptionsItemSelected(item)

        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finishAffinity()
    }
}