package org.d3if3139.trackerex.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.d3if3139.trackerex.model.Expense
import org.d3if3139.trackerex.model.MessageResponse
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

private const val BASE_URL = "https://expense-tracker-abid.vercel.app/"


private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()



private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()


interface UserApi {
    @Multipart
    @POST("expenses/")
    suspend fun addData(
        @Part("title") title: RequestBody,
        @Part("type") type: RequestBody,
        @Part("price") price: RequestBody,
        @Part("user_email") userEmail: RequestBody,
        @Part file: MultipartBody.Part
    ): Expense

    @GET("expenses/")
    suspend fun getAllData(
        @Query("email") email: String,
    ): List<Expense>

    @GET("expenses/total")
    suspend fun getTotalExpenses(
        @Query("email") email: String,
    ): Int

    @DELETE("expenses/{expense_id}")
    suspend fun deleteData(
        @Path("expense_id") id: Int,
        @Query("email") email: String
    ): MessageResponse
}


object Api {
    val userService: UserApi by lazy {
        retrofit.create(UserApi::class.java)
    }

    fun getImageUrl(imageId: String): String{
        return BASE_URL + "expenses/images/$imageId"
    }
}

enum class ApiStatus { LOADING, SUCCESS, FAILED }