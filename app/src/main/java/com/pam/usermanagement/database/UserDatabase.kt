package com.pam.usermanagement.database

import android.content.Context
import androidx.room.*
import androidx.room.migration.AutoMigrationSpec

@Database(
    entities = [DatabaseUser::class],
    version = 2,
    exportSchema = true,
    autoMigrations = [AutoMigration(
        from = 1,
        to = 2,
    )]
)
abstract class UserDatabase : RoomDatabase() {

//    @DeleteColumn(tableName = "databaseuser", columnName = "subscriptions_url")
//    @DeleteColumn(tableName = "databaseuser", columnName = "type")
//    @DeleteColumn(tableName = "databaseuser", columnName = "followers_url")
//    @DeleteColumn(tableName = "databaseuser", columnName = "gists_url")
//    @DeleteColumn(tableName = "databaseuser", columnName = "following_url")
//    @DeleteColumn(tableName = "databaseuser", columnName = "gravatar_id")
//    @DeleteColumn(tableName = "databaseuser", columnName = "node_id")
//    @DeleteColumn(tableName = "databaseuser", columnName = "organizations_url")
//    @DeleteColumn(tableName = "databaseuser", columnName = "received_events_url")
//    @DeleteColumn(tableName = "databaseuser", columnName = "repos_url")
//    @DeleteColumn(tableName = "databaseuser", columnName = "site_admin")
    @DeleteColumn(tableName = "databaseuser", columnName = "url")
    class DeleteColumnAutoMigration : AutoMigrationSpec

    abstract val userDao: UserDAO
}

private lateinit var INSTANCE: UserDatabase

@Synchronized
fun getDatabase(context: Context): UserDatabase {
    if (!::INSTANCE.isInitialized) {
        INSTANCE = Room.databaseBuilder(
            context.applicationContext,
            UserDatabase::class.java, "databaseuser"
        ).build()
    }
    return INSTANCE
}