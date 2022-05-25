package com.pam.usermanagement.ui.fragments.usersFragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.pam.usermanagement.databinding.ItemUserBinding
import com.pam.usermanagement.models.User

class UsersAdapter(private val clickListener: UserListener) :
    ListAdapter<User, UsersAdapter.UserViewHolder>(UserDiffCallBack()) {

    class UserViewHolder(private val binding: ItemUserBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(user: User, clickListener: UserListener) {
            binding.user = user
            binding.clickListener = clickListener
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): UserViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemUserBinding.inflate(layoutInflater, parent, false)
                return UserViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        return UserViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, clickListener)
    }
}

class UserDiffCallBack : DiffUtil.ItemCallback<User>() {
    override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
        return oldItem.login == newItem.login
    }

    override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
        return oldItem.id == newItem.id && oldItem.login == newItem.login
    }
}

class UserListener(
    val clickListener: (login: String, avatar: String) -> Unit,
    val clickUrl: (url: String) -> Unit
) {
    fun onClick(user: User) = clickListener(user.login, user.avatar)
    fun onClickUrl(user: User) = clickUrl(user.url)
}