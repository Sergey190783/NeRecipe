package ru.netology.nerecipe.data

import androidx.lifecycle.LiveData
import ru.netology.nerecipe.domain.RecipeStage

interface RecipeStepsRepository {
    val dataAll: LiveData<List<RecipeStage>>
    val dataFiltered: LiveData<List<RecipeStage>>

    fun save(step: RecipeStage): Long
    fun remove(id: Long)
    fun update(oldId: Long, newId: Long)
    fun updateContent(stepId: Long, newContent: String)
    fun updateStep(step: RecipeStage)
    fun getStepById(getId: Long): RecipeStage
    fun getStepsListWithRecId(getRecId: Long): List<RecipeStage>
}