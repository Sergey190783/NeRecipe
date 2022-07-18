package ru.netology.nerecipe.data

import androidx.lifecycle.LiveData
import ru.netology.nerecipe.domain.Category

interface CategoryRepository {
    val data: LiveData<List<Category>>

    fun save(cat: Category): Long
    fun remove(id: Long)

    fun setVisible(id: Long)
    fun setNotVisible(id: Long)

    fun getName(getId: Long): String?
    fun getIdByName(category: String?): Long?

    fun getNumberOfSelectedCategories(): Int

    fun deleteAllCategories()
}