package dk.hovdeforlob4.android.kortklubbenclientkotlin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import dk.hovdeforlob4.android.kortklubbenclientkotlin.databinding.ActivityMainBinding

class GameMenuActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.listview.isClickable = true
        binding.listview.adapter = MyAdapter(this, arrayLst)


        val i = Intent(this, WhistRoomActivity::class.java)
        startActivity(i)
    }
}