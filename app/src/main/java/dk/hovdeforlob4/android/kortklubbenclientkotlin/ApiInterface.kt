package dk.hovdeforlob4.android.kortklubbenclientkotlin

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiInterface {

    @POST("login")
    fun getData(@Body login:Login): Call<String>
}