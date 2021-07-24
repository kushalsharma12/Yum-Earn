package com.kushalsharma.yumearn.models

import android.net.Uri
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.net.URI

@Parcelize
data class Post (
    val title: String = "",
    val description : String = "",
    val quantity : String = "",
    val imageUrl : String ="",
    val address : String = "",
    val createdBy: User = User(),
    val createdAt: Long = 0L,
    val userId: String = User().uid,

    ) : Parcelable
