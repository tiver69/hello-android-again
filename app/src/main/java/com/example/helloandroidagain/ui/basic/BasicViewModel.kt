package com.example.helloandroidagain.ui.basic

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class BasicViewModel : ViewModel() {
    private val _greetingCheckedStates = MutableStateFlow(mutableMapOf<String, List<Boolean>>())
    val greetingCheckedStates: StateFlow<Map<String, List<Boolean>>> =
        _greetingCheckedStates.asStateFlow()

    fun initializeGreetings() {
        _greetingCheckedStates.value = mutableMapOf<String, List<Boolean>>().apply {
            for (name in List(100) { "Name$it" }) {
                put(name, listOf(false, false, false))
            }
        }
    }

    fun updateCheckState(name: String, position: Int, isChecked: Boolean) {
        _greetingCheckedStates.value[name]?.let { list ->
            val updatedList = list.toMutableList().apply { this[position] = isChecked }
            _greetingCheckedStates.value = _greetingCheckedStates.value.toMutableMap().apply {
                put(name, updatedList)
            }
        }
    }
}
