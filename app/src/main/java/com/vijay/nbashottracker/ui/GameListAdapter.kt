package com.vijay.nbashottracker.ui

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.vijay.nbashottracker.R
import com.vijay.nbashottracker.model.dailyschedule.*
import io.reactivex.annotations.NonNull

class GameListAdapter(itemClickListener: GameItemClickListener)
    :RecyclerView.Adapter<GameListAdapter.GameViewHolder>(){

    var games: List<Game> = listOf()
        set(value){
            field = value
            notifyDataSetChanged()
        }

    @NonNull
    private var mOnClickListener:GameItemClickListener?=itemClickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return GameViewHolder(inflater, parent, mOnClickListener)
    }

    override fun onBindViewHolder(holder: GameViewHolder, position: Int) {
        val game: Game = games[position]
        holder.bind(game)
    }

    override fun getItemCount(): Int = games.size

    class GameViewHolder(inflater: LayoutInflater, parent: ViewGroup, itemClickListener: GameItemClickListener?):
        RecyclerView.ViewHolder(inflater.inflate(R.layout.game_item,parent,false)),View.OnClickListener{

        @NonNull
        private var mHomeText:TextView? = null
        private var mAwayText:TextView? = null
        private var mGameItemClickListener:GameItemClickListener? = itemClickListener
        private var mGame:Game?=null

        init{
            mHomeText = itemView.findViewById(R.id.homeTeam)
            mAwayText = itemView.findViewById(R.id.awayTeam)
            itemView.setOnClickListener(this)
        }

        fun bind(@NonNull game: Game){
            mHomeText?.text = (game.home as Team).alias
            mAwayText?.text = (game.away as Team).alias
            mGame = game;
        }

        override fun onClick(p0: View?) {
            mGameItemClickListener?.onClickGame(mGame!!)
        }

    }
}

interface GameItemClickListener{
    fun onClickGame(game:Game)
}

