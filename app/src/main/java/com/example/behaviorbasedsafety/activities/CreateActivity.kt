package com.example.behaviorbasedsafety.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import com.example.behaviorbasedsafety.R
import com.example.behaviorbasedsafety.data.model.Card
import com.example.behaviorbasedsafety.databinding.ActivityCreateBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class CreateActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCreateBinding
    val collection: String = "BBS"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Instancia a Firebase DB
        val db = FirebaseFirestore.getInstance()
        val TAG = "FirebaseDebug"

        //Creacion de toolbar
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        //Recibo de parametros Login
        val userId = intent.getStringExtra("user_id")
        val emailId = intent.getStringExtra("email_id")

        //binding.lblId.text = "$userId"
        binding.lblEmail.text = "$emailId"



        binding.btnSend.setOnClickListener {

            var aReact : String = checkStatus(binding.swtWorkerReaction.isChecked)
            var aPos : String = checkStatus(binding.swtWorkerPosition.isChecked)
            var aEPP : String = checkStatus(binding.swtEpp.isChecked)
            var aHerr : String = checkStatus(binding.swtTools.isChecked)
            var aPro : String = checkStatus(binding.swtProcedure.isChecked)
            var a5s : String = checkStatus(binding.swt5s.isChecked)

            bbsRef()
                .set(
                    Card("",
                        "${emailId.toString()}",
                         "${binding.txtArea.text}",
                        "${binding.txtMachine.text}",
                        "${binding.txtDate.text}",
                        "${aReact}",
                        "${binding.txtWorkersReactionC.text}",
                        "${aPos}",
                        "${binding.txtWorkerPositionC.text}",
                        "${aEPP}",
                        "${binding.txtEppC.text}",
                        "${aHerr}",
                        "${binding.txtToolsC.text}",
                        "${aPro}",
                        "${binding.txtProcedureC.text}",
                        "${a5s}",
                        "${binding.txt5sC.text}",
                        "${binding.txtGralC.text}",
                        "${binding.txtSafeCount.text.toString()}",
                        "${binding.txtUnsafeCount.text.toString()}",
            ))
                .addOnSuccessListener { status ->
                    Log.d(TAG,"BBS saved: ${status}")
                    Toast.makeText(this,"BBS Saved: ${status}",Toast.LENGTH_SHORT).show()
                    val intent = Intent(this,MainActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    intent.putExtra("user_id", userId)
                    intent.putExtra("email_id", emailId)
                    startActivity(intent)
                    finish()
                }
                .addOnFailureListener { ex ->
                    Log.d(TAG,"Failure: ${ex}")
                    Toast.makeText(this,"An error ocurred: ${ex}",Toast.LENGTH_SHORT).show()
                }


            /*
            db.collection("BBS")
                .get()
                .addOnSuccessListener { result ->

                    for(document in result){
                        Log.d(TAG,"Nombre: ${document.getString("nombre")}")
                        Log.d(TAG,"Area: ${document.getString("area")}")
                    }

                }
                .addOnFailureListener {

                }*/

        }

    }

    fun collectionRef(collection: String)= FirebaseFirestore.getInstance().collection(collection)
    fun bbssRef() = collectionRef(collection)
    fun bbsRef()= bbssRef().document()

    //Funcion para revisar estado del switch
    fun checkStatus(status: Boolean): String {

        if(status==true){
            return "Ok"
        }
        return "Nok"
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