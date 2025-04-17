package com.example.helloandroidagain.ui.basic

import androidx.lifecycle.ViewModel
import com.example.helloandroidagain.ui.basic.component.GreetingItemState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class BasicViewModel : ViewModel() {
    private val _screenUiState = MutableStateFlow(initializeGreetings())
    val greetingScreenState: StateFlow<GreetingScreenState> = _screenUiState.asStateFlow()

    private fun initializeGreetings() = GreetingScreenState(
        greetings = List(100) { i ->
            GreetingItemState(
                name = "Name${i + 1}",
                checks = listOf(false, false, false),
                updateCheckState = { name, position, isChecked ->
                    updateCheckStateByName(name, position, isChecked)
                },
                updateOtherText = { name, newText ->
                    updateOtherTextByName(name, newText)
                }
            )
        }
    )

    private fun updateCheckStateByName(name: String, checkPosition: Int, isChecked: Boolean) {
        updateGreetingByName(name) { greetingItem ->
            val updatedChecks = greetingItem.checks.toMutableList().apply {
                this[checkPosition] = isChecked
            }
            greetingItem.copy(checks = updatedChecks)
        }
    }

    private fun updateOtherTextByName(name: String, newOtherText: String) {
        updateGreetingByName(name) { greetingItem ->
            greetingItem.copy(otherText = newOtherText)
        }
    }

    private fun updateGreetingByName(
        name: String,
        updateItem: (GreetingItemState) -> GreetingItemState
    ) {
        _screenUiState.update { currentState ->
            val updatedGreetings = currentState.greetings.toMutableList()
            val namePosition = updatedGreetings.indexOfFirst { it.name == name }
            if (namePosition != -1) {
                val updatedItem = updateItem(updatedGreetings[namePosition])
                updatedGreetings[namePosition] = updatedItem
            }
            currentState.copy(greetings = updatedGreetings)
        }
    }

    data class GreetingScreenState(
        val greetings: List<GreetingItemState> = emptyList()
    )
}
