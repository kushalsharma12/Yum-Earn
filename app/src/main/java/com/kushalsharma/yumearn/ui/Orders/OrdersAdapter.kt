package com.kushalsharma.yumearn.ui.Orders

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.kushalsharma.yumearn.R
import com.kushalsharma.yumearn.Utils
import com.kushalsharma.yumearn.models.Post

class OrdersAdapter(options: FirestoreRecyclerOptions<Post>) : FirestoreRecyclerAdapter<Post, OrdersAdapter.orderViewHolder>(
    options
) {
    class orderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var oTitle: TextView = itemView.findViewById(R.id.title_order)
        var oDescription: TextView = itemView.findViewById(R.id.description_order)
        var oQty: TextView = itemView.findViewById(R.id.quantity_order)
        var oImg: ImageView = itemView.findViewById(R.id.image_order)
        val createdAt: TextView = itemView.findViewById(R.id.time_order)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): orderViewHolder {
        val viewHolder = orderViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.items_orders, parent, false)
        )

        return viewHolder
    }

    override fun onBindViewHolder(holder: orderViewHolder, position: Int, model: Post) {
        holder.oTitle.text = model.title
        holder.oDescription.text = model.description
        holder.oQty.text = model.quantity
        holder.createdAt.text = Utils.getTimeAgo(model.createdAt)
                Glide.with(holder.oImg.context).load(model.imageUrl)
            .transform(CenterCrop(), RoundedCorners(40))
            .into(holder.oImg)


    }
}