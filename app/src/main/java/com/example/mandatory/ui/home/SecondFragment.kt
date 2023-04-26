package com.example.mandatory.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.mandatory.databinding.FragmentSecondBinding
import com.example.mandatory.ui.all_items.ItemsViewModel
import com.example.mandatory.ui.login.LoginViewModel
import com.example.mandatory.ui.repository.Items
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class SecondFragment : Fragment() {

    private var _binding: FragmentSecondBinding? = null
    private val binding get() = _binding!!
    private val itemsViewModel: ItemsViewModel by activityViewModels()
    private val viewModel: LoginViewModel by activityViewModels() //tager email med.

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //viewModel.email.observe(viewLifecycleOwner) { email -> email }

        val bundle = requireArguments()
        val secondFragmentArgs: SecondFragmentArgs = SecondFragmentArgs.fromBundle(bundle)
        val position = secondFragmentArgs.position
        val item = itemsViewModel[position]
        if (item == null) {
            binding.textviewMessage.text = "No such sales item!"
            return
        }

        binding.editTextDescription.setText(item.description)
        binding.editTextPrice.setText(item.price.toString())
        //binding.editTextSellerEmail.setText(item.sellerEmail)
        binding.editTextPhone.setText(item.sellerPhone)
        binding.editTextPictureUrl.setText(item.pictureUrl)

        binding.buttonBack.setOnClickListener {
            findNavController().popBackStack()
        }
        if (Firebase.auth.currentUser?.email == item.sellerEmail)
            binding.buttonDelete.visibility = View.VISIBLE
        else binding.buttonDelete.visibility = View.GONE
        binding.buttonDelete.setOnClickListener {
            itemsViewModel.delete(item.id)
            findNavController().popBackStack()
        }

        binding.buttonUpdate.setOnClickListener {
            val description = binding.editTextDescription.text.toString().trim()
            val price = binding.editTextPrice.text.toString().trim().toInt()
            val sellerEmail = viewModel.email.observe(viewLifecycleOwner) { email -> email }.toString()
            val sellerPhone = binding.editTextPhone.text.toString().trim()
            val time = System.currentTimeMillis()/1000
            val pictureUrl = binding.editTextPictureUrl.text.toString().trim()
            val updatedSalesItem = Items(
                item.id,
                description,
                price,
                sellerEmail,
                sellerPhone,
                time,
                pictureUrl
            )
            Log.d("APPLE", "update $updatedSalesItem")
            //itemsViewModel.update(updatedSalesItem)
            findNavController().popBackStack()
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}