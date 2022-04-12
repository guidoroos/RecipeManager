package com.guidoroos.recepten.util

import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.appcompat.content.res.AppCompatResources
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.google.android.play.core.splitinstall.d
import com.guidoroos.recepten.R

@BindingAdapter("imageUrl")
fun loadImage(view: ImageView, url: String) { // This methods should not have any return type, = declaration would make it return that object declaration.
    Glide.with(view.context)
        .load(url)
        .placeholder(R.drawable.ic_baseline_local_dining_24)
        .circleCrop()
        .into(view)
}


@BindingAdapter("imageResource", "placeholder")
fun imageResourceOrPlaceholder(
    view: ImageView,
    imageResource: Int?,
    placeholder: Drawable
) {
    val drawable =
        if (imageResource == null) {
            placeholder
        } else {
            AppCompatResources.getDrawable(view.context, imageResource)
        }

    view.setImageDrawable(drawable)
}