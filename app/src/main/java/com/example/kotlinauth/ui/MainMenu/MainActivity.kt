package com.example.kotlinauth.ui.MainMenu

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProviders
import com.example.kotlinauth.R
import com.example.kotlinauth.ui.login.LoginActivity
import com.example.kotlinauth.ui.login.LoginViewModel
import com.example.kotlinauth.ui.login.LoginViewModelFactory

private lateinit var loginViewModel: LoginViewModel

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        loginViewModel = ViewModelProviders.of(this, LoginViewModelFactory())
            .get(LoginViewModel::class.java)
    }

    fun logOut(view: View) {

        val result =  loginViewModel.logOut()

        if (result) {
            // Move to next activity
            val intent = Intent(this, LoginActivity::class.java)
            // Specify any activity here e.g. home or splash or login etc
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            intent.putExtra("EXIT", true)
            startActivity(intent)
            finish()
        } else {
            println("Error signing out")
        }

    }
}