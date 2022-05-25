package com.pam.usermanagement.ui.activities

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import com.pam.usermanagement.R
import com.pam.usermanagement.ui.fragments.usersFragment.UsersFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onBackPressed() {
        try {
            var handled = false // 1
            supportFragmentManager.fragments.forEach { fragment ->
                if (fragment is NavHostFragment) {
                    fragment.childFragmentManager.fragments.forEach { childFragment -> //2
                        if (childFragment is UsersFragment) { // 3
                            handled = childFragment.onBackPressed() // 4
                            if (handled) { //5
                                return
                            }
                        }
                    }
                }
            }

            if (!handled) { //6
                super.onBackPressed()
            }
        } catch (e: Exception) {
            super.onBackPressed()
        }
    }

}


