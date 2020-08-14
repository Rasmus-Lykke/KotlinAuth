package com.example.kotlinauth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class LogInActivityTest : AppCompatActivity() {

    var editTextEmail: EditText? = null
    var editTextPassword: EditText? = null
    var textViewCreateUser: TextView? = null
    var buttonLogin: Button? = null

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_test)

        auth = FirebaseAuth.getInstance()
        editTextEmail = findViewById(R.id.editTextEmail)
        editTextPassword = findViewById(R.id.editTextPassword)

        buttonLogin = findViewById(R.id.buttonLogin)
        // disable login button unless both username / password is valid


        if (auth.currentUser != null) {
            // User is already logged in
            login()
        }

    }

    fun loginClicked(view: View) {
        // Check if we can login the user
        auth.signInWithEmailAndPassword(
            editTextEmail?.text.toString(),
            editTextPassword?.text.toString()
        )
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("Login", "signInWithEmail:success")
                    login()
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("Login", "signInWithEmail:failure", task.exception)
                    Toast.makeText(this, "Login failed. Try again.", Toast.LENGTH_SHORT).show()
                    logInFailed()
                }
            }
    }

    private fun login() {

        // Move to next activity
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)

    }

    private fun logInFailed() {

        // Clear password field
        editTextPassword?.text?.clear()

    }

    fun createUserClicked(view: View) {
        auth.createUserWithEmailAndPassword(
            editTextEmail?.text.toString(),
            editTextPassword?.text.toString()
        )
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("Create user", "createUserWithEmail:success")
                    val user = auth.currentUser
                    login()

                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("Create user", "createUserWithEmail:failure", task.exception)
                    Toast.makeText(
                        baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT
                    ).show()
                    createUserFailed()
                }

                // ...
            }
    }

    private fun createUserFailed() {
        Toast.makeText(
            baseContext, "Failed to create user.",
            Toast.LENGTH_SHORT
        ).show()
    }
}