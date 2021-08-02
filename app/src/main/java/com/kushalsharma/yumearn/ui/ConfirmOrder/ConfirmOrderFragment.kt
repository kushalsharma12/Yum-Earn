package com.kushalsharma.yumearn.ui.ConfirmOrder

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.navArgs
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.kushalsharma.yumearn.MainActivity
import com.kushalsharma.yumearn.R
import com.kushalsharma.yumearn.daos.PostDao
import kotlinx.android.synthetic.main.fragment_confirm_order.*
import kotlinx.android.synthetic.main.fragment_confirm_order.view.*

class ConfirmOrderFragment : BottomSheetDialogFragment() {

    private lateinit var postDao: PostDao
    private val args by navArgs<ConfirmOrderFragmentArgs>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_confirm_order, container, false)

        postDao = PostDao()

        view.title_confirm.text = args.currentPost.title
        view.description_confirm.text = args.currentPost.description
        view.quantity_confirm.text = "Quantity: ${args.currentPost.quantity} kg"

        view.postButton_confirm.setOnClickListener {

            upload(it)

        }
        view.cancelButton_confirm.setOnClickListener {
            dismiss()
        }



        return view
    }

    private fun upload(view: View) {
        val titleInput = title_confirm.text.toString().trim()
        val descripInput = description_confirm.text.toString().trim()
        val quantityInput = quantity_confirm.text.toString().trim()
        val addressInput = address_ET.text.toString().trim()

        if (addressInput.isNotEmpty()) {

            postDao.addPost(titleInput, descripInput, quantityInput, "", addressInput)
            val i = Intent(this.context, MainActivity::class.java)
            startActivity(i)
            Toast.makeText(this.context, "Successful", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this.context, "Please enter your address", Toast.LENGTH_SHORT).show()
        }


    }


}