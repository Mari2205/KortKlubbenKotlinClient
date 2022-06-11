package dk.hovdeforlob4.android.kortklubbenclientkotlin

import android.content.ClipData
import android.content.ClipDescription
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.DragEvent
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.core.view.contains
import dk.hovdeforlob4.android.kortklubbenclientkotlin.databinding.ActivityWhistRoomBinding

class WhistRoomActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWhistRoomBinding
    private lateinit var playingCardsArrLst: ArrayList<PlayingCard>
    private lateinit var llPlayedCards : LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWhistRoomBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val playingCards = arrayOf(
            PlayingCard("", R.drawable.ic_ace_of_diamonds),
            PlayingCard("", R.drawable.ic_2_of_diamonds),
            PlayingCard("", R.drawable.ic_3_of_diamonds),
            PlayingCard("", R.drawable.ic_4_of_diamonds),
            PlayingCard("", R.drawable.ic_5_of_diamonds),
            PlayingCard("", R.drawable.ic_6_of_diamonds),
            PlayingCard("", R.drawable.ic_7_of_diamonds),
            PlayingCard("", R.drawable.ic_8_of_diamonds),
            PlayingCard("", R.drawable.ic_9_of_diamonds),
            PlayingCard("", R.drawable.ic_10_of_diamonds),
            PlayingCard("", R.drawable.ic_jack_of_diamonds),
            PlayingCard("", R.drawable.ic_queen_of_diamonds),
            PlayingCard("", R.drawable.ic_king_of_diamonds)
        )

        playingCardsArrLst = ArrayList()

        for (card in playingCards) {
            playingCardsArrLst.add(card)
        }

        val llCards = findViewById<LinearLayout>(R.id.ll_cards)
        llPlayedCards = findViewById<LinearLayout>(R.id.ll_cardPlayed)

        val imageViewCard1 = findViewById<ImageView>(R.id.imageView_card1)
        val imageViewCard2 = findViewById<ImageView>(R.id.imageView_card2)
        val imageViewCard3 = findViewById<ImageView>(R.id.imageView_card3)
        val imageViewCard4 = findViewById<ImageView>(R.id.imageView_card4)
        val imageViewCard5 = findViewById<ImageView>(R.id.imageView_card5)
        val imageViewCard6 = findViewById<ImageView>(R.id.imageView_card6)


        llPlayedCards.setOnDragListener(dragListener)
        llCards.setOnDragListener(dragListener)





        imageViewCard1.setOnLongClickListener {
            val clipText = "this is our clipdata text"
            val item = ClipData.Item(clipText)
            val mimeTypes = arrayOf(ClipDescription.MIMETYPE_TEXT_PLAIN)
            val data = ClipData(clipText, mimeTypes, item)

            val dragShadowBuilder = View.DragShadowBuilder(it)
            it.startDragAndDrop(data, dragShadowBuilder, it, 0)


            it.visibility = View.INVISIBLE
            true
        }
//        binding.listviewCards.isClickable = true
//        binding.listviewCards.adapter = CardsApapter(this, playingCardsArrLst)
        //binding.recylerViewCards.adapter = CardsApapter(this, playingCardsArrLst)

//        binding.listviewCards.setOnDragListener { view, dragEvent ->
//            val drag = dragEvent
//            val vie = view
//           val t = ""
//            true
//        }


    }

    val dragListener = View.OnDragListener { view, dragEvent ->
        when (dragEvent.action) {
            DragEvent.ACTION_DRAG_STARTED -> {
                dragEvent.clipDescription.hasMimeType(ClipDescription.MIMETYPE_TEXT_PLAIN)
            }
            DragEvent.ACTION_DRAG_ENTERED -> {
                view.invalidate()
                true
            }
            DragEvent.ACTION_DRAG_LOCATION -> true
            DragEvent.ACTION_DRAG_EXITED -> {
                view.invalidate()
                true
            }
            DragEvent.ACTION_DROP -> {
                val item = dragEvent.clipData.getItemAt(0)
                val dragData = item.text
                Toast.makeText(this, dragData, Toast.LENGTH_SHORT).show()

                view.invalidate()

                val v = dragEvent.localState as View
                val owner = v.parent as ViewGroup
                owner.removeView(v)
                val destination = view as LinearLayout
                destination.addView(v)
                v.visibility = View.VISIBLE

                true
            }
            DragEvent.ACTION_DRAG_ENDED -> {
                view.invalidate()
                GetPlayedCard()
                true
            }
            else -> false
        }

    }

    fun GetPlayedCard(){

        val t = llPlayedCards.context
        val tt = t.resources
        val f = llPlayedCards

        val g: ImageView = llPlayedCards.getChildAt(0) as ImageView
        val gg = g.context
        val ggg = g.id
        val gggg = gg.resources

        val ggggg = g.drawable
        val gggggg = g.toString()
        val h = gggggg.replaceBefore("imageView", "")
        val hh = h.replace("}", "")

        val temp = ""


    }
}