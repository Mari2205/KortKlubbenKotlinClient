package dk.hovdeforlob4.android.kortklubbenclientkotlin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


const val BaseUrl = "http://10.0.2.2:8787/api/acccount/"

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btn_login = findViewById<Button>(R.id.btn_login)
        val editView_username = findViewById<EditText>(R.id.editText_username)
        val editView_password = findViewById<EditText>(R.id.editText_password)




        btn_login.setOnClickListener {
//            val i = Intent(this, GameMenuActivity::class.java)
//            startActivity(i)
            TestGetMyData(editView_username.text.toString(), editView_password.text.toString())
        }
    }

    private fun TestGetMyData(username:String, password:String) {
        val gson:Gson = GsonBuilder()
            .setLenient()
            .create()

        val retrofitBuilder = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(gson))
            .baseUrl(BaseUrl)
            .build()
            .create(ApiInterface::class.java)

//        val user = Login("Mari", "Kode123!")
        val user = Login(username, password)
        val retrofitData = retrofitBuilder.getData(user)

        retrofitData.enqueue(object : Callback<String?> {
            override fun onResponse(call: Call<String?>, response: Response<String?>) {
                if(response.isSuccessful){
                    ShiftPage()
                }
            }

            override fun onFailure(call: Call<String?>, t: Throwable) {
                Log.d("main","error ${t.message.toString()}")
            }
        })
    }

    private fun ShiftPage(){
        val i = Intent(this, GameMenuActivity::class.java)
        startActivity(i)
    }
}