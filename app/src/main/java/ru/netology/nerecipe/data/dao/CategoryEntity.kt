package ru.netology.nerecipe.data.dao

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.netology.nerecipe.domain.Category

@Entity(tableName = "categories")
data class CategoryEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id_cat")
    val id: Long,

    @ColumnInfo(name = "name_cat")
    val title: String,

    @ColumnInfo(name = "show_or_not")
    val isShown: Boolean
)


fun Category.toEntity(): CategoryEntity {
    return CategoryEntity(id, title, isShown)
}

fun CategoryEntity.toModel(): Category {
    return Category(id, title, isShown)
}