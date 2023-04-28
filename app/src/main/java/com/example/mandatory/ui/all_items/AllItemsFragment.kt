package com.example.mandatory.ui.all_items

import android.content.res.Configuration
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.recyclerview.widget.GridLayoutManager
import com.example.mandatory.MainActivity
import com.example.mandatory.R
import com.example.mandatory.databinding.FragmentAllItemsBinding
import com.example.mandatory.ui.home.SecondFragmentDirections
import com.example.mandatory.ui.login.LoginViewModel
import com.example.mandatory.ui.repository.Items
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

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


        viewModel.email.observe(viewLifecycleOwner) { email -> email }

        if (viewModel.email.value == null){
            binding.newItemButton.visibility = View.GONE
        } else {
            binding.newItemButton.visibility = View.VISIBLE
        }

        itemsViewModel.itemsLiveData.observe(viewLifecycleOwner) { items ->
            binding.progressbar.visibility = View.GONE
            binding.recyclerView.visibility = if (items == null) View.GONE else View.VISIBLE
            if (items != null) {
                val adapter = ItemsAdapter(items) { position ->
                    //val action =
                        //AllItemsFragmentDirections.actionNavigationViewAllToNavigationSecondFragment(position)
                    findNavController().navigate(/*action*/ R.id.action_navigation_view_all_to_navigation_second_fragment)
                }
                // binding.recyclerView.layoutManager = LinearLayoutManager(activity)
                var columns = 1
                val currentOrientation = this.resources.configuration.orientation
                if (currentOrientation == Configuration.ORIENTATION_LANDSCAPE) {
                    columns = 2
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
            itemsViewModel.filterByTitle(description)
        }

        binding.newItemButton.setOnClickListener {
            showDialog()
        }


        binding.swiperefresh.setOnRefreshListener {
            itemsViewModel.reload()
        }



        return root
    }

    private fun showDialog() {
        val builder: AlertDialog.Builder = AlertDialog.Builder(requireContext())
        builder.setTitle("New item")

        val layout = LinearLayout(requireContext())
        layout.orientation = LinearLayout.VERTICAL

        val descriptionInputField = EditText(requireContext())
        descriptionInputField.hint = "Description"
        descriptionInputField.inputType = InputType.TYPE_CLASS_TEXT
        layout.addView(descriptionInputField)

        val priceInputField = EditText(requireContext())
        priceInputField.hint = "Price"
        priceInputField.inputType =
            InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_DECIMAL
        layout.addView(priceInputField)

        val sellerPhoneInputField = EditText(requireContext())
        sellerPhoneInputField.hint = "phone number"
        sellerPhoneInputField.inputType = InputType.TYPE_CLASS_PHONE
        layout.addView(sellerPhoneInputField)

        val pictureUrlInputField = EditText(requireContext())
        pictureUrlInputField.hint = "pictureUrl"
        pictureUrlInputField.inputType = InputType.TYPE_CLASS_TEXT
        layout.addView(pictureUrlInputField)

        builder.setView(layout)

        builder.setPositiveButton("OK") { dialog, which ->
            val description = descriptionInputField.text.toString().trim()
            val priceStr = priceInputField.text.toString().trim()
            val sellerPhone = sellerPhoneInputField.text.toString().trim()
            val pictureUrl = pictureUrlInputField.text.toString().trim()
            when {
                description.isEmpty() ->
                    Snackbar.make(binding.root, "No description", Snackbar.LENGTH_LONG).show()
                description.isEmpty() -> Snackbar.make(binding.root, "No description", Snackbar.LENGTH_LONG)
                    .show()
                priceStr.isEmpty() -> Snackbar.make(
                    binding.root,
                    "No price",
                    Snackbar.LENGTH_LONG
                )
                    .show()
                else -> {
                    val Items =
                        Items(description,
                            priceStr.toInt(),
                            Firebase.auth.currentUser?.email.toString(),
                            sellerPhone,
                            System.currentTimeMillis()/1000,
                            pictureUrl)
                    itemsViewModel.add(Items)
                }
            }
        }
        builder.setNegativeButton("Cancel") { dialog, which -> dialog.cancel() }
        builder.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}