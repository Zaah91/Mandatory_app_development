package com.example.mandatory.ui.all_items

import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.recyclerview.widget.GridLayoutManager
import com.example.mandatory.R
import com.example.mandatory.databinding.FragmentAllItemsBinding
import com.example.mandatory.ui.login.LoginViewModel

class AllItemsFragment : Fragment() {

    private var _binding: FragmentAllItemsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private val viewModel: LoginViewModel by activityViewModels() //tager email med.
    private val itemsViewModel: ItemsViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val dashboardViewModel =
            ViewModelProvider(this).get(AllItemsViewModel::class.java)

        _binding = FragmentAllItemsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        /*dashboardViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }*/

        viewModel.email.observe(viewLifecycleOwner)
        { email -> email }

        itemsViewModel.itemsLiveData.observe(viewLifecycleOwner) { items ->
            binding.progressbar.visibility = View.GONE
            binding.recyclerView.visibility = if (items == null) View.GONE else View.VISIBLE
            if (items != null) {
                val adapter = ItemsAdapter(items) { position ->
                    /*val action =
                        //FirstFragmentDirections.actionFirstFragmentToSecondFragment(position)
                    findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)*/
                }
                // binding.recyclerView.layoutManager = LinearLayoutManager(activity)
                var columns = 1
                val currentOrientation = this.resources.configuration.orientation
                if (currentOrientation == Configuration.ORIENTATION_LANDSCAPE) {
                    columns = 1
                } else if (currentOrientation == Configuration.ORIENTATION_PORTRAIT) {
                    columns = 1
                }
                binding.recyclerView.layoutManager = GridLayoutManager(this.context, columns)

                binding.recyclerView.adapter = adapter
            }
        }

        itemsViewModel.errorMessageLiveData.observe(viewLifecycleOwner) { errorMessage ->
            /*binding.textviewMessage.text =*/ errorMessage
        }

        itemsViewModel.reload()

        binding.buttonSort.setOnClickListener {
            when (binding.spinnerSorting.selectedItemPosition) {
                0 -> itemsViewModel.sortByDescription()
                1 -> itemsViewModel.sortByDescriptionDescending()
                2 -> itemsViewModel.sortByPrice()
                3 -> itemsViewModel.sortByPriceDescending()
            }
        }

        binding.buttonFilter.setOnClickListener {
            val description = binding.edittextFilterDescription.text.toString().trim()
            /* if (title.isBlank()) {
                 binding.edittextFilterTitle.error = "No title"
                 return@setOnClickListener
             }*/
            itemsViewModel.filterByTitle(description)
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}