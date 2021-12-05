package com.example.restaurantratingapp

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView


class RestaurantAdapter(private val resImagesList: Array<Int> , private val resNamesList: Array<String>,
                        private val activity:HomePage ) :
    RecyclerView.Adapter<RestaurantAdapter.ViewHolder>() {



    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    class ViewHolder(view: View , activity: HomePage , resNamesList: Array<String>) : RecyclerView.ViewHolder(view) {
        val resImage: ImageView
        val resName: TextView

        init {
            // Define click listener for the ViewHolder's View.
            resImage = view.findViewById(R.id.resImage)
            resName = view.findViewById(R.id.resName)

            view.setOnClickListener {
                Toast.makeText(view.context, "Hello " + resNamesList[adapterPosition] , Toast.LENGTH_SHORT).show()
                val intent = Intent(view.context, RestaurantSpecificPage::class.java)
                intent.putExtra("resName",resNamesList[adapterPosition])
                activity.startActivity(intent)
            }
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.restaurant_item, viewGroup, false)

        return ViewHolder(view,this.activity,resNamesList)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        viewHolder.resName.text = resNamesList[position]
        viewHolder.resImage.setImageResource(resImagesList[position])
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = resImagesList.size

}
