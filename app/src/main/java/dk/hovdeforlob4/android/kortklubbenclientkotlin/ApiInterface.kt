package dk.hovdeforlob4.android.kortklubbenclientkotlin

import retrofit2.Call
import retrofit2.http.GET

interface ApiInterface {

    @GET("posts")
    fun getData(): Call<List<TestData>>
}