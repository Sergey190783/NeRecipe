package ru.netology.nerecipe.data.dao

import androidx.room.*
import androidx.room.Dao
import kotlinx.coroutines.flow.Flow
import ru.netology.nerecipe.dao.RecipeEntity
import ru.netology.nerecipe.dao.StepEntity

@Dao
interface RecipeDao {

    @Query(
        "SELECT * FROM recipes " +
                "INNER JOIN categories ON categories.id_cat = recipes.category " +
                "WHERE categories.show_or_not = 1"
    )
    fun getAllFilteredRecipes(): Flow<List<RecipeEntity>>

    @Query(
        "SELECT * FROM recipes " +
                "INNER JOIN categories ON categories.id_cat = recipes.category " +
                "WHERE categories.show_or_not = 1"
    )
    fun listAllFilteredRecipes(): List<RecipeEntity>


    @Query("SELECT * FROM recipes")
    fun getAllRecipes(): Flow<List<RecipeEntity>>

    @Query("SELECT * FROM recipes")
    fun listAllRecipes(): List<RecipeEntity>


    @Query(
        "SELECT * FROM recipes " +
                "WHERE id_rec IN (:ids)"
    )
    fun getRecipesList(ids: List<Long>): List<RecipeEntity> // for test purposes

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(newRecipe: RecipeEntity): Long

    @Update
    fun updateExistingRecipe(recipe: RecipeEntity): Int

    @Query(
        "SELECT * FROM recipes " +
                "WHERE id_rec = :getId LIMIT 1"
    )
    fun getRecipeById(getId: Long): RecipeEntity

    @Query("DELETE FROM recipes WHERE id_rec = :remId")
    fun removeById(remId: Long)

    @Query("DELETE FROM recipes")
    fun clearAll()

    @Query(
        "UPDATE recipes SET " +
                "is_favourite_rec = CASE WHEN :favourite THEN 1 ELSE 0 END " +
                "WHERE id_rec = :id"
    )
    fun setFavourite(id: Long, favourite: Boolean)
}

@Dao
interface StepDao {
    @Query("SELECT * FROM recipe_steps ORDER BY id_step ASC")
    fun getAllRecipeSteps(): Flow<List<StepEntity>>

    @Query(
        "SELECT * FROM recipe_steps " +
                "INNER JOIN recipes ON recipes.id_rec = recipe_steps.rec_id " +
                "INNER JOIN categories ON categories.id_cat = recipes.category WHERE categories.show_or_not = 1 " +
                "ORDER BY recipe_steps.id_step ASC"
    )
    fun getFilteredRecipeSteps(): Flow<List<StepEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(newStep: StepEntity): Long

    @Query(
        "SELECT * FROM recipe_steps " +
                "WHERE id_step = :getId LIMIT 1"
    )
    fun getStepById(getId: Long): StepEntity

    @Query(
        "SELECT * FROM recipe_steps " +
                "WHERE rec_id = :getRecId"
    )
    fun getStepsWithRecipeId(getRecId: Long): List<StepEntity>


    @Query("DELETE FROM recipe_steps WHERE id_step = :remId")
    fun removeById(remId: Long)

    @Query("UPDATE recipe_steps SET rec_id = :newId WHERE rec_id = :oldId")
    fun updateRecipesIds(oldId: Long, newId: Long)

    @Query("UPDATE recipe_steps SET step_content = :newContent WHERE id_step = :stepId")
    fun updateStepContent(newContent: String, stepId: Long)

    @Update
    fun updateStep(step: StepEntity)
}

@Dao
interface CategoryDao {

    @Query("SELECT * FROM categories ORDER BY name_cat ASC")
    fun getAllSortedAsc(): Flow<List<CategoryEntity>>

    @Query("SELECT * FROM categories ORDER BY name_cat DESC")
    fun getAllSortedDesc(): Flow<List<CategoryEntity>>

    @Query("SELECT name_cat FROM categories WHERE id_cat = :id")
    fun getNameById(id: Long): String?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(inCategory: CategoryEntity): Long

    @Query("DELETE FROM categories WHERE id_cat = :remId")
    fun removeById(remId: Long)

    @Query("DELETE FROM categories")
    fun deleteAllCategories()

    @Query("UPDATE categories SET show_or_not = 1 WHERE id_cat = :id")
    fun setVisible(id: Long)

    @Query("UPDATE categories SET show_or_not = 0 WHERE id_cat = :id")
    fun setNotVisible(id: Long)

    @Query("SELECT id_cat FROM categories WHERE name_cat = :category")
    fun getIdByName(category: String?): Long?

    @Query("SELECT COUNT(*) FROM categories WHERE show_or_not = 1")
    fun getNumberOfSelectedCategories(): Int
}

