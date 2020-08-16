package com.example.kotlinauth.data

import com.example.kotlinauth.data.model.LoggedInUser

/**
 * Class that requests authentication and user information from the remote data source and
 * maintains an in-memory cache of login status and user credentials information.
 */

class LoginRepository(val dataSource: LoginDataSource) {

    // in-memory cache of the loggedInUser object
    var user: LoggedInUser? = null
        private set

    val isLoggedIn: Boolean
        get() = user != null

    init {
        // If user credentials will be cached in local storage, it is recommended it be encrypted
        // @see https://developer.android.com/training/articles/keystore
        user = null
    }


    fun login(username: String, password: String): Result<LoggedInUser> {
        println("Step 3")
        // handle login
        val result = dataSource.logIn(username, password)

        if (result is Result.Success) {
            println(">>>> In LoginViewModel, result = success")
            setLoggedInUser(result.data)
        }

        return result
    }

    fun createUser(username: String, password: String): Result<LoggedInUser> {
        val result = dataSource.createUser(username, password)

        if (result is Result.Success) {
            setLoggedInUser(result.data)
        }

        return result
    }

    fun logOut(): Boolean{
        // handle login
        val result = dataSource.logOut()

        if (!result) {
            println(">>>> In LoginViewModel, logout successful")
            user = null
        }

        return result
    }

    private fun setLoggedInUser(loggedInUser: LoggedInUser) {
        this.user = loggedInUser
        // If user credentials will be cached in local storage, it is recommended it be encrypted
        // @see https://developer.android.com/training/articles/keystore
    }
}