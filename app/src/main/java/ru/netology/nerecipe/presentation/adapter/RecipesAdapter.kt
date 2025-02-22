package ru.netology.nerecipe.presentation.adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.netology.nerecipe.databinding.RecipesDetailsBinding
import ru.netology.nerecipe.domain.Recipe
import ru.netology.nerecipe.presentation.viewModel.RecipesFeederHelper

class RecipesAdapter(val helper: RecipesFeederHelper, private val bindType: String) :
    ListAdapter<Recipe, RecipesAdapter.RecipeViewHolder>(RecipeDiffCallback) {

    private val helperCallback = RecipesHelperCallback() // Up and down movement - OK, swipe - disabled (with "and" operator)
    private val mTouchHelper = ItemTouchHelper(helperCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = RecipesDetailsBinding.inflate(inflater, parent, false)

        return RecipeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    fun attachRecyclerView(rw: RecyclerView) {
        mTouchHelper.attachToRecyclerView(rw)
    }

    inner class RecipeViewHolder(val binding: RecipesDetailsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Recipe?) {
            if (item == null) return
            with(binding) {
                val catNumber = item.category
                recepieName.text = item.name
                authorName.text = item.author
                categoryText.text =
                    helper.getCategoryName(item.category) ?: "Error fetching the Category!"


                imageLikeShow.isVisible = item.isFavourite

//                if (item.isFavourite)
//                    imageLikeShow.isVisible = true
//                else
//                    imageLikeShow.isVisible = false

                if (bindType == RECIPES_ADAPTER) { // if we show recipes scree
                    recipeCardContainer.setOnClickListener {
                        helper.onRecipeClicked(item)
                    }
                } else if (bindType == FAVOURITE_ADAPTER) { // if we show favourites screen
                    recipeCardContainer.setOnClickListener {
                        helper.onFavouriteClicked(item)
                    }
                }

            }
        }

    }

    private object RecipeDiffCallback : DiffUtil.ItemCallback<Recipe>() {
        override fun areItemsTheSame(oldItem: Recipe, newItem: Recipe): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Recipe, newItem: Recipe): Boolean {
            return oldItem == newItem
        }
    }

    companion object {
        const val RECIPES_ADAPTER = ".recipes"
        const val FAVOURITE_ADAPTER = ".favourite"
    }


    inner class RecipesHelperCallback : ItemTouchHelper.SimpleCallback(
        ItemTouchHelper.UP or ItemTouchHelper.DOWN, ItemTouchHelper.LEFT and ItemTouchHelper.RIGHT
    ) {

        private var dragFrom: Int = -1
        private var dragTo: Int = -1

        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            val fromPosition = viewHolder.bindingAdapterPosition
            val toPosition = target.bindingAdapterPosition

            val adapter: RecipesAdapter = recyclerView.adapter as RecipesAdapter
            adapter.notifyItemMoved(fromPosition, toPosition)

            if (dragFrom == -1) dragFrom = fromPosition
            dragTo = toPosition

            return true
        }


        override fun clearView(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder) {
            super.clearView(recyclerView, viewHolder)

            if (dragFrom == -1 || dragTo == -1 || dragFrom == dragTo) return

            val adapter: RecipesAdapter = recyclerView.adapter as RecipesAdapter

            val list = adapter.currentList

            helper.updateRepoWithNewListFromTo(list, dragFrom, dragTo)

            dragFrom = -1
            dragTo = -1
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

        }
    }


}

