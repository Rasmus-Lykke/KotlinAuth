package com.example.kotlinauth.data

import android.util.Log
import android.widget.Toast
import com.example.kotlinauth.data.model.LoggedInUser
import com.example.kotlinauth.ui.login.LoginActivity
import com.google.firebase.auth.FirebaseUser
import java.io.IOException

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
class LoginDataSource {

    var currentUser: FirebaseUser? = null
    var activity = LoginActivity.create()

    fun logIn(username: String, password: String): Result<LoggedInUser> {

        try {
            // TODO: handle loggedInUser authentication
            // Check if we can login the user
            activity.auth.signInWithEmailAndPassword(username, password)
                .addOnCompleteListener(activity) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d("Login", "signInWithEmail:success")
                        println(">>>> Login success")
                        currentUser = activity.auth.currentUser

                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w("Login", "signInWithEmail:failure", task.exception)
                        println(">>>> Login error")
                        //TODO Return error
                    }
                }
            val loggedInUser = LoggedInUser(activity.auth.currentUser!!.uid, "Jane Doe")
            // Returning Success with data = logged in user
            return Result.Success(loggedInUser)
        } catch (e: Throwable) {
            return Result.Error(IOException("Error logging in", e))
        }
    }

    fun createUser(username: String, password: String): Result<LoggedInUser> {

        try {
            activity.auth.createUserWithEmailAndPassword(username, password)
                .addOnCompleteListener(activity) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d("Create user", "createUserWithEmail:success")
                        currentUser = activity.auth.currentUser
                        println(">>>> Create user success")
                        //TODO Return success

                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w("Create user", "createUserWithEmail:failure", task.exception)
                        Toast.makeText(activity, "Authentication failed.", Toast.LENGTH_SHORT)
                            .show()
                        println(">>>> Create user error")
                        //TODO Return error
                    }
                }
            val loggedInUser = LoggedInUser("Test uid", "Jane Doe")
            return Result.Success(loggedInUser)
        } catch (e: Throwable) {
            return Result.Error(IOException("Error logging in", e))
        }
    }

    fun logOut(): Boolean {
        // TODO: revoke authentication
        activity.auth.signOut()

        return true
    }
}