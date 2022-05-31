package com.brainer.mvvmcoroutines.model

class User() : ArrayList<UserItem>()

data class UserItem(
    val body: String,
    val id: Int,
    val title: String,
    val user_id: Int
)