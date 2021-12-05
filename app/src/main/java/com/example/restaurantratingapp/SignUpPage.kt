package com.example.restaurantratingapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class SignUpPage : AppCompatActivity() {

    lateinit var signupEmail: EditText
    lateinit var signupPassword : EditText
    lateinit var signupButton: Button

    private lateinit var auth:FirebaseAuth
    private lateinit var database:FirebaseDatabase
    private lateinit var dbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.signup_page)

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        dbRef = database.reference

        signupEmail = findViewById(R.id.signupEmailEntry) as EditText
        signupPassword = findViewById(R.id.signupPasswordEntery) as EditText
        signupButton = findViewById(R.id.signupButton) as Button

        signupButton.setOnClickListener{
            auth.createUserWithEmailAndPassword(signupEmail.text.toString(), signupPassword.text.toString())

        }
    }
}