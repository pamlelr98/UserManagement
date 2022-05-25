package com.pam.usermanagement.ui.fragments.usersFragment

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.pam.usermanagement.R
import com.pam.usermanagement.databinding.UsersFragmentBinding
import com.pam.usermanagement.ui.LoadingDialog

class UsersFragment : Fragment() {

    private var doubleBackToExitPressedOnce = false
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

    @SuppressLint("QueryPermissionsNeeded")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = UsersFragmentBinding.inflate(inflater)
        binding.userViewModel = viewModel

        userAdapter = UsersAdapter(UserListener(clickListener = { login, avatar ->
            findNavController().navigate(
                UsersFragmentDirections.actionUsersFragmentToUserInfoFragment(
                    login,
                    avatar
                )
            )

        }, clickUrl = { url ->
            val packageManager = context?.packageManager ?: return@UserListener
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            if (intent.resolveActivity(packageManager) == null) {
                startActivity(intent)
            }
        }))
        binding.recycleViewUsers.adapter = userAdapter

        viewModel.eventNetWorkError.observe(viewLifecycleOwner, Observer {
            if (it) {
                Snackbar.make(
                    requireActivity().findViewById(android.R.id.content),
                    getString(R.string.offline),
                    Snackbar.LENGTH_LONG
                ).show()
                viewModel.onNetworkErrorShown()
            }
        })
        init()
        setHasOptionsMenu(true)
        return binding.root
    }

    private var isLargeLayout: Boolean = false

    private fun init() {
        isLargeLayout = resources.getBoolean(R.bool.large_layout)
        setListeners()
    }

    private fun setListeners() {
        binding.containerSwipe.setOnRefreshListener {
            viewModel.refreshDataFromRepository()
            LoadingDialog().show(
                childFragmentManager, LoadingDialog.TAG)

            binding.containerSwipe.isRefreshing = false
        }
    }


}