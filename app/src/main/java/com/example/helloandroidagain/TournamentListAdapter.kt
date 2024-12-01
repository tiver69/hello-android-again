package com.example.helloandroidagain

import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.example.helloandroidagain.TournamentListAdapter.TournamentViewHolder
import com.example.helloandroidagain.databinding.ItemTournamentActiveBinding
import com.example.helloandroidagain.databinding.ItemTournamentOutdatedBinding
import com.example.helloandroidagain.model.Tournament
import java.time.LocalDate
import java.time.format.DateTimeFormatter

interface TournamentActionListener {
    fun onTournamentRemove(tournament: Tournament)
}
typealias TournamentType = TournamentViewHolder.Companion.TournamentItemType

class TournamentListAdapter(private val actionListener: TournamentActionListener) :
    RecyclerView.Adapter<TournamentViewHolder>(), OnClickListener {

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
        holder.bindTournamentItem(tournament, this@TournamentListAdapter)
    }

    override fun getItemCount(): Int = tournaments.size

    override fun getItemViewType(position: Int): Int =
        if (tournaments[position].date.isBefore(LocalDate.now())) TournamentType.OUTDATED.viewType
        else TournamentType.ACTIVE.viewType

    override fun onClick(removeButtonView: View) {
        actionListener.onTournamentRemove(removeButtonView.tag as Tournament)
    }

    sealed class TournamentViewHolder(
        val binding: ViewBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        open fun bindTournamentItem(tournament: Tournament, onClickListener: OnClickListener) {}

        companion object {
            enum class TournamentItemType(val viewType: Int) {
                ACTIVE(1), OUTDATED(0)
            }
        }
    }

    class TournamentActiveViewHolder(binding: ItemTournamentActiveBinding) :
        TournamentViewHolder(binding) {

        override fun bindTournamentItem(tournament: Tournament, onClickListener: OnClickListener) {
            with(binding as ItemTournamentActiveBinding) {
                tournamentItemRemoveButton.setOnClickListener(onClickListener)
                tournamentItemName.text = tournament.name
                tournamentItemParticipantCount.text =
                    tournament.participantCount.toString(10)
                tournamentItemDate.text =
                    tournament.date.format(DateTimeFormatter.ISO_LOCAL_DATE)
                tournamentItemRemoveButton.tag = tournament
            }
        }
    }

    class TournamentOutdatedViewHolder(binding: ItemTournamentOutdatedBinding) :
        TournamentViewHolder(binding) {
        override fun bindTournamentItem(tournament: Tournament, onClickListener: OnClickListener) {
            with(binding as ItemTournamentOutdatedBinding) {
                tournamentItemRemoveButton.setOnClickListener(onClickListener)
                tournamentItemName.text = tournament.name
                tournamentItemParticipantCount.text =
                    tournament.participantCount.toString(10)
                tournamentItemDate.text =
                    tournament.date.format(DateTimeFormatter.ISO_LOCAL_DATE)
                tournamentItemRemoveButton.tag = tournament
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