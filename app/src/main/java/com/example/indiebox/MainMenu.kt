package com.example.indiebox

import android.content.Intent
import android.graphics.drawable.AnimationDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.example.indiebox.databinding.ActivityMainMenuBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainMenu : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivityMainMenuBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth

        binding.btLogOut.setOnClickListener {
            Firebase.auth.signOut()
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
        }


        binding.btGotoApps.setOnClickListener {
            val intent = Intent(this,AppsActivity::class.java)
            startActivity(intent)
        }
        binding.btGotoGames.setOnClickListener {
            val intent = Intent(this,GamesActivity::class.java)
            startActivity(intent)
        }
        binding.btGotoRegister.setOnClickListener {
            val intent = Intent(this,RegisterActivity::class.java)
            startActivity(intent)
        }

    }



}