package com.example.helloandroidagain.ui.basic

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class GreetingScreenUiState(
    val checks: List<Boolean>,
    val otherText: String = ""
)

class BasicViewModel : ViewModel() {
    private val _uiState =
        MutableStateFlow(mutableMapOf<String, GreetingScreenUiState>())
    val uiState: StateFlow<Map<String, GreetingScreenUiState>> = _uiState.asStateFlow()

    fun initializeGreetings() {
        _uiState.value = mutableMapOf<String, GreetingScreenUiState>().apply {
            for (name in List(100) { "Name$it" }) {
                put(name, GreetingScreenUiState(listOf(false, false, false)))
            }
        }
    }

    fun updateCheckState(name: String, position: Int, isChecked: Boolean) {
        _uiState.value[name]?.let { namedUiState ->
            val updatedChecks =
                namedUiState.checks.toMutableList().apply { this[position] = isChecked }
            _uiState.value = _uiState.value.toMutableMap().apply {
                put(name, namedUiState.copy(checks = updatedChecks))
            }
        }
    }

    fun updateOtherText(name: String, newOtherText: String) {
        _uiState.value[name]?.let { namedUiState ->
            _uiState.value = _uiState.value.toMutableMap().apply {
                put(name, namedUiState.copy(otherText = newOtherText))
            }
        }
    }
}
