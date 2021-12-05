package com.example.restaurantratingapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DatabaseReference


class CommentAdapter(private val dataSet: ArrayList<Comment>, private val database:DatabaseReference,
                     private val restaurantName: String?, private val currentUser: String?
) :
    RecyclerView.Adapter<CommentAdapter.ViewHolder>() {

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textUserName: TextView
        val commentTextView: TextView
        val buttonDelete: Button
        init {
            // Define click listener for the ViewHolder's View.
            textUserName = view.findViewById(R.id.commentUserName)
            commentTextView = view.findViewById(R.id.resName)
            buttonDelete = view.findViewById(R.id.deleteCommentButton)
            view.setOnClickListener {
                Toast.makeText(view.context,"hellooo",Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.comment_item, viewGroup, false)

        return ViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        viewHolder.textUserName.text = dataSet[position].userName;
        viewHolder.commentTextView.text = dataSet[position].commentText;

        if(dataSet[position].userId == this.currentUser) {
            viewHolder.buttonDelete.setOnClickListener {
                val userId = dataSet[position].userId
                val commentId = dataSet[position].commentId
                if (this.restaurantName != null) {
                    Toast.makeText(
                        viewHolder.buttonDelete.context,
                        "$userId -- $restaurantName -- $commentId", Toast.LENGTH_SHORT
                    ).show()
                    this.database.child("users").child(userId).child(this.restaurantName)
                        .child(commentId).removeValue()
                }
            }
        }else{
            viewHolder.buttonDelete.visibility = Button.INVISIBLE
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = dataSet.size

}
