package com.pam.usermanagement

import android.os.Build
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*


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

@RequiresApi(Build.VERSION_CODES.O)
@BindingAdapter("textFormatted")
fun formattedDateString(textView: TextView, string: String?) {
    string?.let {
        val inputFormatter =
            DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.ENGLISH)
        val outputFormatter = DateTimeFormatter.ofPattern("'Since 'MMM d, yyyy", Locale.ENGLISH)
        val date = LocalDate.parse(string, inputFormatter)
        val s = outputFormatter.format(date)
        textView.text = s
    }
}

@BindingAdapter("textFormattedString")
fun formattedString(textView: TextView, followers: Int?) {
    followers?.let {
        textView.text = String.format("%d Followers", followers)
    }
}

@BindingAdapter("textViewName")
fun formattedString(textView: TextView, string: String?) {
    if (string.isNullOrEmpty()) {
        textView.visibility = View.GONE
    } else {
        textView.visibility = View.VISIBLE
    }
}

@BindingAdapter("textViewBio")
fun textViewBio(textView: TextView, string: String?) {
    if (string.isNullOrEmpty()) {
        textView.visibility = View.GONE
    } else {
        textView.visibility = View.VISIBLE
        textView.text = string
    }
}

@BindingAdapter("textViewEmail")
fun textViewEmail(textView: TextView, string: String?) {
    if (string.isNullOrEmpty()) {
        textView.visibility = View.GONE
    } else {
        textView.visibility = View.VISIBLE
        textView.text = string
    }
}
