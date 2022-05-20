package com.pam.usermanagement.database

import androidx.annotation.ColorInt
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
    val url: String
)

fun List<DatabaseUser>.asUserModel(): List<User> {
    return map {
        User(
            id = it.id,
            avatar = it.avatar,
            login = it.login,
            events_url = it.events_url,
            followers_url = it.followers_url,
            following_url = it.following_url,
            gists_url = it.gists_url,
            gravatar_id = it.gravatar_id,
            html_url = it.html_url,
            node_id = it.node_id,
            organizations_url = it.organizations_url,
            received_events_url = it.received_events_url,
            repos_url = it.repos_url,
            site_admin = it.site_admin,
            starred_url = it.starred_url,
            subscriptions_url = it.subscriptions_url,
            type = it.type,
            url = it.url
        )
    }
}