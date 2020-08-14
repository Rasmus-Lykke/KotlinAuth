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
    var activity = LoginActivity.create()

    fun login(username: String, password: String): Result<LoggedInUser> {

        auth = FirebaseAuth.getInstance()

        try {
            // TODO: handle loggedInUser authentication
            // Check if we can login the user
            auth.signInWithEmailAndPassword(username, password)
                .addOnCompleteListener(activity) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d("Login", "signInWithEmail:success")
                        println("SUCCESS!!!!")
                        currentUser = auth.currentUser
                        //TODO Return success

                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w("Login", "signInWithEmail:failure", task.exception)
                        println("Test 3")
                        //TODO Return error
                    }
                }
            val loggedInUser = LoggedInUser("Test uid", "Jane Doe")
            return Result.Success(loggedInUser)
        } catch (e: Throwable) {
            return Result.Error(IOException("Error logging in", e))
        }
    }

    fun createUser(username: String, password: String): Result<LoggedInUser> {
        
        auth = FirebaseAuth.getInstance()

        try {
            auth.createUserWithEmailAndPassword(username, password)
                .addOnCompleteListener(activity) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d("Create user", "createUserWithEmail:success")
                        currentUser = auth.currentUser

                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w("Create user", "createUserWithEmail:failure", task.exception)
                        Toast.makeText(activity, "Authentication failed.", Toast.LENGTH_SHORT)
                            .show()
                        println("Test1")
                    }
                }
            val loggedInUser = LoggedInUser("Test uid", "Jane Doe")
            return Result.Success(loggedInUser)
        } catch (e: Throwable) {
            println("Test2: $e")
            return Result.Error(IOException("Error logging in", e))
        }
    }

    fun logout() {
        // TODO: revoke authentication
        auth.currentUser?.delete()
        // Move to next activity
        val intent = Intent(activity, LoginActivity::class.java)
        // Specify any activity here e.g. home or splash or login etc
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.putExtra("EXIT", true)
        activity.startActivity(intent)
        activity.finish()
    }
}