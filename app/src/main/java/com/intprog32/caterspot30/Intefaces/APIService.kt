package com.intprog32.caterspot30.Intefaces

import com.intprog32.caterspot30.Data.UserData
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.DELETE
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.PUT // Import PUT

interface ApiService {

    // POST route to register a new user
    @POST("/users")
    fun registerUser(@Body userData: UserData): Call<UserData>

    @GET("/users/validate")
    fun validateUser(
        @Query("email") email: String,
        @Query("password") password: String
    ): Call<ResponseBody>

    // Change this line:
    @GET("/users")
    fun getUserByEmail(@Query("email") email: String): Call<UserData>

    @PUT("/users/{id}")
    fun updateUser(
        @Path("id") id: String,
        @Body userData: UserData
    ): Call<UserData>
}