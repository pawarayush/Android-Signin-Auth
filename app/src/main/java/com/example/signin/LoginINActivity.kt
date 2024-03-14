package com.example.signin

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class LoginINActivity : AppCompatActivity() {
    lateinit var databaseReference: DatabaseReference
    companion object{
        const val KEY1 = "com.example.signin.signinActivity.Email"
        const val KEY2 = "com.example.signin.signinActivity.username"


    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login_inactivity)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val UserName = findViewById<TextInputEditText>(R.id.ltUser)
        val passWord = findViewById<TextInputEditText>(R.id.ltPass)
        val login = findViewById<Button>(R.id.btnlogin)
        val regester = findViewById<TextView>(R.id.regester)


        regester.setOnClickListener{
            val singup = Intent(this,SignUpActivity::class.java)
            startActivity(singup)
        }

        login.setOnClickListener{

            val Usernamedata = UserName.text.toString()
//            val PasswordData = passWord.text.toString()

            if(Usernamedata.isNotEmpty() ){
                readData(Usernamedata)


            }
            else{
                Toast.makeText(this,"Please enter the Username",Toast.LENGTH_SHORT).show()
            }



        }




    }



    private fun readData(usernamedata: String ) {
        databaseReference = FirebaseDatabase.getInstance().getReference("Users")
        databaseReference.child(usernamedata).get().addOnSuccessListener {

            ///if user exiset

            if(it.exists()){
                ///Welcome User to your app ,With intent
                val email = it.child("email").value
                val user = it.child("username").value

                val intentWelcome = Intent(this,HomeActivity::class.java)

                intentWelcome.putExtra(KEY1,email.toString())
                intentWelcome.putExtra(KEY2,user.toString())
                startActivity(intentWelcome )

            }
            else{
                val resignin = Intent(this,SignUpActivity::class.java)
                Toast.makeText(this,"Please First SignUP \n" +
                        "As User Do Not Exist",Toast.LENGTH_LONG).show()
                startActivity(resignin)
            }

        }.addOnFailureListener{
            Toast.makeText(this,"Failed",Toast.LENGTH_SHORT).show()
        }


    }




}