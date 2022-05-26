package com.pam.usermanagement.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.pam.usermanagement.models.User

@Entity
data class DatabaseUser constructor(
    val id: Int,
    @PrimaryKey val login: String,
    @ColumnInfo(name = "avatar_url") val avatar: String,
    val html_url: String,
)

fun List<DatabaseUser>.asUserModel(): List<User> {
    return map {
        User(
            id = it.id,
            avatar = it.avatar,
            login = it.login,
            html_url = it.html_url,
        )
    }
}