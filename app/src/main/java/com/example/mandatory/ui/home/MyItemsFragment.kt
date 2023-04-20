package com.example.mandatory.ui.home

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.mandatory.R
import com.example.mandatory.databinding.FragmentMyItemsBinding
import com.example.mandatory.ui.all_items.AllItemsViewModel
import com.example.mandatory.ui.all_items.ItemsAdapter
import com.example.mandatory.ui.all_items.ItemsViewModel
import com.example.mandatory.ui.login.LoginViewModel

class MyItemsFragment : Fragment() {

    private var _binding: FragmentMyItemsBinding? = null

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

        _binding = FragmentMyItemsBinding.inflate(inflater, container, false)
        val root: View = binding.root

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


        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}