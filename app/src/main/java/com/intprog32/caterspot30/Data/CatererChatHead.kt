package com.intprog32.caterspot30.Data

data class CatererChatHead(
    val catererId: String, // Unique ID for the caterer
    val catererName: String,
    val profileImageResId: Int, // Resource ID for the caterer's profile image
    val lastMessage: String = ""
)