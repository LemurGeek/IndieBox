package com.example.indiebox

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.core.view.get
import androidx.core.view.isEmpty
import com.example.indiebox.databinding.ActivityRegisterBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import java.lang.Exception

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private lateinit var db: DocumentReference
    private lateinit var userEmail: String;
    private lateinit var firestore: FirebaseFirestore;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firestore = FirebaseFirestore.getInstance();
        db = FirebaseFirestore.getInstance().document("IndieBox/Apps")
        val user = Firebase.auth.currentUser?.email;
        userEmail = "$user";

        binding.createBtn.setOnClickListener { registerApp() }
    }

    private fun registerApp(){
        val name = binding.appNameTxt.text.toString();
        val typeGroup = binding.typeRd;
        val intSelectButton: Int = typeGroup!!.checkedRadioButtonId;
        val type = findViewById<RadioButton>(intSelectButton).text.toString();
        val platform = binding.platformTxt.text.toString();
        val description = binding.descriptionTxt.text.toString();

        if(!name.isEmpty() && !platform.isEmpty() && !description.isEmpty()){
            try {
                val item = HashMap<String, Any>()
                item.put("name", name)
                item.put("type", type)
                item.put("platform", platform)
                item.put("description", description)
                item.put("user", userEmail)
                val document: DocumentReference = firestore.collection("IndieBox/Apps/$type").document();
                item.put("id", document.id)
                db.collection(type).document(document.id).set(item).addOnSuccessListener {
                    void: Void? -> Toast.makeText(baseContext, "Successfully created new " + type + ".", Toast.LENGTH_LONG).show();
                }.addOnFailureListener {
                    exception: java.lang.Exception -> Toast.makeText(baseContext, exception.toString(), Toast.LENGTH_LONG).show();
                }

            } catch (e: Exception) {
                Toast.makeText(baseContext, e.toString(), Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(baseContext,
                "Please, fill up all the fields.",
                Toast.LENGTH_LONG).show();
        }
    }
}