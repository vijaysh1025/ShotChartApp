package com.vijay.nbashottracker

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.vijay.nbashottracker.model.dailyschedule.*
import io.reactivex.annotations.NonNull

class GameListAdapter
    :RecyclerView.Adapter<GameListAdapter.GameViewHolder>(){

    var games: List<Game> = listOf()
        set(value){
            field = value
            notifyDataSetChanged()
        }

    private var mOnClickListener:View.OnClickListener?=null

    fun setOnItemClickListener(itemClickListener:View.OnClickListener){
        mOnClickListener = itemClickListener;
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return GameViewHolder(inflater, parent, mOnClickListener)
    }

    override fun onBindViewHolder(holder: GameViewHolder, position: Int) {
        val game: Game = games[position]
        val home:String = (game.home as Team).alias
        val away:String = (game.away as Team).alias

        holder.bind(home,away)
    }

    override fun getItemCount(): Int = games.size

    class GameViewHolder(inflater: LayoutInflater, parent: ViewGroup, itemClickListener: View.OnClickListener?):
        RecyclerView.ViewHolder(inflater.inflate(R.layout.game_item,parent,false)){
        @NonNull
        private var mHomeText:TextView? = null
        private var mAwayText:TextView? = null

        init{
            mHomeText = itemView.findViewById(R.id.homeTeam)
            mAwayText = itemView.findViewById(R.id.awayTeam)
            itemView.setOnClickListener(itemClickListener)
        }

        fun bind(@NonNull home: String, away:String){
            mHomeText?.text = home
            mAwayText?.text = away
        }

    }
}

