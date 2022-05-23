package com.pam.usermanagement

import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions


@BindingAdapter("imageUrl")
fun bindImage(imageView: ImageView, imgUrl: String?) {
    imgUrl?.let {
        val imageUri = imgUrl.toUri().buildUpon().scheme("https").build()
        Glide.with(imageView.context).load(imageUri).apply(
            RequestOptions()
                .error(R.drawable.ic_broken_image)
        ).transition(DrawableTransitionOptions.withCrossFade()).into(imageView)
    }
}

