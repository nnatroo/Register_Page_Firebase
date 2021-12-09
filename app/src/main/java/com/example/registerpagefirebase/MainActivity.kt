package com.example.registerpagefirebase

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    private lateinit var editTextEmail : EditText
    private lateinit var editTextPassword : EditText
    private lateinit var buttonRegister : Button
    private lateinit var editTextRepeatPassword : EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        init()
        registerListeners()

    }

    private fun init(){
        editTextEmail = findViewById(R.id.editTextEmail)
        editTextPassword = findViewById(R.id.editTextPassword)
        buttonRegister = findViewById(R.id.buttonRegister)
        editTextRepeatPassword = findViewById(R.id.editTextRepeatPassword)

    }

    private fun registerListeners() {
        buttonRegister.setOnClickListener {

            val email = editTextEmail.text.toString()
            val password = editTextPassword.text.toString()
            val repeatedPassword = editTextRepeatPassword.text.toString()

            if(email.isEmpty() || password.isEmpty() || repeatedPassword.isEmpty()) {
                Toast.makeText(this, "Empty", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if(!email.contains("@")){
                editTextEmail.error = "Email must contain \"@\""
                return@setOnClickListener
            }

            if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                editTextEmail.error = "Enter valid e-mail address. Try again."
                return@setOnClickListener
            }

            if(password.length < 9){
                editTextPassword.error = "Use 9 characters or more for your password"
                return@setOnClickListener

            }

            if(!password.matches(".*[0-9].*".toRegex())){
                editTextPassword.error = "Password must contain digits also"
                return@setOnClickListener

            }


            if((!password.matches(".*[a-z].*".toRegex())) && (!password.matches(".*[A-Z].*".toRegex()))){
                editTextPassword.error = "Password must contain letters also"
                return@setOnClickListener
            }

            if(password != repeatedPassword){
                editTextRepeatPassword.error = "Those passwords didn’t match. Try again."
                return@setOnClickListener

            }


            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener{task ->
                    if (task.isSuccessful) {
                        Toast.makeText(this, "Registration Completed", Toast.LENGTH_SHORT).show()

                    }else {
                        Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()
                    }

                }


        }


    }








}
