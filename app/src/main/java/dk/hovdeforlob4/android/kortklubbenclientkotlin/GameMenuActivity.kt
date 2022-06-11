package dk.hovdeforlob4.android.kortklubbenclientkotlin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import dk.hovdeforlob4.android.kortklubbenclientkotlin.databinding.ActivityGameMainBinding

class GameMenuActivity : AppCompatActivity() {

    private lateinit var binding: ActivityGameMainBinding
    private lateinit var gameArrLst: ArrayList<Game>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        gameArrLst = ArrayList()

        val mockGame = Game("Whist", "Card game for 4 players")
        gameArrLst.add(mockGame)

        binding.listview.isClickable = true
        binding.listview.adapter = GameMenuApapter(this, gameArrLst)


        binding.listview.setOnItemClickListener { parent, view, position, id ->

            val i = Intent(this, WhistRoomActivity::class.java)
//            i.putExtra("gameName", gameArrLst[position].gameName)
//            i.putExtra("gameDescription", gameArrLst[position].description)
            startActivity(i)
        }
    }
}