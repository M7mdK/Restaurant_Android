package com.example.restaurantratingapp

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast

class HomePage : AppCompatActivity() {

    lateinit var m7text : TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_page)

        m7text = findViewById(R.id.Title)

        val sharedPref =  getSharedPreferences(SHARED_PREF, Context.MODE_PRIVATE)
        val s1 = sharedPref.getString("Email","defEmail")
        val s2 = sharedPref.getString("Pass","def")
        val s3 = sharedPref.getString("UserID","def")

        m7text.text = "Hello World,\n Email $s1 \n Pass $s2 \n UserID $s3 "

    }
}