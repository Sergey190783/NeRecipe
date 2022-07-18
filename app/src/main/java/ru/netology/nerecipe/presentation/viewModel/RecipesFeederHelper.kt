package ru.netology.nerecipe.presentation.viewModel

import ru.netology.nerecipe.domain.Recipe

interface RecipesFeederHelper {
    fun onRecipeClicked(recipe: Recipe?)
    fun getCategoryName(id: Long): String?
    fun onFavouriteClicked(recipe: Recipe?)
    fun updateRepoWithNewListFromTo(list: List<Recipe>, dragFrom: Int, dragTo: Int): Boolean
}