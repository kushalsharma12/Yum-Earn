package com.kushalsharma.yumearn.models

data class Earning (
    val userMoney : String = "",
    val userId: String = User().uid
)
