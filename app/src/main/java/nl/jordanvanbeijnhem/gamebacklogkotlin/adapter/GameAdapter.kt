package nl.jordanvanbeijnhem.gamebacklogkotlin.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import nl.jordanvanbeijnhem.gamebacklogkotlin.R
import nl.jordanvanbeijnhem.gamebacklogkotlin.model.Game
import java.text.SimpleDateFormat
import java.util.*


class GameAdapter(private val games: List<Game>) : RecyclerView.Adapter<GameAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_game,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return games.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(games[position])
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val tvTitle: TextView = itemView.findViewById(R.id.tvTitle)
        private val tvPlatform: TextView = itemView.findViewById(R.id.tvPlatform)
        private val tvDate: TextView = itemView.findViewById(R.id.tvDate)

        fun bind(game: Game) {
            tvTitle.text = game.title
            tvPlatform.text = game.platform
            tvDate.text = itemView.context.applicationContext.getString(
                R.string.release,
                SimpleDateFormat("dd MMMM yyyy", Locale.ENGLISH).format(game.releaseDate)
            )
        }
    }
}