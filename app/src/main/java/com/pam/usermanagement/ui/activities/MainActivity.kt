package com.pam.usermanagement.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import com.pam.usermanagement.R
import com.pam.usermanagement.database.UserDatabase
import com.pam.usermanagement.database.getDatabase
import com.pam.usermanagement.repository.UserRepository
import kotlinx.coroutines.runBlocking

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val database: UserDatabase = getDatabase(this.application)

        val userRepository = UserRepository(database)

        runBlocking {
            userRepository.videos.observeForever {
                Log.d("getAPI", it.toString())
            }
        }


//        runBlocking {
//            try {
//                Toast.makeText(applicationContext, "success", Toast.LENGTH_SHORT).show()
//                val users = RetrofitClient.getInstance().getUsers().asUserDatabase()
//                database.userDao.insertAll(users)
//
//                val user = database.userDao.getUser("nitay")
//                Log.d("getApi", user.toString())
//
//                Glide.with(applicationContext).load(user.avatar).into(img_avatar)
//
//            } catch (err: Exception) {
//                Toast.makeText(applicationContext, err.toString(), Toast.LENGTH_SHORT).show()
//                Log.d("getAPI", err.message.toString())
//            }
//        }
    }
}


