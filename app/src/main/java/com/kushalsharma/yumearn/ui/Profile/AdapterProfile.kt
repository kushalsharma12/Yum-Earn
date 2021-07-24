package com.kushalsharma.yumearn.ui.Profile

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.kushalsharma.yumearn.R
import com.kushalsharma.yumearn.models.Earning

class AdapterProfile(options: FirestoreRecyclerOptions<Earning>) : FirestoreRecyclerAdapter<Earning, AdapterProfile.profileViewHolder>(
    options
) {
    class profileViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val eText : TextView = itemView.findViewById(R.id.earn_Tv)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): profileViewHolder {

        val viewHolder = profileViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.earning_item, parent, false)
        )

        return viewHolder

    }

    override fun onBindViewHolder(holder: profileViewHolder, position: Int, model: Earning) {
        holder.eText.text = model.userMoney
    }
}