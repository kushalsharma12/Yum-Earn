package com.kushalsharma.yumearn.ui.Profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.kushalsharma.yumearn.R
import com.kushalsharma.yumearn.models.Earning
import kotlinx.android.synthetic.main.fragment_profile.*


class ProfileFragment : Fragment() {

    private lateinit var auth: FirebaseAuth
    val dbp = FirebaseFirestore.getInstance()
    private lateinit var adapter: AdapterProfile


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_profile, container, false)
        auth = Firebase.auth

        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val currentUser = auth.currentUser!!
        user_Name.text = currentUser.displayName!!.toString()
        user_Email.text = currentUser.email!!.toString()
        phNo.text = currentUser.phoneNumber!!.toString()
        Glide.with(userImg)
            .load(currentUser.photoUrl).circleCrop().into(userImg)

        setupRecycle()
    }

    private fun setupRecycle() {

        val currentUser = auth.currentUser


        val query = dbp.collection("userEarning")
//            .whereEqualTo("userId", currentUser!!.uid)
            .orderBy("userMoney").limit(100)

        val options = FirestoreRecyclerOptions.Builder<Earning>()
            .setQuery(query, Earning::class.java)

            .setLifecycleOwner(this).build()

        adapter = AdapterProfile(options)
        rv_profile.adapter = adapter
        rv_profile.layoutManager = LinearLayoutManager(this.context)


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
