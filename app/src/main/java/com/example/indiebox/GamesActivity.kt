package com.example.indiebox

import android.content.ContentValues
import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import com.example.indiebox.databinding.ActivityGamesBinding
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore

class GamesActivity : AppCompatActivity() {
    private lateinit var db: DocumentReference
    private lateinit var firestore: FirebaseFirestore
    private lateinit var binding: ActivityGamesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityGamesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firestore = FirebaseFirestore.getInstance();
        db = FirebaseFirestore.getInstance().document("IndieBox/Apps")
        //val user = Firebase.auth.currentUser?.email;
        loadGames()

        binding.swipe.setOnRefreshListener {
            binding.pbLoading.visibility = View.VISIBLE
            binding.llGamesList.removeAllViews()
            loadGames()
            binding.swipe.isRefreshing = false
        }

        binding.btBack.setOnClickListener {
            this.finish()
        }
    }

    private fun loadGames(){
        db.collection("Game")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    binding.pbLoading.visibility = View.INVISIBLE
                    CreateAppCardView(document.data["name"].toString(),document.data["description"].toString(),document.data["platform"].toString(),document.data["user"].toString())
                }
            }
            .addOnFailureListener { exception ->
                Log.w(ContentValues.TAG, "Error getting documents.", exception)
            }

    }

    fun CreateAppCardView(appName: String,appDescription: String,platform: String,user: String){


        var params = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT)
        params.setMargins(0,0,0,30)
        params.width = 970
        params.height = 420
        params.gravity = Gravity.CENTER

        val cardView = CardView(this)
        cardView.radius = 60f
        cardView.setCardBackgroundColor(Color.parseColor("#FFFFFF"))
        cardView.layoutParams = params
        cardView.cardElevation = 30f


        val containerLayout = LinearLayout(this)

        params = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT)

        containerLayout.orientation = LinearLayout.VERTICAL
        containerLayout.layoutParams = params
        containerLayout.setPadding(30,10,30,10)

        val gameName = TextView(this)
        gameName.text = appName;
        gameName.textAlignment = TextView.TEXT_ALIGNMENT_CENTER
        gameName.textSize = 24f
        gameName.setTextColor(Color.BLACK)
        gameName.setTypeface(Typeface.SANS_SERIF,Typeface.BOLD)

        val descriptionTxt = TextView(this)
        descriptionTxt.text = "\n$appDescription";
        descriptionTxt.textAlignment = TextView.TEXT_ALIGNMENT_VIEW_START
        descriptionTxt.textSize = 16f
        descriptionTxt.setTextColor(Color.BLACK)
        descriptionTxt.setTypeface(Typeface.SANS_SERIF,Typeface.NORMAL)

        val containerLayoutDetails = LinearLayout(this)

        params = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT)
        params.setMargins(0,5,0,0)

        containerLayoutDetails.orientation = LinearLayout.HORIZONTAL
        containerLayoutDetails.layoutParams = params
        containerLayoutDetails.setPadding(30,10,30,10)

        val platformTxt = TextView(this)
        platformTxt.text = "Platform: $platform";
        platformTxt.textAlignment = TextView.TEXT_ALIGNMENT_VIEW_START
        platformTxt.textSize = 12f
        platformTxt.setTextColor(Color.BLACK)
        platformTxt.setTypeface(Typeface.SANS_SERIF,Typeface.BOLD)

        val userTxt = TextView(this)
        userTxt.text = "   Owner: $user"
        userTxt.textAlignment = TextView.TEXT_ALIGNMENT_VIEW_END
        userTxt.textSize = 12f
        userTxt.setTextColor(Color.BLACK)
        userTxt.setTypeface(Typeface.SANS_SERIF,Typeface.BOLD)

        containerLayout.addView(gameName)
        containerLayout.addView(descriptionTxt)
        containerLayoutDetails.addView(platformTxt)
        containerLayoutDetails.addView(userTxt)
        containerLayout.addView(containerLayoutDetails)
        cardView.addView(containerLayout)

        binding.llGamesList.addView(cardView)



    }
}