package com.example.behaviorbasedsafety.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import com.example.behaviorbasedsafety.R
import com.example.behaviorbasedsafety.databinding.ActivityMainBinding
import com.example.behaviorbasedsafety.databinding.ActivityProfileBinding
import com.google.firebase.auth.FirebaseAuth

class ProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Creacion de toolbar
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        //Recibo de parametros Login
        val userId = intent.getStringExtra("user_id")
        val emailId = intent.getStringExtra("email_id")

        binding.lblId.text = "$userId"
        binding.lblEmail.text = "$emailId"

        binding.btnLogout.setOnClickListener {

            FirebaseAuth.getInstance().signOut()

            startActivity(Intent(this,LoginActivity::class.java))

        }


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.usermenu,menu)

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        val userId = intent.getStringExtra("user_id")
        val emailId = intent.getStringExtra("email_id")

         when(item.itemId)
        {
            android.R.id.home -> {
                val intent = Intent(this,MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                intent.putExtra("user_id", userId)
                intent.putExtra("email_id", emailId)
                startActivity(intent)
                finish()
            }
            R.id.lbl_email -> {
                Toast.makeText(this,"Already on profile",Toast.LENGTH_SHORT).show()
            }
            R.id.btn_logout ->{
                FirebaseAuth.getInstance().signOut()

                startActivity(Intent(this,LoginActivity::class.java))
            }
        }
        return super.onOptionsItemSelected(item)
    }
}