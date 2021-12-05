package com.example.restaurantratingapp

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.*
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

        // dummy data
//        val comments: Array<Comment> = arrayOf(
//        Comment(100,"user_1","This is the first review"),
//        Comment(101,"user_2","This is the second review"),
//        Comment(102,"user_3","This is the third review"),
//            Comment(102,"user_4","This is the fourth     review")
//
//            )
        val restaurantName = intent.getStringExtra("resName")
        val restaurantImageId  = intent.getIntExtra("resImage",0)

        // initialize image view of the restaurant
        val resImageView  = findViewById<ImageView>(R.id.restaurantLogo)
        resImageView.setImageResource(restaurantImageId)

        val resNameTextView = findViewById<TextView>(R.id.restaurantNameDetail)
        resNameTextView.text = restaurantName


        // init the user info
        val sharedPref =  getSharedPreferences(SHARED_PREF, Context.MODE_PRIVATE)
        var userId = sharedPref.getString("UserID","defUser")

        val log = Logger.getLogger(MainActivity::class.java.name)
        log.info("here----------------: $restaurantName")
        var comments:ArrayList<Comment>

        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // Get Post object and use the values to update the UI
                comments = ArrayList()
                for (snapshot in dataSnapshot.children) {
                    var userName = ""
                    snapshot.children.forEach { user ->

                        user.children.forEach{ restaurant ->
                            if(restaurant.key.toString() == "FullName"){
                                userName = restaurant.value.toString()
                            }
                            if(restaurant.key.toString() == restaurantName){
                                restaurant.children.forEach { comment ->
                                    comments.add(Comment(
                                        commentText = comment.value.toString(),
                                        userId = user.key.toString(),
                                        commentId = comment.key.toString(),
                                        userName = userName
                                    ))
                                }
                            }
                        }
                    }
                }


                commentAdapter = CommentAdapter(comments,database,restaurantName,userId)
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

        // init the add comment listener
        initAddComment(userId,restaurantName)
    }

    private fun initAddComment(userId: String?, restaurantName: String?){
        addCommentButton.setOnClickListener {
            //Inflate the dialog as custom view
            val commentBoxView = LayoutInflater.from(this).inflate(R.layout.add_comment_box, null)

            //AlertDialogBuilder
            val commentBoxBuilder = AlertDialog.Builder(this).setView(commentBoxView)

            // add save comment listener

            val editText = commentBoxView.findViewById<EditText>(R.id.addCommentText)
            val commentText = editText.text
            commentBoxBuilder.setNegativeButton("Cancel") { dialog, whichButton ->
                dialog.dismiss()
            }
            commentBoxBuilder.setPositiveButton("Save") { dialog, whichButton ->
                Toast.makeText(this,"Clicked!! $userId",Toast.LENGTH_SHORT).show()
                if (userId != null && restaurantName != null && commentText.toString() != "") {
                    val isCompleted = database.child("users").child(userId)
                        .child(restaurantName).push().setValue(commentText.toString()).isComplete

                    if(isCompleted){
                        dialog.dismiss()
                    }
                }
            }
            //show dialog
            commentBoxBuilder.show()


        }
    }

}