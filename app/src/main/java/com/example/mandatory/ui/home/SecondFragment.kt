package com.example.mandatory.ui.home

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.mandatory.databinding.FragmentSecondBinding
import com.example.mandatory.ui.all_items.ItemsViewModel
import com.example.mandatory.ui.login.LoginViewModel
import com.example.mandatory.ui.repository.Items

class SecondFragment : Fragment() {

    private var _binding: FragmentSecondBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.

    private val binding get() = _binding!!
    private val viewModel: LoginViewModel by activityViewModels() //tager email med.
    private val itemsViewModel: ItemsViewModel by activityViewModels()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.email.observe(viewLifecycleOwner) { email -> email }

        val bundle = requireArguments()
        val secondFragmentArgs: SecondFragmentArgs = SecondFragmentArgs.fromBundle(bundle)
        val position = secondFragmentArgs.position
        val item = itemsViewModel[position]
        if (item == null) {
            binding.textviewMessage.text = "No such item!"
            return
        }

        binding.editTextDescription.setText(item.description)
        binding.editTextPrice.setText(item.price.toString())

        binding.buttonBack.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.buttonDelete.setOnClickListener {
            itemsViewModel.delete(item.id)
            findNavController().popBackStack()
        }

        binding.buttonUpdate.setOnClickListener {
            val description = binding.editTextDescription.text.toString().trim()
            val price = binding.editTextPrice.text.toString().trim().toInt()
            val sellerEmail = viewModel.email.observe(viewLifecycleOwner) { email -> email }.toString()
            val sellerPhone = binding.editTextPhone.text.toString().trim()
            val time = System.currentTimeMillis()/1000L.toString().toLong()
            val pictureUrl = binding.editTextPictureUrl.text.toString().trim()
            val updatedItems = Items(description,  price, sellerEmail, sellerPhone, time, pictureUrl)

            Log.d("APPLE", "update $updatedItems")
            //itemsViewModel.update(updatedItems)
            findNavController().popBackStack()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}