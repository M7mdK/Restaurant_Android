package com.example.restaurantratingapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.recyclerview.widget.RecyclerView

class RestaurantSpecificPage : AppCompatActivity() {

    lateinit var listComments: RecyclerView
    private lateinit var commentAdapter: CommentAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.restaurant_specific_page)

        listComments = findViewById(R.id.listOfComments) as RecyclerView
        val comments: Array<Comment> = arrayOf(
        Comment(100,"user_1","This is the first review"),
        Comment(101,"user_2","This is the second review"),
        Comment(102,"user_3","This is the third review"),
            Comment(102,"user_4","This is the fourth     review")

            )


        commentAdapter = CommentAdapter(comments)

        listComments.adapter = commentAdapter
        listComments.setHasFixedSize(true)


    }

}