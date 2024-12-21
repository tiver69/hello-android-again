package com.example.helloandroidagain.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.example.helloandroidagain.recyclerview.TournamentListAdapter.TournamentViewHolder
import com.example.helloandroidagain.databinding.ItemTournamentActiveBinding
import com.example.helloandroidagain.databinding.ItemTournamentOutdatedBinding
import com.example.helloandroidagain.model.Tournament
import com.example.helloandroidagain.util.convertToString
import java.time.LocalDate

typealias TournamentType = TournamentViewHolder.Companion.TournamentItemType

class TournamentListAdapter : RecyclerView.Adapter<TournamentViewHolder>() {

    var tournaments: List<Tournament> = emptyList()
        set(newValue) {
            val diffResult = DiffUtil.calculateDiff(TournamentListDiffCallback(field, newValue))
            field = newValue
            diffResult.dispatchUpdatesTo(this)
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TournamentViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return if (viewType == TournamentType.ACTIVE.viewType) {
            val binding = ItemTournamentActiveBinding.inflate(inflater, parent, false)
            TournamentActiveViewHolder(binding)
        } else {
            val binding = ItemTournamentOutdatedBinding.inflate(inflater, parent, false)
            TournamentOutdatedViewHolder(binding)
        }
    }

    override fun onBindViewHolder(holder: TournamentViewHolder, position: Int) {
        val tournament = tournaments[position]
        holder.bindTournamentItem(tournament)
    }

    override fun getItemCount(): Int = tournaments.size

    override fun getItemViewType(position: Int): Int =
        if (tournaments[position].date.isBefore(LocalDate.now())) TournamentType.OUTDATED.viewType
        else TournamentType.ACTIVE.viewType

    sealed class TournamentViewHolder(
        val binding: ViewBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        open fun bindTournamentItem(tournament: Tournament) {}

        companion object {
            enum class TournamentItemType(val viewType: Int) {
                ACTIVE(1), OUTDATED(0)
            }
        }
    }

    class TournamentActiveViewHolder(binding: ItemTournamentActiveBinding) :
        TournamentViewHolder(binding) {

        override fun bindTournamentItem(tournament: Tournament) {
            with(binding as ItemTournamentActiveBinding) {
                tournamentItemId.text = tournament.id.toString(10)
                tournamentItemName.text = tournament.name
                tournamentItemParticipantCount.text =
                    tournament.participantCount.toString(10)
                tournamentItemDate.text =
                    tournament.date.convertToString()
            }
        }
    }

    class TournamentOutdatedViewHolder(binding: ItemTournamentOutdatedBinding) :
        TournamentViewHolder(binding) {
        override fun bindTournamentItem(tournament: Tournament) {
            with(binding as ItemTournamentOutdatedBinding) {
                tournamentItemId.text = tournament.id.toString(10)
                tournamentItemName.text = tournament.name
                tournamentItemParticipantCount.text =
                    tournament.participantCount.toString(10)
                tournamentItemDate.text =
                    tournament.date.convertToString()
            }
        }
    }
}

class TournamentListDiffCallback(
    private val oldTournamentList: List<Tournament>,
    private val newTournamentList: List<Tournament>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldTournamentList.size

    override fun getNewListSize(): Int = newTournamentList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldTournamentList[oldItemPosition].id == newTournamentList[newItemPosition].id

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldTournamentList[oldItemPosition] == newTournamentList[newItemPosition]
}