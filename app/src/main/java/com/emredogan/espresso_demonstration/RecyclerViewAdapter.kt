package com.emredogan.espresso_demonstration

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar

class RecyclerViewAdapter(context: Context): RecyclerView.Adapter<RecyclerViewAdapter.PlayerViewHolder>() {
    var layoutInflater = LayoutInflater.from(context)
    inner class PlayerViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        init {
            itemView.setOnClickListener {
                Snackbar.make(it,playerText.text.toString(),Snackbar.LENGTH_LONG).show()
            }
        }

        val playerText = itemView.findViewById<TextView>(R.id.playerText)
        val playerImage = itemView.findViewById<ImageView>(R.id.playerImage)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayerViewHolder {
        val view = layoutInflater.inflate(R.layout.player_list_item,parent, false)
        val playerViewHolder = PlayerViewHolder(view)
        return playerViewHolder
    }

    override fun getItemCount(): Int {
        return LocationDatabase.location_list.size
    }

    override fun onBindViewHolder(holder: PlayerViewHolder, position: Int) {
        val player = LocationDatabase.location_list[position]
        holder.playerText.text = player.name
        holder.playerImage.setImageDrawable(player.drawableImage)
    }
}