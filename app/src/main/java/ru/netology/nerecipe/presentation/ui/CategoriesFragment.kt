package ru.netology.nerecipe.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import ru.netology.nerecipe.R
import ru.netology.nerecipe.databinding.FragmentCategoriesBinding
import ru.netology.nerecipe.presentation.adapter.CategoriesAdapter
import ru.netology.nerecipe.presentation.viewModel.RecipesViewModel

class CategoriesFragment : Fragment() {
    private val viewModel: RecipesViewModel by activityViewModels()
    private var _binding: FragmentCategoriesBinding? = null
    private val binding get() = _binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val callback = requireActivity().onBackPressedDispatcher.addCallback(this) {
            Toast.makeText(context, getString(R.string.cat_feeder_string01), Toast.LENGTH_SHORT)
                .show()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCategoriesBinding.inflate(inflater)

        val adapter = CategoriesAdapter(viewModel)
        binding?.categoriesList?.adapter = adapter

        viewModel.catData.observe(viewLifecycleOwner) { categories ->
            adapter.submitList(categories)
        }


        return binding?.root
    }

}