package com.example.behaviorbasedsafety.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.behaviorbasedsafety.R
import com.example.behaviorbasedsafety.adapters.CardAdapter
import com.example.behaviorbasedsafety.adapters.toListCards
import com.example.behaviorbasedsafety.data.model.Card
import com.example.behaviorbasedsafety.databinding.ActivitySearchBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class SearchActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySearchBinding
    val collection: String = "BBS"

    private lateinit var cardRecyclerView: RecyclerView
    private lateinit var cardAdapter: CardAdapter



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Instancia a Firebase DB
        val db = FirebaseFirestore.getInstance()
        val TAG = "FirebaseDebug"

        //Recibo de parametros Login
        val userId = intent.getStringExtra("user_id")
        val emailId = intent.getStringExtra("email_id")

        //Creacion de toolbar
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        cardRecyclerView = findViewById(R.id.recycler_bbs)
        cardRecyclerView.setHasFixedSize(true)
        cardRecyclerView.layoutManager = LinearLayoutManager(this)

        bbssRef().get().addOnSuccessListener { result ->

            var cards = result.toListCards()
            Log.d(TAG, cards.toString())

            setAdapter(cards.toMutableList())
        }

    }

    private fun setAdapter(carList: MutableList<Card>){

        cardAdapter = CardAdapter(carList){ card->

            Toast.makeText(this,card.id,Toast.LENGTH_SHORT).show()
            OnClickCard(card.id)

        }
        cardRecyclerView.adapter = cardAdapter
    }

    fun OnClickCard(id : String){
        val intent = Intent(this,DetailActivity::class.java)
        intent.putExtra("card_id",id)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        //intent.putExtra("user_id", userId)
        //intent.putExtra("email_id", emailId)
        startActivity(intent)
        finish()
    }

    fun collectionRef(collection: String)= FirebaseFirestore.getInstance().collection(collection)
    fun bbssRef() = collectionRef(collection)
    fun bbsRef()= bbssRef().document()

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
            android.R.id.home -> {
                val intent = Intent(this,MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                intent.putExtra("user_id", userId)
                intent.putExtra("email_id", emailId)
                startActivity(intent)
                finish()
            }
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