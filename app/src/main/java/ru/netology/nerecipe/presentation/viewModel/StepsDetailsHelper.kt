package ru.netology.nerecipe.presentation.viewModel

import android.graphics.Bitmap
import ru.netology.nerecipe.domain.RecipeStage

interface StepsDetailsHelper {
    fun deleteEditedStep(step: RecipeStage)
    fun onChoosePictureClicked(step: RecipeStage)
    fun onEditStepContents(stepId: Long, text: String)
    fun updateStep(step: RecipeStage)
    fun editStep(step: RecipeStage)
    fun getBitmapFromFile(name: String): Bitmap?
    fun updateStepsRepoWithListFromTo(list: List<RecipeStage>, dragFrom: Int, dragTo: Int)
}