package com.example.restaurantratingapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.google.firebase.auth.FirebaseAuth

class LoginPage : AppCompatActivity() {

    lateinit var loginEmail: EditText
    lateinit var loginPassword : EditText
    lateinit var loginButton: Button

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_page)

        loginEmail = findViewById(R.id.loginEmailEntry) as EditText
        loginPassword = findViewById(R.id.loginPasswordEntery) as EditText
        loginButton = findViewById(R.id.loginButton) as Button

        loginButton.setOnClickListener {
            auth = FirebaseAuth.getInstance()
            auth.signInWithEmailAndPassword(loginEmail.text.toString(), loginPassword.text.toString())
        }

    }
}