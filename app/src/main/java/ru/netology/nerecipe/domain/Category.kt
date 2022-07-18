package ru.netology.nerecipe.domain

data class Category(
    val id: Long = 0L,
    val title: String,
    val isShown: Boolean = true
)
