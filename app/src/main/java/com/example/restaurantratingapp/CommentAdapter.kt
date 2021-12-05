package com.example.restaurantratingapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView


class CommentAdapter(private val dataSet: Array<Comment>) :
    RecyclerView.Adapter<CommentAdapter.ViewHolder>() {

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textUserName: TextView
        val commentTextView: TextView
        val buttonEdit: Button
        init {
            // Define click listener for the ViewHolder's View.
            textUserName = view.findViewById(R.id.commentUserName)
            commentTextView = view.findViewById(R.id.commentText)
            buttonEdit = view.findViewById(R.id.editCommentButton)
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
        viewHolder.commentTextView.text = dataSet[position].userName;
        viewHolder.buttonEdit.setOnClickListener {
            Toast.makeText(viewHolder.buttonEdit.context,
                "Button number: $position",Toast.LENGTH_SHORT).show()
        }

    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = dataSet.size

}
