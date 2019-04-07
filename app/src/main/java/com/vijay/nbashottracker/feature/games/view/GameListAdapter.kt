package com.vijay.nbashottracker.feature.games.view

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.vijay.nbashottracker.R
import com.vijay.nbashottracker.feature.games.state.objects.GameItem
import io.reactivex.annotations.NonNull

class GameListAdapter(itemClickListener: GameItemClickListener)
    : RecyclerView.Adapter<GameListAdapter.GameViewHolder>(){

    var isClickable:Boolean = true;
    var games: List<GameItem> = listOf()
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
        val game: GameItem = games[position]
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
        private var mGame: GameItem?=null
        private var isClickable:Boolean = isClickable
        init{
            mHomeText = itemView.findViewById(R.id.homeTeam)
            mAwayText = itemView.findViewById(R.id.awayTeam)
            mHomeLogo = itemView.findViewById(R.id.homTeamLogo)
            mAwayLogo = itemView.findViewById(R.id.awayTeamLogo)
            itemView.setOnClickListener(this)
        }

        fun bind(@NonNull game: GameItem){
            mHomeText?.text = game.homeTeam.alias
            mAwayText?.text = game.awayTeam.alias
            val homeLogoId = itemView.context.resources.getIdentifier(game.homeTeam.alias.toLowerCase(),"drawable", itemView.context.packageName)
            val awayLogoId = itemView.context.resources.getIdentifier(game.awayTeam.alias.toLowerCase(),"drawable", itemView.context.packageName)
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
    fun onClickGame(game: GameItem)
}

