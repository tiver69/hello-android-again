package com.example.helloandroidagain.tournament_presentation.component.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.browser.customtabs.CustomTabColorSchemeParams
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.net.toUri
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.helloandroidagain.core.util.convertToString
import com.example.helloandroidagain.tournament_domain.model.Tournament
import com.example.helloandroidagain.tournament_presentation.R
import com.example.helloandroidagain.tournament_presentation.component.recyclerview.TournamentListAdapter.TournamentViewHolder
import com.example.helloandroidagain.tournament_presentation.databinding.ItemTournamentActiveBinding
import com.example.helloandroidagain.tournament_presentation.databinding.ItemTournamentOutdatedBinding
import java.time.LocalDate
import javax.inject.Inject

typealias TournamentType = TournamentViewHolder.Companion.TournamentItemType

class TournamentListAdapter @Inject constructor() : RecyclerView.Adapter<TournamentViewHolder>() {

    lateinit var onClickListener: TournamentClickListener

    var tournaments: List<Tournament> = emptyList()
        set(newValue) {
            if (field.isEmpty()) {
                notifyDataSetChanged() // the only way I managed ItemViewType to work correctly
            }
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
        holder.itemView.setOnClickListener {
            onClickListener.onItemClick(tournament)
        }
        holder.bindTournamentItem(tournament)
    }

    override fun getItemCount(): Int = tournaments.size

    override fun getItemViewType(position: Int): Int =
        if (tournaments[position].date.isBefore(LocalDate.now())) {
            TournamentType.OUTDATED.viewType
        } else {
            TournamentType.ACTIVE.viewType
        }

    sealed class TournamentViewHolder(val binding: ViewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        open fun bindTournamentItem(tournament: Tournament) {}

        fun openItemLogoInBrowser(url: String) {
            val customTabsIntent = CustomTabsIntent.Builder()
                .setShowTitle(true)
                .setDefaultColorSchemeParams(
                    CustomTabColorSchemeParams.Builder()
                        .setToolbarColor(
                            itemView.context.getColor(
                                com.example.helloandroidagain.core.R.color.md_theme_primary
                            )
                        )
                        .setNavigationBarColor(
                            itemView.context.getColor(
                                com.example.helloandroidagain.core.R.color.md_theme_primary
                            )
                        )
                        .build()
                ).build()
            customTabsIntent.launchUrl(itemView.context, url.toUri())
        }

        fun loadItemLogo(url: String, into: ImageView) {
            Glide.with(itemView.context)
                .asBitmap()
                .load(url)
                .error(R.drawable.ic_image_placeholder)
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .circleCrop()
                .into(into)
        }

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
                loadItemLogo(tournament.logo.thumbUrl, tournamentItemLogo)
                tournamentItemName.text = tournament.name
                tournamentItemParticipantCount.text =
                    tournament.participantCount.toString(10)
                tournamentItemDate.text =
                    tournament.date.convertToString()
                tournamentItemLogo.setOnClickListener {
                    openItemLogoInBrowser(tournament.logo.rawUrl)
                }
            }
        }
    }

    class TournamentOutdatedViewHolder(binding: ItemTournamentOutdatedBinding) :
        TournamentViewHolder(binding) {
        override fun bindTournamentItem(tournament: Tournament) {
            with(binding as ItemTournamentOutdatedBinding) {
                loadItemLogo(tournament.logo.thumbUrl, tournamentItemLogo)
                tournamentItemName.text = tournament.name
                tournamentItemParticipantCount.text =
                    tournament.participantCount.toString(10)
                tournamentItemDate.text =
                    tournament.date.convertToString()
                tournamentItemLogo.setOnClickListener {
                    openItemLogoInBrowser(tournament.logo.rawUrl)
                }
            }
        }
    }
}

interface TournamentClickListener {
    fun onItemClick(tournament: Tournament)
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