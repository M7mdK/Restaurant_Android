package com.example.restaurantratingapp

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    lateinit var frontLoginButton : Button
    lateinit var frontSignupButton : Button
    lateinit var guestButton : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.front_page)
        val sharedPref =  getSharedPreferences(SHARED_PREF, Context.MODE_PRIVATE)

        var userId = sharedPref.getString("UserID","defUser")
        if(userId != "defUser"){
            val intentLogin = Intent(this, HomePage::class.java)
            startActivity(intentLogin)
        }
        frontLoginButton = findViewById(R.id.frontLoginButton) as Button
        frontSignupButton = findViewById(R.id.frontSignupButton) as Button
        guestButton = findViewById(R.id.guestButton) as Button

        frontLoginButton.setOnClickListener {
            val intentLogin = Intent(this, LoginPage::class.java)
            startActivity(intentLogin)
        }

        frontSignupButton.setOnClickListener {
            val intentSignup = Intent(this, SignUpPage::class.java)
            startActivity(intentSignup)
        }

        guestButton.setOnClickListener {
            var editor = sharedPref.edit()
            editor.remove("Email")
            editor.remove("Pass")
            editor.remove("UserID")
            editor.commit()
            val intentGuest = Intent(this, HomePage::class.java)
            startActivity(intentGuest)
        }
    }
}