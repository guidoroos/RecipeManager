package com.guidoroos.recepten.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.guidoroos.recepten.R
import java.io.File

@BindingAdapter("isVisible")
fun View.setVisibility(show: Boolean) {
    this.visibility = if (show) View.VISIBLE else View.GONE
}

@BindingAdapter("imageUrl")
fun loadImage(
    view: ImageView,
    url: String
) { // This methods should not have any return type, = declaration would make it return that object declaration.
    Glide.with(view.context)
        .load(url)
        .placeholder(R.drawable.ic_local_dining)
        .circleCrop()
        .into(view)
}

@BindingAdapter("imageResource", "isEditPage")
fun imageResourceOrPlaceholder(
    view: ImageView,
    imageUri: String?,
    isEditPage: Boolean
) {
    when (imageUri) {
        null -> {
            val resource =
                if (isEditPage) R.drawable.ic_camera else R.drawable.ic_local_dining
            view.setImageResource(resource)
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
            val imageBitmap = getCapturedImage(
                view.context, Uri.fromFile(
                    File(imageUri)
                )
            )
            view.setImageBitmap(imageBitmap)
        }
    }
}

@BindingAdapter("setFilled")
fun setFilled(
    view: ImageView,
    shouldFill: Boolean
) {
    if (shouldFill) {
        val color = ContextCompat.getColor(view.context, R.color.primaryColor)
        view.setColorFilter(color)
    } else {
        val color = ContextCompat.getColor(view.context, R.color.cardview_shadow_end_color)
        view.setColorFilter(color)
    }
}

@BindingAdapter("starLevel", "starNumber")
fun setStarsFilled(
    view: ImageView,
    level: Int,
    starNumber: Int
) {
    when (starNumber) {
        1 -> setFilled(view, level >= 1)
        2 -> setFilled(view, level >= 2)
        3 -> setFilled(view, level >= 3)
    }
}

@BindingAdapter("formatDuration")
fun setDurationText(
    view: TextView,
    minutes: Int
) {
    if (minutes < 60) {
        val text = "$minutes minutes"
        view.text = text
    } else {
        val text = "${minutes / 60}:${minutes % 60} hours"
        view.text = text
    }

}

private fun getCapturedImage(context: Context, selectedPhotoUri: Uri): Bitmap {
    return when {
        Build.VERSION.SDK_INT < 28 -> MediaStore.Images.Media.getBitmap(
            context.contentResolver,
            selectedPhotoUri
        )
        else -> {
            val source = ImageDecoder.createSource(context.contentResolver, selectedPhotoUri)
            ImageDecoder.decodeBitmap(source)
        }
    }
}


