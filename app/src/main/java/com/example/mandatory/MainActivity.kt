package com.example.mandatory

import android.os.Bundle
import android.text.InputType
import android.view.Window
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import androidx.appcompat.app.AlertDialog
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.mandatory.databinding.ActivityMainBinding
import com.example.mandatory.ui.login.LoginViewModel
import com.example.mandatory.ui.repository.Items
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth

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
                R.id.navigation_my_items, R.id.navigation_view_all, R.id.navigation_login
            )

        )
        loginMenuItem.setOnMenuItemClickListener {
            // TODO skal rydde data i LoginViewModel og redirect til login siden
            true
        }


        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }
}