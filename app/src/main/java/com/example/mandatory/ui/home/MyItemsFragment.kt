package com.example.mandatory.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import com.example.mandatory.databinding.FragmentMyItemsBinding
import com.example.mandatory.ui.login.LoginViewModel

class MyItemsFragment : Fragment() {

    private var _binding: FragmentMyItemsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private val viewModel: LoginViewModel by activityViewModels() //tager email med.

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(MyItemsViewModel::class.java)

        _binding = FragmentMyItemsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textMyItems
        homeViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        viewModel.email.observe(viewLifecycleOwner)
        { email -> email } // gemmer mail i baggrunden
        Log.d("Email logget ind", viewModel.email.value.toString())
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}