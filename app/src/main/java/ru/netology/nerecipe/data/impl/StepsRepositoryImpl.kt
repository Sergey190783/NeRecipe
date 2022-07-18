package ru.netology.nerecipe.data.impl

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.map
import ru.netology.nerecipe.dao.toEntity
import ru.netology.nerecipe.dao.toModel
import ru.netology.nerecipe.data.dao.StepDao
import ru.netology.nerecipe.data.RecipeStepsRepository
import ru.netology.nerecipe.domain.RecipeStage

class StepsRepositoryImpl(private val dao: StepDao) : RecipeStepsRepository {

    private var stepsFiltered: List<RecipeStage> = emptyList()
        get() = checkNotNull(dataFiltered.value) { "Steps data value should not be empty!" }

    override val dataAll: LiveData<List<RecipeStage>> =
        dao.getAllRecipeSteps().asLiveData().map { stepsList ->
            stepsList.map { it.toModel() }
        }

    override val dataFiltered: LiveData<List<RecipeStage>> =
        dao.getFilteredRecipeSteps().asLiveData().map { filteredList ->
            filteredList.map { it.toModel() }
        }

    override fun getStepById(getId: Long): RecipeStage {
        return dao.getStepById(getId).toModel()
    }

    override fun getStepsListWithRecId(getRecId: Long): List<RecipeStage> {
        return dao.getStepsWithRecipeId(getRecId).map { it.toModel() }
    }

    override fun save(step: RecipeStage): Long {
        return dao.insert(step.toEntity())
    }

    override fun remove(id: Long) {
        dao.removeById(id)
    }

    override fun update(oldId: Long, newId: Long) {
        dao.updateRecipesIds(oldId, newId)
    }

    fun get(getId: Long): RecipeStage? {
        val rs = stepsFiltered.find { it.id == getId }
        return rs
    }

    override fun updateContent(stepId: Long, newContent: String) {
        dao.updateStepContent(newContent, stepId)
    }

    override fun updateStep(step: RecipeStage) {
        dao.insert(step.toEntity())
    }

}