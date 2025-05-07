package com.intprog32.caterspot30.Intefaces

import android.provider.ContactsContract.CommonDataKinds.Email
import com.intprog32.caterspot30.Data.UserData
import okhttp3.ResponseBody // Import ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.DELETE
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    // POST route to register a new user
    @POST("/users")
    fun registerUser(@Body userData: UserData): Call<UserData>

    @GET("/users/validate")
    fun validateUser(
        @Query("email") email: String,
        @Query("password") password: String
    ): Call<ResponseBody> // <--- Changed to Call<ResponseBody>

    // GET route to fetch all users
    /*    @GET("/users")
        fun getUsers(): Call<List<UserData>>*/

    // DELETE route to remove a user
    @DELETE("/users/{id}")
    fun deleteUser(@Path("id") userId: String): Call<Void>
}