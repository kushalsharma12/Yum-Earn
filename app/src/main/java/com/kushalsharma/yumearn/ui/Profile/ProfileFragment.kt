package com.kushalsharma.yumearn.ui.Profile

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.AdapterView
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.kushalsharma.yumearn.LoginActivity
import com.kushalsharma.yumearn.R
import com.kushalsharma.yumearn.models.Earning
import com.razorpay.Checkout
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.android.synthetic.main.fragment_profile.view.*
import org.json.JSONException
import org.json.JSONObject


 class ProfileFragment : Fragment() {

    private lateinit var auth: FirebaseAuth
    val dbp = FirebaseFirestore.getInstance()
    private lateinit var adapter: AdapterProfile

    private var recylerview : RecyclerView? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_profile, container, false)

        (activity as AppCompatActivity).supportActionBar?.hide()

        auth = Firebase.auth
        val currentUser = auth.currentUser

        Glide.with(root.userImg!!)
            .load(currentUser!!.photoUrl)
            .transform(CenterCrop(), RoundedCorners(36))
            .into(root.userImg!!)

        recylerview = root.findViewById(R.id.rv_profile) as RecyclerView?

        root.tv_LogOut.setOnClickListener {
            val dialogBuilder = AlertDialog.Builder(this.requireContext())


            dialogBuilder.setMessage("Are you sure?")

                .setCancelable(false)

                .setPositiveButton("Proceed", DialogInterface.OnClickListener { dialog, id ->
                    Firebase.auth.signOut()
                    val Intent = Intent(activity, LoginActivity::class.java)
                    startActivity(Intent)
                    requireActivity().finish()


                })
                // negative button text and action
                .setNegativeButton("Cancel", DialogInterface.OnClickListener { dialog, id ->
                    dialog.cancel()
                })


            val alert = dialogBuilder.create()

            alert.setTitle("Sign Out")

            alert.show()

        }


        root.user_Name.text = currentUser.displayName!!.toString()
        root.user_Email.text = currentUser.email!!.toString()
        root.phNo.text = currentUser.phoneNumber!!.toString()

        setupRecycle()

        return root
    }

    private fun setupRecycle() {



        val query = dbp.collection("userEarning")
//            .whereEqualTo("userId", currentUser!!.uid)
            .orderBy("userMoney").limit(100)

        val options = FirestoreRecyclerOptions.Builder<Earning>()
            .setQuery(query, Earning::class.java)

            .setLifecycleOwner(this).build()

        adapter = AdapterProfile(options)
        recylerview!!.adapter = adapter
        recylerview!!.layoutManager = LinearLayoutManager(this.context)


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
