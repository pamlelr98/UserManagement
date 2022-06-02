package com.pam.usermanagement.models

class User(
    val id: Int,
    val login: String,
    val avatar: String,
    val html_url: String,
    val followers:Int?,
    val name: String?,
    val location: String?,
    val blog: String?,
    val email: String?,
    val bio: String?,
    val created_at: String?,
)