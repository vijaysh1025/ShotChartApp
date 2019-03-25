package com.vijay.nbashottracker.ui

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.vijay.nbashottracker.R
import com.vijay.nbashottracker.model.dailyschedule.*
import io.reactivex.annotations.NonNull

class GameListAdapter(itemClickListener: GameItemClickListener)
    :RecyclerView.Adapter<GameListAdapter.GameViewHolder>(){

    var isClickable:Boolean = true;
    var games: List<Game> = listOf()
        set(value){
            field = value
            notifyDataSetChanged()
        }

    @NonNull
    private var mOnClickListener:GameItemClickListener?=itemClickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return GameViewHolder(inflater, parent, mOnClickListener, isClickable)
    }

    override fun onBindViewHolder(holder: GameViewHolder, position: Int) {
        val game: Game = games[position]
        holder.bind(game)
    }

    override fun getItemCount(): Int = games.size

    class GameViewHolder(inflater: LayoutInflater, parent: ViewGroup, itemClickListener: GameItemClickListener?, isClickable:Boolean):
        RecyclerView.ViewHolder(inflater.inflate(R.layout.game_item,parent,false)),View.OnClickListener{

        @NonNull
        private var mHomeText:TextView? = null
        private var mAwayText:TextView? = null
        private var mHomeLogo:ImageView? = null
        private var mAwayLogo:ImageView? = null
        private var mGameItemClickListener:GameItemClickListener? = itemClickListener
        private var mGame:Game?=null
        private var isClickable:Boolean = isClickable
        init{
            mHomeText = itemView.findViewById(R.id.homeTeam)
            mAwayText = itemView.findViewById(R.id.awayTeam)
            mHomeLogo = itemView.findViewById(R.id.homTeamLogo)
            mAwayLogo = itemView.findViewById(R.id.awayTeamLogo)
            itemView.setOnClickListener(this)
        }

        fun bind(@NonNull game: Game){
            mHomeText?.text = (game.home as Team).alias
            mAwayText?.text = (game.away as Team).alias
            val homeLogoId = itemView.context.resources.getIdentifier((game.home as Team).alias.toLowerCase(),"drawable", itemView.context.packageName)
            val awayLogoId = itemView.context.resources.getIdentifier((game.away as Team).alias.toLowerCase(),"drawable", itemView.context.packageName)
            mHomeLogo?.setImageResource(homeLogoId)
            mAwayLogo?.setImageResource(awayLogoId)

            mGame = game

        }

        override fun onClick(p0: View?) {
            if(isClickable)
                mGameItemClickListener?.onClickGame(mGame!!)
        }

    }
}

interface GameItemClickListener{
    fun onClickGame(game:Game)
}

