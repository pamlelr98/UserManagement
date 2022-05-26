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
    val events_url: String,
    val followers_url: String,
    val following_url: String,
    val gists_url: String,
    val gravatar_id: String,
    val html_url: String,
    val node_id: String,
    val organizations_url: String,
    val received_events_url: String,
    val repos_url: String,
    val site_admin: Boolean,
    val starred_url: String,
    val subscriptions_url: String,
    val type: String,
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