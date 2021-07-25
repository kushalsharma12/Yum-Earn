package com.kushalsharma.yumearn.ui.Orders

import android.app.ActionBar
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.kushalsharma.yumearn.R
import com.kushalsharma.yumearn.models.Post
import kotlinx.android.synthetic.main.fragment_orders.*


class OrdersFragment : Fragment() {


    private val db = FirebaseFirestore.getInstance()
    private lateinit var adapter: OrdersAdapter
    private lateinit var auth: FirebaseAuth


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_orders, container, false)

        (activity as AppCompatActivity).supportActionBar?.hide()

        auth = Firebase.auth


        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupRecyclerView()

    }



    private fun setupRecyclerView() {


        val currentUser = auth.currentUser


        val query = db.collection("Posts")
//            .whereEqualTo("userId", currentUser!!.uid)
            .orderBy("createdAt").limit(100)


        val options = FirestoreRecyclerOptions.Builder<Post>()
            .setQuery(query, Post::class.java)
//            .setLifecycleOwner(this)
            .build()



        adapter = OrdersAdapter(options)

        adapter.notifyDataSetChanged()

        order_rv.adapter = adapter


    }

    override fun onStart() {
        super.onStart()

        adapter.startListening()

    }


    override fun onStop() {
        super.onStop()
        adapter.stopListening()
    }



}
