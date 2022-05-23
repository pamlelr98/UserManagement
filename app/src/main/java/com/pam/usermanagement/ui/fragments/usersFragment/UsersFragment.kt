package com.pam.usermanagement.ui.fragments.usersFragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.pam.usermanagement.R
import com.pam.usermanagement.databinding.UsersFragmentBinding

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
        viewModel.users.observe(viewLifecycleOwner, Observer { users ->
            users?.apply {
                userAdapter?.submitList(users)
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = UsersFragmentBinding.inflate(inflater)
        binding.userViewModel = viewModel

        userAdapter = UsersAdapter(UserListener { login ->
            Toast.makeText(context, login, Toast.LENGTH_SHORT).show()
            findNavController().navigate(UsersFragmentDirections.actionUsersFragmentToUserInfoFragment(login))
        })
        binding.recycleViewUsers.adapter = userAdapter

        viewModel.eventNetWorkError.observe(viewLifecycleOwner, Observer {
            if (it) {
                Snackbar.make(
                    requireActivity().findViewById(android.R.id.content),
                    getString(R.string.online),
                    Snackbar.LENGTH_LONG
                ).show()
                viewModel.onNetworkErrorShown()
            }
        })
        setHasOptionsMenu(true)
        return binding.root
    }

}