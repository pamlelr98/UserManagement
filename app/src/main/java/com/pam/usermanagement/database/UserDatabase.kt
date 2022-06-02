package com.pam.usermanagement.database

import android.content.Context
import androidx.room.*
import androidx.room.migration.AutoMigrationSpec
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(
    entities = [DatabaseUser::class],
    version = 4,
    exportSchema = true,
    autoMigrations = [AutoMigration(
        from = 1,
        to = 2,
        spec = DeleteColumnAutoMigration::class
    ), AutoMigration(from = 2, to = 3, spec = RenameTableAutoMigration::class)]
)

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

@RenameTable(fromTableName = "DatabaseUser", toTableName = "Users")
class RenameTableAutoMigration : AutoMigrationSpec

val MIGRATION_3_4 = object : Migration(3, 4) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL("ALTER TABLE Users ADD COLUMN followers INTEGER")
        database.execSQL("ALTER TABLE Users ADD COLUMN name TEXT")
        database.execSQL("ALTER TABLE Users ADD COLUMN location TEXT")
        database.execSQL("ALTER TABLE Users ADD COLUMN blog TEXT")
        database.execSQL("ALTER TABLE Users ADD COLUMN bio TEXT")
        database.execSQL("ALTER TABLE Users ADD COLUMN email TEXT")
        database.execSQL("ALTER TABLE Users ADD COLUMN created_at TEXT")
    }
}

private lateinit var INSTANCE: UserDatabase

@Synchronized
fun getDatabase(context: Context): UserDatabase {
    if (!::INSTANCE.isInitialized) {
        INSTANCE = Room.databaseBuilder(
            context.applicationContext,
            UserDatabase::class.java, "DatabaseUser"
        ).addMigrations(MIGRATION_3_4).build()
    }
    return INSTANCE
}