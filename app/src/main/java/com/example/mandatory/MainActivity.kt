package com.example.mandatory

import android.os.Bundle
import android.text.InputType
import android.view.Window
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.mandatory.databinding.ActivityMainBinding
import com.example.mandatory.ui.all_items.ItemsViewModel
import com.example.mandatory.ui.repository.Items
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar()?.hide(); // skjuler actionbar i toppen med title

        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView
        val loginMenuItem = navView.menu.findItem(R.id.navigation_login)

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_view_all, R.id.navigation_login
            )

        )
        loginMenuItem.setOnMenuItemClickListener {

                    if (Firebase.auth.currentUser != null) {
                        Firebase.auth.signOut()
                        Snackbar.make(binding.root, "Signed out", Snackbar.LENGTH_LONG).show()
                        val navController = findNavController(R.id.nav_host_fragment_activity_main)
                        navController.popBackStack(R.id.navigation_login, false)
                    } else {
                        Snackbar.make(binding.root, "Cannot sign out", Snackbar.LENGTH_LONG).show()
                    }
                    true
            }

        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }
}