package com.guidoroos.recepten.db

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "recipe")
data class Recipe(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val title: String,
    val imageResourceId: Int? = null,
    val description: String,
    val cuisineId: Long? = null,
    val recipeTypeId: Long,
    val timeCreated: Long = System.currentTimeMillis(),
    val timeLastSeen: Long = System.currentTimeMillis(),
    val level: Int,
    val isFavorite:Boolean = false,
    val minutesDuration:Int
): Parcelable

