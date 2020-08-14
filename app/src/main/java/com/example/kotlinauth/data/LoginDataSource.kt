package com.example.kotlinauth.data

import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import com.example.kotlinauth.MainActivity
import com.example.kotlinauth.data.model.LoggedInUser
import com.example.kotlinauth.ui.login.LoginActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import java.io.IOException

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
class LoginDataSource {

    private lateinit var auth: FirebaseAuth

    var currentUser: FirebaseUser? = null

    fun login(username: String, password: String): Result<LoggedInUser> {
        auth = FirebaseAuth.getInstance()

        try {
            // TODO: handle loggedInUser authentication
            // Check if we can login the user
            auth.signInWithEmailAndPassword(
                username, password).addOnCompleteListener(LoginActivity.create()) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("Login", "signInWithEmail:success")
                    println("SUCCESS!!!!")
                    currentUser = auth.currentUser

                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("Login", "signInWithEmail:failure", task.exception)
                }
            }
            val loggedInUser = LoggedInUser("Test uid", "Jane Doe")
            return Result.Success(loggedInUser)
        } catch (e: Throwable) {
            return Result.Error(IOException("Error logging in", e))
        }
    }

    fun logout() {
        // TODO: revoke authentication
        auth.currentUser?.delete()
        // Move to next activity
        val intent = Intent(LoginActivity.create(), LoginActivity::class.java)
        LoginActivity.create().startActivity(intent)
    }
}