package com.example.mandatory.ui.login

import android.app.ActionBar
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.mandatory.R
import com.example.mandatory.databinding.FragmentLoginBinding
import com.google.firebase.auth.FirebaseAuth

class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null

    private val binding get() = _binding!!
    private var auth: FirebaseAuth = FirebaseAuth.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonSignup.setOnClickListener{
            //findNavController().navigate(R.id.action_navigation_logout_to_navigation_my_items, null)
        }
        binding.buttonLogin.setOnClickListener {
            val email = binding.emailInput.text.trim().toString()
            if (email.isEmpty()) {
                binding.emailInput.error = "No email"
                return@setOnClickListener
            }
            val password = binding.password.text.trim().toString()
            if (password.isEmpty()) {
                binding.password.error = "No password"
                return@setOnClickListener
            }
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(requireActivity()) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d("APPLE", "createUserWithEmail:success")
                        val user = auth.currentUser
                        findNavController().navigate(R.id.action_navigation_logout_to_navigation_my_items, null)
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w("APPLE", "createUserWithEmail:failure", task.exception)
                        Toast.makeText(requireContext(), "Fejl", Toast.LENGTH_SHORT).show()
                    }
                }
        }

        binding.buttonSignup.setOnClickListener {
            val email = binding.emailInput.text.trim().toString()
            if (email.isEmpty()) {
                binding.emailInput.error = "No email"
                return@setOnClickListener
            }
            val password = binding.password.text.trim().toString()
            if (password.isEmpty()) {
                binding.password.error = "No password"
                return@setOnClickListener
            }
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(requireActivity()) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d("APPLE", "createUserWithEmail:success")
                        val user = auth.currentUser
                        //updateUI(user)
                        findNavController().navigate(R.id.action_navigation_logout_to_navigation_my_items, null)
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w("APPLE", "createUserWithEmail:failure", task.exception)
                        Toast.makeText(requireContext(), "Fejl", Toast.LENGTH_SHORT).show()
                        //updateUI(null)
                    }
                }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}