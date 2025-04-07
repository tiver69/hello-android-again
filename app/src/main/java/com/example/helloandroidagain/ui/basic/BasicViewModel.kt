package com.example.helloandroidagain.ui.basic

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class BasicViewModel : ViewModel() {
    private val _screenUiState = MutableStateFlow(GreetingScreenUiState())
    val greetingScreenUiState: StateFlow<GreetingScreenUiState> = _screenUiState.asStateFlow()

    fun initializeGreetings() {
        _screenUiState.value = GreetingScreenUiState(
            greetings = List(100) { i ->
                GreetingScreenUiState.GreetingItemUiState(
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
    }

    private fun updateCheckStateByName(name: String, checkPosition: Int, isChecked: Boolean) {
        val greetings = _screenUiState.value.greetings.toMutableList()
        val namePosition = greetings.indexOfFirst { it.name == name }
        greetings[namePosition].let { greetingItem ->
            val updatedChecks =
                greetingItem.checks.toMutableList().apply { this[checkPosition] = isChecked }
            greetings[namePosition] = greetingItem.copy(checks = updatedChecks)
        }

        _screenUiState.value = _screenUiState.value.copy(greetings = greetings)
    }

    private fun updateOtherTextByName(name: String, newOtherText: String) {
        val greetings = _screenUiState.value.greetings.toMutableList()
        val namePosition = greetings.indexOfFirst { it.name == name }
        greetings[namePosition].let { greetingItem ->
            greetings[namePosition] = greetingItem.copy(otherText = newOtherText)
        }

        _screenUiState.value = _screenUiState.value.copy(greetings = greetings)
    }

    data class GreetingScreenUiState(
        val greetings: List<GreetingItemUiState> = emptyList()
    ) {
        data class GreetingItemUiState(
            val name: String,
            val checks: List<Boolean>,
            val otherText: String = "",
            val updateCheckState: (String, Int, Boolean) -> Unit,
            val updateOtherText: (String, String) -> Unit
        )
    }
}
