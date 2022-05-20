package com.pam.usermanagement.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.pam.usermanagement.R
import com.pam.usermanagement.database.UserDatabase
import com.pam.usermanagement.database.getDatabase
import com.pam.usermanagement.network.NetworkUser
import com.pam.usermanagement.network.RetrofitClient
import com.pam.usermanagement.network.asUserDatabase
import kotlinx.coroutines.runBlocking
import java.lang.Exception

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val database: UserDatabase = getDatabase(this.application)
        runBlocking {
            try {
                Toast.makeText(applicationContext, "success", Toast.LENGTH_SHORT).show()
                val users = RetrofitClient.getInstance().getUsers().asUserDatabase()
                database.userDao.insertAll(users)

                Log.d("getApi", database.userDao.getUser("nitay").toString())
            } catch (err: Exception) {
                Toast.makeText(applicationContext, err.toString(), Toast.LENGTH_SHORT).show()
                Log.d("getAPI", err.message.toString())
            }
        }
    }
}


