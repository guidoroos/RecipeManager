package com.guidoroos.recepten.util

import android.graphics.drawable.Drawable
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.guidoroos.recepten.R


@BindingAdapter("imageUrl")
fun loadImage(
    view: ImageView,
    url: String
) { // This methods should not have any return type, = declaration would make it return that object declaration.
    Glide.with(view.context)
        .load(url)
        .placeholder(R.drawable.ic_baseline_local_dining_24)
        .circleCrop()
        .into(view)
}

@BindingAdapter("imageResource")
fun imageResourceOrPlaceholder(
    view: ImageView,
    @DrawableRes imageUri: String?
) {
    when (imageUri) {
        null -> {
            view.setImageResource(R.drawable.ic_baseline_local_dining_24)
        }
        "example_pasta" -> {
            val drawable = AppCompatResources.getDrawable(view.context, R.drawable.pasta)
            view.setImageDrawable(drawable)
        }
        "example_curry" -> {
            val drawable = AppCompatResources.getDrawable(view.context, R.drawable.curry)
            view.setImageDrawable(drawable)
        }
        else -> {
            view.setImageURI(imageUri.toUri())
        }
    }
}

@BindingAdapter("setFilled")
fun setFilled(
    view: ImageView,
    shouldFill:Boolean
) {
    if (shouldFill) {
        val color = ContextCompat.getColor(view.context,R.color.primaryColor)
        view.setColorFilter(color)
    } else {
        val color = ContextCompat.getColor(view.context,R.color.primaryTextColor)
        view.setColorFilter(color)
    }
}

@BindingAdapter("starLevel","starNumber")
fun setStarsFilled(
    view: ImageView,
    level: Int,
    starNumber:Int
) {
    when (starNumber) {
        1 -> setFilled(view,level >= 1)
        2 -> setFilled(view,level >= 2)
        3 -> setFilled (view, level >= 3)
    }
}

@BindingAdapter("formatDuration")
fun setDurationText(
    view: TextView,
    minutes:Int
) {
    val text = "$minutes minutes"
    view.text = text
}


