package dk.hovdeforlob4.android.kortklubbenclientkotlin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


const val BaseUrl = "https://jsonplaceholder.typicode.com/"

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btn_login = findViewById<Button>(R.id.btn_login)
        val editView_username = findViewById<EditText>(R.id.editText_username)
        val editView_password = findViewById<EditText>(R.id.editText_password)


        TestGetMyData()

        btn_login.setOnClickListener {
            val i = Intent(this, GameMenuActivity::class.java)
            startActivity(i)
        }
    }

    private fun TestGetMyData() {
        val retrofitBuilder = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BaseUrl)
            .build()
            .create(ApiInterface::class.java)

        val retrofitData = retrofitBuilder.getData()

        retrofitData.enqueue(object : Callback<List<TestData>?> {
            override fun onResponse(
                call: Call<List<TestData>?>,
                response: Response<List<TestData>?>) {
                val responseBody = response.body()!!

                for (data in responseBody){
                    val id = data.id
                    val userId = data.userId
                    val title = data.title
                    val body = data.body
                    val temp = ""

                }

            }

            override fun onFailure(call: Call<List<TestData>?>, t: Throwable) {
                Log.d("MainActivity", "onFailure: ${t.message}")
            }
        })
    }
}