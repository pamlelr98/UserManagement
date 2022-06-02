package com.pam.usermanagement.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.pam.usermanagement.models.User

@Entity(tableName = "Users")
data class DatabaseUser constructor(
    val followers: Int?,
    val id: Int,
    @PrimaryKey val login: String,
    @ColumnInfo(name = "avatar_url") val avatar: String,
    val html_url: String,
    val name: String?,
    val location: String?,
    val blog: String?,
    val email: String?,
    val bio: String?,
    val created_at: String?,
)

fun List<DatabaseUser>.asUserModel(): List<User> {
    return map {
        User(
            id = it.id,
            avatar = it.avatar,
            login = it.login,
            html_url = it.html_url,
            bio = it.bio,
            name = it.name,
            created_at = it.created_at,
            blog = it.blog,
            email = it.email,
            followers = it.followers,
            location = it.location
        )
    }
}


fun DatabaseUser.asUserModel(): User {
    return let {
        User(
            id = it.id,
            avatar = it.avatar,
            login = it.login,
            html_url = it.html_url,
            bio = it.bio,
            name = it.name,
            created_at = it.created_at,
            blog = it.blog,
            email = it.email,
            followers = it.followers,
            location = it.location
        )
    }
}