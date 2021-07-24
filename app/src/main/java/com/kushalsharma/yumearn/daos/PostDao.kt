package com.kushalsharma.yumearn.daos

import kotlinx.coroutines.GlobalScope
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.android.gms.tasks.Task

import com.google.firebase.ktx.Firebase
import com.kushalsharma.yumearn.models.Post
import com.kushalsharma.yumearn.models.User


class PostDao {

    val db = FirebaseFirestore.getInstance()
    val postCollection = db.collection("Posts")
    val auth = Firebase.auth

    fun addPost(title: String, description: String, quantity: String, image : String, address : String) {
        GlobalScope.launch {
            val currentUserId = auth.currentUser!!.uid
            val userDao = UserDao()
            val user = userDao.getUserById(currentUserId).await().toObject(User::class.java)!!

            val currentTime = System.currentTimeMillis()
            val post = Post(title,description,quantity,image,address,user,currentTime,user.uid)
            postCollection.document().set(post)
        }
    }
    fun getPostById(postId: String): Task<DocumentSnapshot> {
        return postCollection.document(postId).get()
    }

}