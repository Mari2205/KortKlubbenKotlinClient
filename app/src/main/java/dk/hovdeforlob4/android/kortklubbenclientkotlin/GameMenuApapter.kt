package dk.hovdeforlob4.android.kortklubbenclientkotlin

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView

class GameMenuApapter(private val context: Activity, private val arrayList: ArrayList<Game>) : ArrayAdapter<Game>(context,
                        R.layout.list_item, arrayList) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val inflater: LayoutInflater = LayoutInflater.from(context)
        val view: View = inflater.inflate(R.layout.list_item, null)

        val gameName: TextView = view.findViewById(R.id.textView_gameName)
        val gameDescription: TextView = view.findViewById(R.id.textView_gameDiscription)

        gameName.text = arrayList[position].gameName
        gameDescription.text = arrayList[position].description


        return view
    }
}