package com.test.food.data.repositories

import com.test.food.data.db.AppDatabase
import com.test.food.data.db.entities.User
import com.test.food.data.network.MyApi
import com.test.food.data.network.SafeApiRequest
import com.test.food.data.network.params.LoginParams
import com.test.food.data.network.responses.AuthResponse

class UserRepository(
    private val api: MyApi,
    private val db: AppDatabase
) : SafeApiRequest() {

    /*
    * call API login
    * */
    suspend fun userLogin(username: String, password: String): AuthResponse {
        return apiRequest { api.userLogin(
            LoginParams(
                username,
                password,
                "password",
                "test.token.test",
                "ANDROID"
            )
        ) }
    }

    /*
    * save user to local database
    * */
    suspend fun saveUser(user: User) = db.getUserDao().upsert(user)

    /*get user from DB*/
    fun getUser() = db.getUserDao().getuser()

}