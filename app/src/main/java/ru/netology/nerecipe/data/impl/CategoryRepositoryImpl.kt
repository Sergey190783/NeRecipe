package ru.netology.nerecipe.data.impl

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.map
import ru.netology.nerecipe.data.dao.CategoryDao
import ru.netology.nerecipe.data.dao.toEntity
import ru.netology.nerecipe.data.dao.toModel
import ru.netology.nerecipe.data.CategoryRepository
import ru.netology.nerecipe.domain.Category

class CategoryRepositoryImpl(private val dao: CategoryDao) : CategoryRepository {
    private var categories: List<Category> = emptyList()
        get() = checkNotNull(data.value) { "Categories data value should not be null!" }

    override lateinit var data: LiveData<List<Category>>

    init {
        data = dao.getAllSortedAsc().asLiveData().map { catList ->
            catList.map { it.toModel() }
        }
    }

    override fun getNumberOfSelectedCategories(): Int {
        return dao.getNumberOfSelectedCategories()
    }

    override fun deleteAllCategories() {
        dao.deleteAllCategories()
    }

    override fun getIdByName(category: String?): Long? {
        return dao.getIdByName(category)
    }

    override fun save(cat: Category): Long {
        return dao.insert(cat.toEntity())
    }

    override fun remove(id: Long) {
        dao.removeById(id)
    }

    override fun setVisible(id: Long) {
        dao.setVisible(id)
    }

    override fun setNotVisible(id: Long) {
        dao.setNotVisible(id)
    }

    override fun getName(getId: Long): String? = dao.getNameById(getId)

}