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

class SignUpActivity : AppCompatActivity() {

    lateinit var dataBase: DatabaseReference

    companion object {
        const val KEY1 = "com.example.signup.signupActivity.Email"
        const val KEY2 = "com.example.signup.signupActivity.username"


    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val signup = findViewById<Button>(R.id.btnSignUP)
        val user = findViewById<TextInputEditText>(R.id.etUser)
        val password = findViewById<TextInputEditText>(R.id.etPass)
        val email = findViewById<TextInputEditText>(R.id.etEmail)

        signup.setOnClickListener {

            val name = user.text.toString()
            val pass = password.text.toString()
            val Email = email.text.toString()

            val user1 = User(name, Email, pass)

            dataBase = FirebaseDatabase.getInstance().getReference("Users")
            dataBase.child(name).setValue(user1).addOnSuccessListener {
                user.text?.clear()
                password.text?.clear()
                email.text?.clear()


                readData(name)


//                val intentwelcome = Intent(this,HomeActivity::class.java)
//                Toast.makeText(this,"User Regester",Toast.LENGTH_SHORT ).show()
//                startActivity(intentwelcome)
            }.addOnFailureListener {

                Toast.makeText(this, "User Not regester", Toast.LENGTH_SHORT).show()


            }


        }
        val all = findViewById<TextView>(R.id.allready)

        all.setOnClickListener {
            val intent = Intent(this, LoginINActivity::class.java)
            startActivity(intent)
        }

    }

    private fun readData(usernamedata: String) {
        dataBase = FirebaseDatabase.getInstance().getReference("Users")
        dataBase.child(usernamedata).get().addOnSuccessListener {

            ///if user exiset

            if (it.exists()) {
                ///Welcome User to your app ,With intent
                val email = it.child("email").value
                val user = it.child("username").value

                val intentWelcome = Intent(this, HomeActivity::class.java)

                intentWelcome.putExtra(LoginINActivity.KEY1, email.toString())
                intentWelcome.putExtra(LoginINActivity.KEY2, user.toString())
                startActivity(intentWelcome)

            } else {
                Toast.makeText(this, "User does Not Exist", Toast.LENGTH_SHORT).show()
            }

        }.addOnFailureListener {
            Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show()
        }

    }
}