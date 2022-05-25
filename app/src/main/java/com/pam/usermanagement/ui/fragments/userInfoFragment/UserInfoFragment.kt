package com.pam.usermanagement.ui.fragments.userInfoFragment

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.pam.usermanagement.R
import com.pam.usermanagement.databinding.UserInfoFragmentBinding

class UserInfoFragment : Fragment() {

    private lateinit var binding: UserInfoFragmentBinding

    private val viewModel: UserInfoViewModel by lazy {
        val application = requireNotNull(this.activity).application
        val arguments = UserInfoFragmentArgs.fromBundle(requireArguments())
        ViewModelProvider(
            this,
            UserInfoViewModel.Factory(arguments.login, arguments.avatarUrl, application)
        )[UserInfoViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = UserInfoFragmentBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        init()
        return binding.root
    }

    private fun init() {
        setListeners()
    }

    @SuppressLint("QueryPermissionsNeeded")
    private fun setListeners() {
        binding.backTextview.setOnClickListener {
            findNavController().popBackStack(R.id.usersFragment, false)
        }

        binding.blogTextview.setOnClickListener {
            val packageManager = context?.packageManager ?: return@setOnClickListener
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse((it as TextView).text.toString()))
            if (intent.resolveActivity(packageManager) == null) {
                startActivity(intent)
            }
        }

        binding.emailTextview.setOnClickListener {
            val packageManager = context?.packageManager ?: return@setOnClickListener
            val intent = Intent(Intent.ACTION_SENDTO)
            intent.data = Uri.parse("mailto: ${(it as TextView).text}")
            intent.putExtra(Intent.EXTRA_SUBJECT, "send email from my application");
            //intent.type = "message/rfc822"
            if (intent.resolveActivity(packageManager) == null) {
                startActivity(intent)
            }
        }
    }
}