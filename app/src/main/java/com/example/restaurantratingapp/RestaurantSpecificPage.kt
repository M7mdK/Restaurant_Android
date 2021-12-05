package com.example.restaurantratingapp

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.util.logging.Logger

class RestaurantSpecificPage : AppCompatActivity() {

    lateinit var listComments: RecyclerView
    private lateinit var commentAdapter: CommentAdapter
    lateinit var addCommentButton: Button
    // database reference to the Firebase
    private lateinit var database: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.restaurant_specific_page)

        listComments = findViewById(R.id.listOfComments)
        addCommentButton = findViewById(R.id.AddComment)
        database = Firebase.database.reference

//        val comments: Array<Comment> = arrayOf(
//        Comment(100,"user_1","This is the first review"),
//        Comment(101,"user_2","This is the second review"),
//        Comment(102,"user_3","This is the third review"),
//            Comment(102,"user_4","This is the fourth     review")
//
//            )
        val restaurantName = intent.getStringExtra("resName")
        val log = Logger.getLogger(MainActivity::class.java.name)

        val comments:ArrayList<Comment> = ArrayList()

        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // Get Post object and use the values to update the UI
                for (snapshot in dataSnapshot.children) {
                    snapshot.children.forEach { user ->
                        user.children.forEach{ restaurant ->
                            if(restaurant.key == restaurantName){
                                restaurant.children.forEach { comment ->
                                    comments.add(Comment(comment.key.toString(),
                                        commentText = comment.value.toString(),
                                        userName = user.key.toString()
                                        ))
                                }
                            }
                        }
                    }
                }
                // ...
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                log.warning("loadPost:onCancelled,"+ databaseError.toException())
            }
        }
        database.addValueEventListener(postListener)

        commentAdapter = CommentAdapter(comments)
        listComments.adapter = commentAdapter
        listComments.setHasFixedSize(true)


        // init the user info
        val sharedPref =  getSharedPreferences(SHARED_PREF, Context.MODE_PRIVATE)
        var userId = sharedPref.getString("UserID","defUser")

        // init the add comment listener
        initAddComment(userId,"Macdo")
    }

    private fun initAddComment(userId: String?, restaurantName: String?){
        addCommentButton.setOnClickListener {
            //Inflate the dialog as custom view
            val commentBoxView = LayoutInflater.from(this).inflate(R.layout.add_comment_box, null)

            //AlertDialogBuilder
            val commentBoxBuilder = AlertDialog.Builder(this).setView(commentBoxView)

            // add save comment listener

            val  button = commentBoxView.findViewById<Button>(R.id.saveCommentButton)
            val editText = commentBoxView.findViewById<EditText>(R.id.addCommentText)
            val commentText = editText.text
            button.setOnClickListener {
                Toast.makeText(this,"Clicked!! $userId",Toast.LENGTH_SHORT).show()
                if (userId != null && restaurantName != null && commentText.toString() != "") {
                    database.child("users").child(userId).child("restaurants")
                        .child(restaurantName).push().setValue(commentText.toString())
                }
            }
            //show dialog
            commentBoxBuilder.show()


        }
    }

}