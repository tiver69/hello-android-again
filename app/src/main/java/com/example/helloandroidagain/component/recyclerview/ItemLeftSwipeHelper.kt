package com.example.helloandroidagain.component.recyclerview

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView

interface TournamentSwipeListener {
    fun onRemoveSwipe(tournamentPosition: Int)
}

class ItemLeftSwipeHelper(private val swipeListener: TournamentSwipeListener) :
    ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean = false

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        swipeListener.onRemoveSwipe(viewHolder.bindingAdapterPosition)
    }
}