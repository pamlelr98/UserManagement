package com.pam.usermanagement.network

import com.pam.usermanagement.database.DatabaseUser
import com.pam.usermanagement.database.UserDatabase
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class NetworkUser(
    @Json(name = "avatar_url") val avatar_url: String,
    val events_url: String,
    val followers_url: String,
    val following_url: String,
    val gists_url: String,
    val gravatar_id: String,
    val html_url: String,
    val id: Int,
    val login: String,
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

@JsonClass(generateAdapter = true)
data class NetworkUserInfo(
    val avatar_url: String,
    val bio: String?,
    val blog: String,
    val company: String?,
    val created_at: String,
    val email: String?,
    val events_url: String,
    val followers: Int,
    val followers_url: String,
    val following: Int,
    val following_url: String,
    val gists_url: String,
    val gravatar_id: String,
    val hireable: Boolean?,
    val html_url: String,
    val id: Int,
    val location: String?,
    val login: String,
    val name: String?,
    val node_id: String,
    val organizations_url: String,
    val public_gists: Int,
    val public_repos: Int,
    val received_events_url: String,
    val repos_url: String,
    val site_admin: Boolean,
    val starred_url: String,
    val subscriptions_url: String,
    val twitter_username: String?,
    val type: String,
    val updated_at: String,
    val url: String
)

fun List<NetworkUser>.asUserDatabase(): List<DatabaseUser> {
    return map {
        DatabaseUser(
            id = it.id,
            avatar = it.avatar_url,
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
