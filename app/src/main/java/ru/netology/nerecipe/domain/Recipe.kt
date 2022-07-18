package ru.netology.nerecipe.domain

data class Recipe(
    val id: Long,
    val name: String,
    val author: String,
    val category: Long,
    val isFavourite: Boolean
)
