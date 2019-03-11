package com.vijay.nbashottracker

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.vijay.nbashottracker.model.Game
import com.vijay.nbashottracker.model.Team
import io.reactivex.annotations.NonNull
import io.reactivex.annotations.Nullable

class GameListAdapter
    :RecyclerView.Adapter<GameViewHolder>(){

    var games: List<Game> = listOf()
        set(value){
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return GameViewHolder(inflater, parent)
    }

    override fun onBindViewHolder(holder: GameViewHolder, position: Int) {
        val game:Game = games[position]
        val home:String = (game.home as Team).name
        val away:String = (game.away as Team).name
        val label:String = home + " vs " + away
        holder.bind(label)
    }

    override fun getItemCount(): Int = games.size
}

class GameViewHolder(inflater: LayoutInflater, parent: ViewGroup):
    RecyclerView.ViewHolder(inflater.inflate(R.layout.game_item,parent,false)){
    @NonNull
    private var mText:TextView? = null

    init{
        mText = itemView.findViewById(R.id.gameName)
    }

    fun bind(@NonNull text:String){mText?.text = text}

}