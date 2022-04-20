package com.example.indiebox

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

  private lateinit var txtUser: EditText
  private lateinit var txtPassword: EditText
  private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)
      setContentView(R.layout.activity_main)
      var Boton1 = findViewById<Button>(R.id.Enter_btn)

      Boton1.setOnClickListener(){
        singIn()
      }

      txtUser=findViewById(R.id.txtUser)
      txtPassword=findViewById(R.id.txtPass)
      auth= FirebaseAuth.getInstance()


    }





fun singIn(){
  loginUser()
}

private fun loginUser(){
  val user:String=txtUser.text.toString()
  val password:String=txtPassword.text.toString()

  if(!TextUtils.isEmpty(user) && !TextUtils.isEmpty(password)){

    auth.signInWithEmailAndPassword(user, password)
      .addOnCompleteListener(this){
          task ->

        if(task.isSuccessful){
          action()
        }else{
          Toast.makeText(this,"Error de credenciales", Toast.LENGTH_LONG).show()
        }
      }
  }

}
private fun action(){
  startActivity(Intent(this,GamesActivity::class.java))
}
}

