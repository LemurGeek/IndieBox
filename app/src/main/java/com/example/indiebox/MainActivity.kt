package com.example.indiebox

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
      var Boton1  = findViewById<Button>(R.id.Enter_btn)

      Boton1.setOnClickListener{
        val irRegister = Intent(this, Register_New_App::class.java)
        startActivity(irRegister)
    }
    }
}

