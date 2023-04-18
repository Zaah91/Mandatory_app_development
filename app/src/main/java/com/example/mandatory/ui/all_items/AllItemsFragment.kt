package com.example.mandatory.ui.all_items

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import com.example.mandatory.databinding.FragmentAllItemsBinding
import com.example.mandatory.R

class AllItemsFragment : Fragment() {

    private var _binding: FragmentAllItemsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val dashboardViewModel =
            ViewModelProvider(this).get(AllItemsViewModel::class.java)

        _binding = FragmentAllItemsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textAllItems
        dashboardViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}