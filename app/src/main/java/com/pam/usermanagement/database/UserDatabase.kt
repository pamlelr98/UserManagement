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
        spec = DeleteColumnAutoMigration::class
    )]
)
//, AutoMigration(from = 2, to = 3, spec = RenameTableAutoMigration::class)

abstract class UserDatabase : RoomDatabase() {
    abstract val userDao: UserDAO
}

@DeleteColumn(tableName = "DatabaseUser", columnName = "events_url")
@DeleteColumn(tableName = "DatabaseUser", columnName = "followers_url")
@DeleteColumn(tableName = "DatabaseUser", columnName = "following_url")
@DeleteColumn(tableName = "DatabaseUser", columnName = "gists_url")
@DeleteColumn(tableName = "DatabaseUser", columnName = "gravatar_id")
@DeleteColumn(tableName = "DatabaseUser", columnName = "node_id")
@DeleteColumn(tableName = "DatabaseUser", columnName = "organizations_url")
@DeleteColumn(tableName = "DatabaseUser", columnName = "received_events_url")
@DeleteColumn(tableName = "DatabaseUser", columnName = "repos_url")
@DeleteColumn(tableName = "DatabaseUser", columnName = "site_admin")
@DeleteColumn(tableName = "DatabaseUser", columnName = "starred_url")
@DeleteColumn(tableName = "DatabaseUser", columnName = "subscriptions_url")
@DeleteColumn(tableName = "DatabaseUser", columnName = "type")
@DeleteColumn(tableName = "DatabaseUser", columnName = "url")
class DeleteColumnAutoMigration : AutoMigrationSpec

//@RenameTable(fromTableName = "DatabaseUser", toTableName = "Users")
//class RenameTableAutoMigration : AutoMigrationSpec

private lateinit var INSTANCE: UserDatabase

@Synchronized
fun getDatabase(context: Context): UserDatabase {
    if (!::INSTANCE.isInitialized) {
        INSTANCE = Room.databaseBuilder(
            context.applicationContext,
            UserDatabase::class.java, "DatabaseUser"
        ).build()
    }
    return INSTANCE
}