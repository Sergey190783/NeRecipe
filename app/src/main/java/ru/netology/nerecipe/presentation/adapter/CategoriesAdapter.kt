package ru.netology.nerecipe.presentation.adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.CompoundButton
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.netology.nerecipe.R
import ru.netology.nerecipe.databinding.CategoryDetailCheckboxBinding
import ru.netology.nerecipe.domain.Category
import ru.netology.nerecipe.presentation.viewModel.CategoriesHelper

class CategoriesAdapter(private val helper: CategoriesHelper) :
    ListAdapter<Category, CategoriesAdapter.CatViewHolder>(CategoryDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = CategoryDetailCheckboxBinding.inflate(inflater, parent, false)

        return CatViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CatViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class CatViewHolder(private val binding: CategoryDetailCheckboxBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Category?) {
            if (item == null) return
            with(binding) {
                checkboxCategory.text = item.title
                checkboxCategory.isChecked = item.isShown

                checkboxCategory.tag = TAG_PROCESS
                checkboxCategory.setOnCheckedChangeListener { buttonView: CompoundButton?, isChecked: Boolean ->
                    if (buttonView == null) return@setOnCheckedChangeListener
                    if (!buttonView.isPressed) return@setOnCheckedChangeListener

                    if (isChecked) {
                        helper.setCategoryVisible(item.id)
                        checkboxCategory.tag = TAG_PROCESS
                        return@setOnCheckedChangeListener
                    }

                    val num = helper.getNumberOfSelectedCategories()
                    if (num > 1) {
                        helper.setCategoryInvisible(item.id)
                        checkboxCategory.tag = TAG_PROCESS
                        return@setOnCheckedChangeListener
                    }

                    checkboxCategory.tag = TAG_SKIP
                    buttonView.isChecked = true
                    Toast.makeText(
                        binding.root.context,
                        binding.root.context.getString(R.string.cat_adapter_string01),
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }

    }

    private object CategoryDiffCallback : DiffUtil.ItemCallback<Category>() {
        override fun areItemsTheSame(oldItem: Category, newItem: Category): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Category, newItem: Category): Boolean {
            return oldItem == newItem
        }
    }

    companion object {
        const val TAG_SKIP = ".SKIP"
        const val TAG_PROCESS = ".PROCESS"
    }

}