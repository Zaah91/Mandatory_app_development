package com.example.mandatory.ui.home

import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.mandatory.R
import com.example.mandatory.databinding.FragmentAllItemsBinding
import com.example.mandatory.databinding.FragmentMyItemsBinding
import com.example.mandatory.ui.all_items.AllItemsViewModel
import com.example.mandatory.ui.all_items.ItemsAdapter
import com.example.mandatory.ui.all_items.ItemsViewModel
import com.example.mandatory.ui.login.LoginViewModel
import com.example.mandatory.ui.repository.Items
import com.example.mandatory.ui.repository.humanDate
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import java.text.DateFormat
import java.time.format.DateTimeFormatter

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

        _binding = FragmentMyItemsBinding.inflate(inflater, container, false)

        binding.createNewItemButton.setOnClickListener {
            Log.d("Knap tryk", "linje 49")
            val description = binding.descriptionInput.text.trim().toString()
            if (description.isEmpty()){
                binding.descriptionInput.error = "No description"
                return@setOnClickListener
            }
            val price = binding.priceInput.text.trim().toString()
            if (price.isEmpty()){
                binding.descriptionInput.error = "No price"
                return@setOnClickListener
            }
            val sellerMail = viewModel.email.observe(viewLifecycleOwner){email -> email}.toString()
            val sellerPhone = binding.phoneInput.text.trim().toString()
            if (sellerPhone.isEmpty()){
                binding.descriptionInput.error = "No phone number"
                return@setOnClickListener
            }
            val time = System.currentTimeMillis()/1000L

            when {
                description.isEmpty() -> Snackbar.make(binding.root, "No title", Snackbar.LENGTH_LONG)
                    .show()
                price.isEmpty() -> Snackbar.make(
                    binding.root,
                    "No price",
                    Snackbar.LENGTH_LONG
                )
                sellerPhone.isEmpty() -> Snackbar.make(
                    binding.root,
                    "No phone number",
                    Snackbar.LENGTH_LONG
                )
                    .show()
                else -> {
                    Log.d("Kagemand", "Linje 83")
                    val items = Items(description, price.toInt(), sellerMail, sellerPhone, time, "")
                    itemsViewModel.add(items)
                }
            }
        }
/*
        _binding = FragmentMyItemsBinding.inflate(inflater, container, false)

        if (Firebase.auth.currentUser?.email == salesItems.sellerEmail)
            binding.delete_button.visibility = View.VISIBLE
        else binding.buttonDelete.visibility = View.GONE
        binding.delete_button.setOnClickListener {
            salesViewModel.delete(salesItems.id)
            findNavController().popBackStack()
        }
*/

        val dashboardViewModel =
            ViewModelProvider(this).get(AllItemsViewModel::class.java)

        _binding = FragmentMyItemsBinding.inflate(inflater, container, false)
        val root: View = binding.root

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

        itemsViewModel.errorMessageLiveData.observe(viewLifecycleOwner) { errorMessage -> errorMessage }

        itemsViewModel.reload()


        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}