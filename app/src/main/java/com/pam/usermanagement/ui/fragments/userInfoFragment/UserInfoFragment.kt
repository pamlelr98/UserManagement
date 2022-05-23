package com.pam.usermanagement.ui.fragments.userInfoFragment

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.pam.usermanagement.R
import com.pam.usermanagement.databinding.UserInfoFragmentBinding

class UserInfoFragment : Fragment() {

    private val viewModel: UserInfoViewModel by lazy {
        val application = requireNotNull(this.activity).application
        val arguments = UserInfoFragmentArgs.fromBundle(requireArguments())
        ViewModelProvider(
            this,
            UserInfoViewModel.Factory(arguments.login, application)
        )[UserInfoViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = UserInfoFragmentBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        return binding.root
    }
}