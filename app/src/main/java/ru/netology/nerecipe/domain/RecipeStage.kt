package ru.netology.nerecipe.domain

import android.net.Uri

data class RecipeStage(
    val id: Long,
    val recipeId: Long,
    val content: String,
    val picture: String = "empty",
    val pUri: Uri? = null
)
