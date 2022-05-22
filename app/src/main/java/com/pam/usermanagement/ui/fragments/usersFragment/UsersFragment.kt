package com.pam.usermanagement.ui.fragments.usersFragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.google.android.material.snackbar.Snackbar
import com.pam.usermanagement.R
import com.pam.usermanagement.database.UserDatabase
import com.pam.usermanagement.database.getDatabase
import com.pam.usermanagement.databinding.UsersFragmentBinding
import com.pam.usermanagement.repository.UserRepository
import com.pam.usermanagement.ui.fragments.userInfoFragment.UsersAdapter
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

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
        val userAdapter = UsersAdapter()
        binding.recycleViewUsers.adapter = userAdapter

        val userRepository = UserRepository(database)
        userRepository.videos.observe(viewLifecycleOwner, Observer {
            userAdapter.submitList(it)
            Snackbar.make(
                requireActivity().findViewById(android.R.id.content),
                getString(R.string.online),
                Snackbar.LENGTH_SHORT // How long to display the message.
            ).show()
        })

        runBlocking {
            userRepository.refreshUsers()
        }

        return binding.root
    }

}