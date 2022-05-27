package com.pam.usermanagement.ui.fragments

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.pam.usermanagement.R
import com.pam.usermanagement.adapters.UserListener
import com.pam.usermanagement.adapters.UsersAdapter
import com.pam.usermanagement.databinding.UsersFragmentBinding
import com.pam.usermanagement.helper.IOnBackPressed
import com.pam.usermanagement.helper.LoadingDialog
import com.pam.usermanagement.viewmodels.UsersApiStatus
import com.pam.usermanagement.viewmodels.UsersViewModel

class UsersFragment : Fragment(), IOnBackPressed {

    private lateinit var binding: UsersFragmentBinding
    private lateinit var loadingDialog: LoadingDialog
    private var doubleBackToExitPressedOnce: Boolean = false

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
        init()
        return binding.root
    }


    @SuppressLint("QueryPermissionsNeeded")
    private fun init() {
        binding.userViewModel = viewModel

        loadingDialog = LoadingDialog().newInstance()

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

        setListeners()
        observe()
        setHasOptionsMenu(true)
    }

    private fun setListeners() {
        binding.containerSwipe.setOnRefreshListener {
            viewModel.eventNetWorkError.value?.let {
                if (!it) {
                    viewModel.refreshDataFromRepository()
                }
            }
            binding.containerSwipe.isRefreshing = false
        }
    }

    private fun observe() {
        viewModel.status.observe(viewLifecycleOwner, Observer {
            when (it) {
                UsersApiStatus.DONE -> dismissDialog()
                UsersApiStatus.LOADING -> showDialog()
                else -> {
                    dismissDialog()
                }
            }
        })

        viewModel.isNetWorkErrorShown.observe(viewLifecycleOwner, Observer {
            if (it) {
                Snackbar.make(
                    requireActivity().findViewById(android.R.id.content),
                    getString(R.string.offline),
                    Snackbar.LENGTH_LONG
                ).show()
                viewModel.onNetworkErrorShown()
            }
        })
    }

    private fun showDialog() {
        val ft: FragmentTransaction = this.parentFragmentManager.beginTransaction()
        val prev = this.parentFragmentManager.findFragmentByTag(LoadingDialog.TAG)
        if (prev != null) {
            ft.remove(prev)
        }
        ft.addToBackStack(null)
        loadingDialog.show(ft, LoadingDialog.TAG)
    }

    private fun dismissDialog() {
        val prev = this.parentFragmentManager.findFragmentByTag(LoadingDialog.TAG)
        if (prev != null) {
            loadingDialog.dismiss()
        }
    }

    override fun onBackPressed(): Boolean {
        if (doubleBackToExitPressedOnce) {
            return false
        }
        doubleBackToExitPressedOnce = true
        Toast.makeText(context, getString(R.string.exit_msg), Toast.LENGTH_SHORT)
            .show()
        Handler(Looper.getMainLooper()).postDelayed(Runnable {
            doubleBackToExitPressedOnce = false
        }, 2000)
        return true
    }
}