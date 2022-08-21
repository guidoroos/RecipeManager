package com.guidoroos.recepten.db

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "recipe")
data class Recipe(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,
    val title: String,
    val imageResourceUri: String? = null,
    val description: String,
    val cuisine: String,
    val recipeType: String,
    val timeCreated: Long = System.currentTimeMillis(),
    val timeLastSeen: Long = System.currentTimeMillis(),
    val level: Int,
    val isFavorite:Boolean = false,
    val minutesDuration:Int
): Parcelable

