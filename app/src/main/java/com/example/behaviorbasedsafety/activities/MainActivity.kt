package com.example.behaviorbasedsafety.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.widget.Toolbar
import com.example.behaviorbasedsafety.R
import com.example.behaviorbasedsafety.databinding.ActivityLoginBinding
import com.example.behaviorbasedsafety.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val TAG = "FirebaseDebug"

        //Creacion de toolbar
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        //Recibo de parametros Login
        val userId = intent.getStringExtra("user_id")
        val emailId = intent.getStringExtra("email_id")

        //Funcionalidad de boton create
        binding.btnCreate.setOnClickListener {

            val intent = Intent(this, CreateActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            intent.putExtra("user_id", userId)
            intent.putExtra("email_id", emailId)
            startActivity(intent)
            finish()
        }

        //Funcionalidad de boton view all
        binding.btnViewall.setOnClickListener {
            val intent = Intent(this, SearchActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            intent.putExtra("user_id", userId)
            intent.putExtra("email_id", emailId)
            startActivity(intent)
            finish()
        }

        /*
        //Ejemplo firebase
        val db = FirebaseFirestore.getInstance()
        db.collection("BBS")
            .get()
            .addOnSuccessListener { result ->

                for(document in result){
                    Log.d(TAG,"Nombre: ${document.getString("nombre")}")
                    Log.d(TAG,"Area: ${document.getString("area")}")
                }

            }
            .addOnFailureListener {

            }
         */
    }

    //Inflado de barra de menu
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.usermenu,menu)

        return super.onCreateOptionsMenu(menu)
    }

    //Funcines de barra de menu
    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        val userId = intent.getStringExtra("user_id")
        val emailId = intent.getStringExtra("email_id")

        when(item.itemId)
        {
            R.id.lbl_email -> {
                val intent = Intent(this, ProfileActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                intent.putExtra("user_id", userId)
                intent.putExtra("email_id", emailId)
                startActivity(intent)
            }
            R.id.btn_logout ->{
                FirebaseAuth.getInstance().signOut()

                startActivity(Intent(this,LoginActivity::class.java))
            }
        }

        return super.onOptionsItemSelected(item)
    }

}