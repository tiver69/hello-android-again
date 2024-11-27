package com.example.helloandroidagain

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.helloandroidagain.databinding.ItemTournamentBinding
import com.example.helloandroidagain.model.Tournament
import java.time.format.DateTimeFormatter

interface TournamentActionListener {
    fun onTournamentRemove(tournament: Tournament)
}

class TournamentListAdapter(private val actionListener: TournamentActionListener) :
    RecyclerView.Adapter<TournamentListAdapter.TournamentViewHolder>(), View.OnClickListener {

    var tournaments: List<Tournament> = emptyList()
        set(newValue) {
            field = newValue
            notifyDataSetChanged()
        }

    class TournamentViewHolder(
        val binding: ItemTournamentBinding
    ) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TournamentViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemTournamentBinding.inflate(inflater, parent, false)

        binding.tournamentItemRemoveButton.setOnClickListener(this)
        return TournamentViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TournamentViewHolder, position: Int) {
        val tournament = tournaments[position]
        holder.binding.tournamentItemName.text = tournament.name
        holder.binding.tournamentItemParticipantCount.text =
            tournament.participantCount.toString(10)
        holder.binding.tournamentItemDate.text =
            tournament.date.format(DateTimeFormatter.ISO_LOCAL_DATE)
        holder.binding.tournamentItemRemoveButton.tag = tournament
    }

    override fun getItemCount(): Int = tournaments.size

    override fun onClick(removeButtonView: View) {
        actionListener.onTournamentRemove(removeButtonView.tag as Tournament)
    }
}