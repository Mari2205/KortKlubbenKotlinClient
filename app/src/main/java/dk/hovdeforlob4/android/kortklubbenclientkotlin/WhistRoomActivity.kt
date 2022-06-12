package dk.hovdeforlob4.android.kortklubbenclientkotlin

import android.content.ClipData
import android.content.ClipDescription
import android.content.res.Resources
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.DragEvent
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.core.view.contains
import androidx.core.view.get
import androidx.core.view.iterator
import com.microsoft.signalr.HubConnection
import com.microsoft.signalr.HubConnectionBuilder
import com.microsoft.signalr.HubConnectionState
import dk.hovdeforlob4.android.kortklubbenclientkotlin.databinding.ActivityWhistRoomBinding
import java.sql.DriverManager

class WhistRoomActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWhistRoomBinding
    private lateinit var playingCardsArrLst: ArrayList<PlayingCard>
    private lateinit var llPlayedCards : LinearLayout
    private lateinit var llCards : LinearLayout
    lateinit var hubConnection: HubConnection
    private val hashmapIds:HashMap<Int, Int> = HashMap<Int, Int>()

    //TODO: delect cards apapter if not used
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWhistRoomBinding.inflate(layoutInflater)
        setContentView(binding.root)


        hubConnection = HubConnectionBuilder.create("http://10.0.2.2:5038/cardhub").build()
        hubConnection.start()


        val playingCards = arrayOf(
            PlayingCard("ace of diamonds",      R.drawable.ic_ace_of_diamonds),
            PlayingCard("2 of diamonds",        R.drawable.ic_2_of_diamonds),
            PlayingCard("3 of diamonds",        R.drawable.ic_3_of_diamonds),
            PlayingCard("4 of diamonds",        R.drawable.ic_4_of_diamonds),
            PlayingCard("5 of diamonds",        R.drawable.ic_5_of_diamonds),
            PlayingCard("6 of diamonds",        R.drawable.ic_6_of_diamonds),
            PlayingCard("7 of diamonds",        R.drawable.ic_7_of_diamonds),
            PlayingCard("8 of diamonds",        R.drawable.ic_8_of_diamonds),
            PlayingCard("9 of diamonds",        R.drawable.ic_9_of_diamonds),
            PlayingCard("10 of diamonds",       R.drawable.ic_10_of_diamonds),
            PlayingCard("jack of diamonds",     R.drawable.ic_jack_of_diamonds),
            PlayingCard("queen of diamonds",    R.drawable.ic_queen_of_diamonds),
            PlayingCard("king of diamonds",     R.drawable.ic_king_of_diamonds)
        )

        playingCardsArrLst = ArrayList()

        for (card in playingCards) {
            playingCardsArrLst.add(card)
        }


        llCards = findViewById<LinearLayout>(R.id.ll_cards)
        llPlayedCards = findViewById<LinearLayout>(R.id.ll_cardPlayed)

        GiveCards(playingCardsArrLst)

        val imageViewCard1 = findViewById<ImageView>(R.id.imageView_card1)
        val imageViewCard2 = findViewById<ImageView>(R.id.imageView_card2)
        val imageViewCard3 = findViewById<ImageView>(R.id.imageView_card3)
        val imageViewCard4 = findViewById<ImageView>(R.id.imageView_card4)
        val imageViewCard5 = findViewById<ImageView>(R.id.imageView_card5)
        val imageViewCard6 = findViewById<ImageView>(R.id.imageView_card6)


        llPlayedCards.setOnDragListener(dragListener)
        llCards.setOnDragListener(dragListener)

        imageViewCard1.setOnLongClickListener(imageViewSetup)
        imageViewCard2.setOnLongClickListener(imageViewSetup)
        imageViewCard3.setOnLongClickListener(imageViewSetup)
        imageViewCard4.setOnLongClickListener(imageViewSetup)
        imageViewCard5.setOnLongClickListener(imageViewSetup)
        imageViewCard6.setOnLongClickListener(imageViewSetup)

    }


    val dragListener = View.OnDragListener { view, dragEvent ->
        when (dragEvent.action) {
            DragEvent.ACTION_DRAG_STARTED -> {
                dragEvent.clipDescription.hasMimeType(ClipDescription.MIMETYPE_TEXT_PLAIN)
            }
            DragEvent.ACTION_DRAG_ENTERED -> {
                view.invalidate()
                RemovePreviousCard()
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


    val imageViewSetup = View.OnLongClickListener {
        val clipText = "Card Played"
        val item = ClipData.Item(clipText)
        val mimeTypes = arrayOf(ClipDescription.MIMETYPE_TEXT_PLAIN)
        val data = ClipData(clipText, mimeTypes, item)

        val dragShadowBuilder = View.DragShadowBuilder(it)
        it.startDragAndDrop(data, dragShadowBuilder, it, 0)


        it.visibility = View.INVISIBLE
        true
    }

    //TODO: sends boradcast msg two times
    /**
     * this method find the name of whitch card played
     */
    private fun GetPlayedCard(){

        for (imageview in llPlayedCards){
            for (item in hashmapIds){
                if (item.key == imageview.id){
                    for (card in playingCardsArrLst){
                        if (item.value == card.imageId){
                            val name =  card.cardName
                            BoradcastCard(name)
                        }
                    }
                }
            }
        }
    }


    /**
     * Removes previous card from table
     */
    private fun RemovePreviousCard(){
        llPlayedCards.removeAllViews()

    }


    /**
     * this method should give cards recvide from the server
     * but it gives a set off mock data whit randomized cards of the type haerts
     */
    private fun GiveCards(cards:ArrayList<PlayingCard>){
        val imageViewCounts = llCards.childCount

        for (i in 0..imageViewCounts -1){
            val randomNo = (0..cards.size-1).random()
            val cardId = cards[randomNo].imageId
            val imageview = llCards[i]

            imageview.setBackgroundResource(cardId)
            hashmapIds[imageview.id] = cardId

        }
    }

    //TODO: sends boradcast msg two times
    /**
     * this method boradcast out on the signal R web socket
     */
    private fun BoradcastCard(cardName: String){
        try {
            //Note: need plain text permission in manifest
            val status = hubConnection.connectionState

            if (hubConnection.connectionState == HubConnectionState.CONNECTED) {
                hubConnection.send("SendCard", "Android Client", cardName)
            }
        } catch (ex: Exception) {
            DriverManager.println("ex")
        }
    }
}