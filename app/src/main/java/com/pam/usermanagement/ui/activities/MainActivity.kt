package com.pam.usermanagement.ui.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import com.pam.usermanagement.R
import com.pam.usermanagement.ui.fragments.UsersFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onBackPressed() {
        try {
            var handled = false
            supportFragmentManager.fragments.forEach { fragment ->
                if (fragment is NavHostFragment) {
                    fragment.childFragmentManager.fragments.forEach { childFragment ->
                        if (childFragment is UsersFragment) {
                            handled = childFragment.onBackPressed()
                            if (handled) {
                                return
                            }
                        }
                    }
                }
            }

            if (!handled) {
                super.onBackPressed()
            }
        } catch (e: Exception) {
            super.onBackPressed()
        }
    }

}


