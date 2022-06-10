package dk.hovdeforlob4.android.kortklubbenclientkotlin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btn_login = findViewById<Button>(R.id.btn_login)
        val editView_username = findViewById<EditText>(R.id.editText_username)
        val editView_password = findViewById<EditText>(R.id.editText_password)


        btn_login.setOnClickListener {
            val i = Intent(this, GameMenuActivity::class.java)
            startActivity(i)
        }
    }
}