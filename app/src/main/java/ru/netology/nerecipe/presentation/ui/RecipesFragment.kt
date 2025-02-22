package ru.netology.nerecipe.presentation.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.addCallback
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import ru.netology.nerecipe.R
import ru.netology.nerecipe.presentation.adapter.RecipesAdapter
import ru.netology.nerecipe.databinding.FragmentRecipesBinding
import ru.netology.nerecipe.presentation.viewModel.RecipesViewModel

class RecipesFragment : Fragment() {

    private val viewModel: RecipesViewModel by activityViewModels()
    private var _binding: FragmentRecipesBinding? = null
    private val binding get() = _binding
    private var isEmptyState: Boolean = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val callback = requireActivity().onBackPressedDispatcher.addCallback(this) {
            Toast.makeText(context, getString(R.string.rec_feeder_string01), Toast.LENGTH_LONG)
                .show()
            val exit = requireActivity().onBackPressedDispatcher.addCallback {
                requireActivity().finish()
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentRecipesBinding.inflate(inflater, container, false)
        if (binding == null) return super.onCreateView(inflater, container, savedInstanceState)

        val adapter = RecipesAdapter(viewModel, RecipesAdapter.RECIPES_ADAPTER)

        binding?.recipesList?.adapter = adapter
        val rw = binding?.recipesList ?: return binding?.root
        adapter.attachRecyclerView(rw)

        viewModel.isFavouriteShow = false

        // set the filter string
        val filter = if (viewModel.recipeNamesFilter.value.isNullOrEmpty()) ""
        else viewModel.recipeNamesFilter.value
        binding?.recipeNameFilterEdit?.setText(filter)

        viewModel.catData.observe(viewLifecycleOwner) {
            viewModel.initCategories()
        }

        // Submit the list of recipes for the RW with the account of possible applied filter
        viewModel.recData.observe(viewLifecycleOwner) { recipes ->

            if (recipes.size == 0)
                showEmptyState()
            else
                if (isEmptyState) hideEmptyState()

            val filterRN = viewModel.recipeNamesFilter.value
            if (filterRN.isNullOrEmpty()) {
                adapter.submitList(recipes) // No filter applied
            } else {
                adapter.submitList(recipes.filter { rec ->
                    rec.name.contains(filterRN, true) // Apply a filer for recipes names
                })
            }
        }

        // Update the displayed by RW list of Recipes with the search filter entered by the user
        viewModel.recipeNamesFilter.observe(viewLifecycleOwner) { rnFilter ->
            val recData = viewModel.recData.value
            if (rnFilter.isNullOrEmpty()) {
                adapter.submitList(recData)
            } else {
                adapter.submitList(recData?.filter { rec ->
                    rec.name.contains(rnFilter, true)
                })
            }
        }

        binding?.recipeNameFilterEdit?.doOnTextChanged { text, start, before, count ->
            val newText = text.toString().trim()
            // if  (newText == null ) return@doOnTextChanged
            viewModel.setRecipeNamesFilter(newText)
            Log.d("doOnTextChanged", newText)
        }

        viewModel.showRecipe.observe(viewLifecycleOwner) { recipe ->
            if (recipe == null) return@observe
            findNavController().navigate(R.id.action_recipesFeederFragment_to_recipesCardFragment)
        }

        binding?.addRecipeButton?.alpha = 0.90f
        binding?.addRecipeButton?.setOnClickListener {
            viewModel.addNewRecipe()

        }
        viewModel.editRecipe.observe(viewLifecycleOwner) { recipe ->
            if (recipe == null) return@observe
            findNavController().navigate(R.id.action_recipesFeederFragment_to_recipeNewFragment)
        }

        if (viewModel.tempRecipe != null && viewModel.editRecipe.value != null) {
            findNavController().navigate(R.id.action_recipesFeederFragment_to_recipeNewFragment)
        }

        return binding?.root
    }

    private fun showEmptyState() {
        if (binding == null) return
        with(binding!!) {
            recipesList.visibility = View.GONE
            recipeNameFilterEdit.visibility = View.GONE

            emptyStatePicture.visibility = View.VISIBLE
            emptyStateText.visibility = View.VISIBLE
        }
        isEmptyState = true
    }

    private fun hideEmptyState() {
        if (binding == null) return
        with(binding!!) {
            recipesList.visibility = View.VISIBLE
            recipeNameFilterEdit.visibility = View.VISIBLE

            emptyStatePicture.visibility = View.GONE
            emptyStateText.visibility = View.GONE
        }
        isEmptyState = false
    }

}

