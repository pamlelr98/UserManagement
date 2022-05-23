package com.pam.usermanagement.ui.fragments.usersFragment

import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions.bitmapTransform
import com.pam.usermanagement.R
import com.pam.usermanagement.databinding.ItemUserBinding
import com.pam.usermanagement.models.User
import jp.wasabeef.glide.transformations.BlurTransformation
import jp.wasabeef.glide.transformations.RoundedCornersTransformation


class UsersAdapter(private val clickListener: UserListener) :
    ListAdapter<User, UsersAdapter.UserViewHolder>(UserDiffCallBack()) {

    class UserViewHolder(private val binding: ItemUserBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(user: User, clickListener: UserListener) {
            val radius = binding.avatarImage.resources.getDimensionPixelSize(R.dimen.radius)
            val multi = MultiTransformation<Bitmap>(
                CenterCrop(),
                RoundedCornersTransformation(radius, 0, RoundedCornersTransformation.CornerType.TOP)
            )
            Glide.with(binding.avatarImage.context)
                .load(user.avatar)
                .apply(bitmapTransform(multi))
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(binding.avatarImage)
            binding.login.text = user.login
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

class UserListener(val clickListener: (login: String) -> Unit) {
    fun onClick(user: User) = clickListener(user.login)
}