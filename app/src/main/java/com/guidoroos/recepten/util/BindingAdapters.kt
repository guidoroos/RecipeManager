package com.guidoroos.recepten.util

import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.appcompat.content.res.AppCompatResources
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
    @DrawableRes imageResource: Int?
) {
    if (imageResource == null) {
        view.setImageResource(R.drawable.ic_baseline_local_dining_24)
    } else {
        val drawable = AppCompatResources.getDrawable(view.context,imageResource)
        view.setImageDrawable(drawable)
    }
}