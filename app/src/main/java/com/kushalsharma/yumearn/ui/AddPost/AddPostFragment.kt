package com.kushalsharma.yumearn.ui.AddPost

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.text.Layout
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.kushalsharma.yumearn.BuildConfig
import com.kushalsharma.yumearn.R
import com.kushalsharma.yumearn.daos.PostDao
import com.kushalsharma.yumearn.models.Post
import kotlinx.android.synthetic.main.fragment_add_post.*
import java.io.InputStream

class AddPostFragment : Fragment() {

    private lateinit var postDao: PostDao
    private val PICK_IMAGE_REQUEST = 71
    private var selectedImageUri: Uri? = null



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root =  inflater.inflate(R.layout.fragment_add_post, container, false)

        postDao = PostDao()

         return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        postImage.setOnClickListener {
            chooseImage()
        }

        postButton.setOnClickListener {
            uploadToFirebase(it)
//            Toast.makeText(this.context, "your post is uploaded", Toast.LENGTH_SHORT).show()
        }
    }


    private fun chooseImage() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_IMAGE_REQUEST) {
            if (resultCode == RESULT_OK) {
                selectedImageUri = data?.data
                val inputStream: InputStream? = null
                try {
                    if (BuildConfig.DEBUG && selectedImageUri == null) {
                        error("Assertion failed")
                        //
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
                BitmapFactory.decodeStream(inputStream)
                postImage.setImageURI(selectedImageUri)
            }
        }
    }
    private fun uploadToFirebase(view: View) {
        val titleInput = postTitle.text.toString().trim()
        val descripInput = postDescription.text.toString().trim()
        val quantityInput = quantity_Et.text.toString().trim()
        val imageInput = selectedImageUri

        if (titleInput.isNotEmpty() && descripInput.isNotEmpty()
            && quantityInput.isNotEmpty() && quantityInput != "0"
        ) {

//            postDao.addPost(titleInput, descripInput, quantityInput, imageInput.toString())
//            Navigation.findNavController(view).navigate(R.id.action_addPost_to_orders)

            val post = Post(titleInput,descripInput,quantityInput,"","")
            val action = AddPostFragmentDirections.actionNavigationAddPostToConfirmOrderFragment(post)
            findNavController().navigate(action)




        } else {
            Toast.makeText(this.context, "Empty Fields are not allowed", Toast.LENGTH_SHORT).show()
        }
    }


}
