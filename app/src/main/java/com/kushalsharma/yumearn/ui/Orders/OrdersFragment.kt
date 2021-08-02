package com.kushalsharma.yumearn.ui.Orders

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.airbnb.lottie.LottieAnimationView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.kushalsharma.yumearn.R
import com.kushalsharma.yumearn.models.Post
import kotlinx.android.synthetic.main.fragment_orders.*
import kotlinx.android.synthetic.main.fragment_orders.view.*


class OrdersFragment : Fragment(), iPostAdapter {


    private val db = FirebaseFirestore.getInstance()
    private lateinit var adapter: OrdersAdapter
    private lateinit var auth: FirebaseAuth
    private lateinit var animView: LottieAnimationView
    private lateinit var textBelow: TextView
    private lateinit var btn_addHere: Button


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_orders, container, false)
        auth = Firebase.auth

        (activity as AppCompatActivity).supportActionBar?.hide()

        val currentUser = auth.currentUser

        animView = root.findViewById(R.id.animationView) as LottieAnimationView
        btn_addHere = root.findViewById(R.id.order_btn_add_here) as Button
        textBelow = root.findViewById(R.id.tv_below_anim) as TextView


        Glide.with(root.order_profilePic!!)
            .load(currentUser!!.photoUrl)
            .transform(CenterCrop(), RoundedCorners(36))
            .into(root.order_profilePic!!)

        root.order_profilePic.setOnClickListener {
            Navigation.findNavController(it)
                .navigate(R.id.action_navigation_orders_to_navigation_profile)
        }

        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setupRecyclerView()

    }


    private fun setupRecyclerView() {

        auth = Firebase.auth

        val currentUser = auth.currentUser


        val query = db.collection("Posts")
//            .whereEqualTo("userId", currentUser!!.uid)
            .orderBy("createdAt").limit(100)


        val options = FirestoreRecyclerOptions.Builder<Post>()
            .setQuery(query, Post::class.java)
//            .setLifecycleOwner(this)
            .build()



        adapter = OrdersAdapter(options, this)
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

    override fun onItemCount(count: Int) {
        if (count == 0) {
            animView.visibility = View.VISIBLE
            btn_addHere.visibility = View.VISIBLE
            textBelow.visibility = View.VISIBLE
            animView.playAnimation()

        } else {
            animView.visibility = View.INVISIBLE
            btn_addHere.visibility = View.INVISIBLE
            textBelow.visibility = View.INVISIBLE

            animView.pauseAnimation()
        }

    }


}
