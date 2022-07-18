package ru.netology.nerecipe.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import ru.netology.nerecipe.R
import ru.netology.nerecipe.presentation.adapter.RecipesAdapter
import ru.netology.nerecipe.databinding.FragmentFavouriteBinding
import ru.netology.nerecipe.presentation.viewModel.RecipesViewModel

class FavouriteFragment : Fragment() {
    private val viewModel: RecipesViewModel by activityViewModels()
    private var _binding: FragmentFavouriteBinding? = null
    private val binding get() = _binding
    private var isEmptyState: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val callback = requireActivity().onBackPressedDispatcher.addCallback(this) {
            Toast.makeText(
                context,
                context?.getString(R.string.fav_feeder_string01),
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFavouriteBinding.inflate(inflater)

        val adapter = RecipesAdapter(viewModel, RecipesAdapter.RECIPES_ADAPTER)
        binding?.recipesList?.adapter = adapter

        viewModel.isFavouriteShow = true

        viewModel.allRecipesData.observe(viewLifecycleOwner) { recipes ->
            if (recipes.filter { it.isFavourite }.size == 0)
                showEmptyStateFavourites()
            else if (isEmptyState) hideEmptyStateFavourites()

            adapter.submitList(recipes.filter { it.isFavourite })
        }

        viewModel.showRecipe.observe(viewLifecycleOwner) { recipe ->
            if (recipe == null) return@observe
            findNavController().navigate(R.id.action_favouriteFeederFragment_to_recipesCardFragment)
        }

        return binding?.root
    }

    private fun showEmptyStateFavourites() {
        if (binding == null) return
        with(binding!!) {
            recipesList.visibility = View.GONE

            emptyStatePictureFavourites.visibility = View.VISIBLE
            emptyStateTextFavourites.visibility = View.VISIBLE
        }
        isEmptyState = true
    }

    private fun hideEmptyStateFavourites() {
        if (binding == null) return
        with(binding!!) {
            recipesList.visibility = View.VISIBLE

            emptyStatePictureFavourites.visibility = View.GONE
            emptyStateTextFavourites.visibility = View.GONE
        }
        isEmptyState = false
    }

}