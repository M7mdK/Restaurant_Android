package com.example.restaurantratingapp

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.util.logging.Logger

class UserAccount : AppCompatActivity() {
    private lateinit var database: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.user_account)

        database = Firebase.database.reference
        // init the user info
        val sharedPref =  getSharedPreferences(SHARED_PREF, Context.MODE_PRIVATE)
        var userId = sharedPref.getString("UserID","defUser")

        val log = Logger.getLogger(MainActivity::class.java.name)
        var comments:ArrayList<Comment>
        var listComments: RecyclerView = findViewById(R.id.myAcountListReviews)
        var commentAdapter: CommentAdapter

        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // Get Post object and use the values to update the UI
                comments = ArrayList()
                for (snapshot in dataSnapshot.children) {
                    var userName = ""
                    snapshot.children.forEach { user ->
                        if(user.key.toString() == userId) {
                            user.children.forEach { restaurant ->
                                if (restaurant.key.toString() == "FullName") {
                                    userName = restaurant.value.toString()
                                }
                                log.info("Herreee ${user.key.toString()} and $userId")
                                restaurant.children.forEach { comment ->
                                    log.info("Hereee ${comment.toString()}")
                                    val rating = comment.children.toList()[0].value
                                    val commentText = comment.children.toList()[1].value
                                    comments.add(
                                        Comment(
                                            commentText = commentText.toString(),
                                            userId = "",
                                            commentId = comment.key.toString(),
                                            userName = ""
                                        )
                                    )
                                }
                            }
                        }
                    }
                }
                commentAdapter = CommentAdapter(comments,database,"",userId)
                listComments.adapter = commentAdapter
                listComments.setHasFixedSize(true)
                // ...
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                log.warning("loadPost:onCancelled,"+ databaseError.toException())
            }
        }
        database.addValueEventListener(postListener)
    }
}