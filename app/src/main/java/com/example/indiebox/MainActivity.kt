package com.example.indiebox

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.example.indiebox.databinding.ActivityMainBinding
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)
      binding = ActivityMainBinding.inflate(layoutInflater)
      setContentView(binding.root)
      FirebaseApp.initializeApp(this)

      auth = Firebase.auth

      binding.createBtn.setOnClickListener { registerUser() }
      binding.enterBtn.setOnClickListener { loginUser() }
    }

    private fun loginUser(){
      val email = binding.emailTxt.text.toString()
      val pass = binding.passTxt.text.toString()

      if(!TextUtils.isEmpty(email) && !TextUtils.isEmpty(pass)){
        auth.signInWithEmailAndPassword(email,pass)
          .addOnCompleteListener(this) { task ->
            if (task.isSuccessful) {
              val user = auth.currentUser
              updateUser(user)
            } else {
              Toast.makeText(baseContext,"Fallo login",
                Toast.LENGTH_SHORT).show()
              updateUser(null)
            }
          }
      }
    }

  private fun registerUser() {
    val email = binding.emailTxt.text.toString()
    val pass = binding.passTxt.text.toString()

    auth.createUserWithEmailAndPassword(email,pass)
      .addOnCompleteListener(this) { task ->
        if (task.isSuccessful) {
          val user = auth.currentUser
          updateUser(user)
        } else {
          Toast.makeText(baseContext,
            "Fallo registro",
            Toast.LENGTH_SHORT).show()
          updateUser(null)
        }
      }
  }

    private fun updateUser(user: FirebaseUser?) {
      if (user!=null) {
        val intent = Intent(this,RegisterActivity::class.java)
        startActivity(intent)
      }
    }

    public override fun onStart() {
      super.onStart()
      val user = auth.currentUser
      updateUser(user)
    }
}

