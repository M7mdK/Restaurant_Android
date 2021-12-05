package com.example.restaurantratingapp

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth

val SHARED_PREF = "SharedPref123"

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
            val email = loginEmail.text.toString()
            val pass = loginPassword.text.toString()
                auth.signInWithEmailAndPassword(email, pass).
                    addOnCompleteListener { task: Task<AuthResult> ->
                        if (task.isSuccessful){
                            val userID = auth.currentUser?.uid
                            val sharedPref =  getSharedPreferences(SHARED_PREF, Context.MODE_PRIVATE)
                            var editor = sharedPref.edit()
                            editor.putString("Email",email)
                            editor.putString("Pass",pass)
                            editor.putString("UserID",userID)
                            editor.commit()
                            //sharedPreference.getString("username","defaultName")
                            Toast.makeText(this, "Logged in Successfully!" , Toast.LENGTH_SHORT).show()
                            val intent = Intent(this, HomePage::class.java)
                            startActivity(intent)

                        }
                    }

        }
    }
}