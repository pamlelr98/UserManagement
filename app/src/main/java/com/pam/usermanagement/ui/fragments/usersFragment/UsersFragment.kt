package com.pam.usermanagement.ui.fragments.usersFragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.pam.usermanagement.R
import com.pam.usermanagement.database.UserDatabase
import com.pam.usermanagement.database.getDatabase
import com.pam.usermanagement.databinding.UsersFragmentBinding
import com.pam.usermanagement.models.User
import com.pam.usermanagement.repository.UserRepository
import kotlinx.coroutines.runBlocking

class UsersFragment : Fragment() {

    private lateinit var binding: UsersFragmentBinding
    private val viewModel: UsersViewModel by lazy {
        val activity = requireNotNull(this.activity) {
            "You can only access the viewModel after onActivityCreate()"
        }
        ViewModelProvider(
            this,
            UsersViewModel.Factory(activity.application)
        )[UsersViewModel::class.java]
    }

    private var userAdapter: UsersAdapter? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.users.observe(viewLifecycleOwner, Observer<List<User>> { users ->
            users?.apply {
                userAdapter?.submitList(users)
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = UsersFragmentBinding.inflate(inflater)

        binding.userViewModel = viewModel
        val application = requireNotNull(this.activity).application
        val database: UserDatabase = getDatabase(application)
        userAdapter = UsersAdapter()
        binding.recycleViewUsers.adapter = userAdapter

//        viewModel.users.observe(viewLifecycleOwner, Observer {
//            userAdapter.submitList(it)
//            Snackbar.make(
//                requireActivity().findViewById(android.R.id.content),
//                getString(R.string.online),
//                Snackbar.LENGTH_SHORT // How long to display the message.
//            ).show()
//        })

        return binding.root
    }

}