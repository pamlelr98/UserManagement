package com.pam.usermanagement.ui.fragments.usersFragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.pam.usermanagement.database.UserDatabase
import com.pam.usermanagement.database.getDatabase
import com.pam.usermanagement.databinding.UsersFragmentBinding
import com.pam.usermanagement.repository.UserRepository
import com.pam.usermanagement.ui.fragments.userInfoFragment.UsersAdapter

class UsersFragment : Fragment() {

    private lateinit var binding: UsersFragmentBinding
    private lateinit var viewModel: UsersViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = UsersFragmentBinding.inflate(inflater)

        val application = requireNotNull(this.activity).application
        val database: UserDatabase = getDatabase(application)
        val userAdapter: UsersAdapter? = null
        val userRepository = UserRepository(database)
        userRepository.videos.observe(viewLifecycleOwner, Observer {
            userAdapter?.users = it
        })

        return binding.root
    }

}