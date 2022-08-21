package com.guidoroos.recepten.util

import android.content.ContentProvider
import android.content.Context
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
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
import java.io.File


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
            val imageBitmap = getCapturedImage(view.context, Uri.fromFile(
                File(imageUri)
            ))
            view.setImageBitmap(imageBitmap)
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
        val color = ContextCompat.getColor(view.context,R.color.cardview_shadow_end_color)
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
    if (minutes < 60) {
        val text = "$minutes minutes"
        view.text = text
    }
    else {
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


