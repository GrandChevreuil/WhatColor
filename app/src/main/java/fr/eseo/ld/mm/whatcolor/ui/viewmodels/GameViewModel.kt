package fr.eseo.ld.mm.whatcolor.ui.viewmodels

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue
import fr.eseo.ld.mm.whatcolor.model.ColourData
import fr.eseo.ld.mm.whatcolor.model.Colours
import fr.eseo.ld.mm.whatcolor.ui.state.GameUiState
import kotlinx.coroutines.Job
import kotlin.random.Random

class GameViewModel : ViewModel() {
    // Private MutableStateFlow for UI state
    private val _uiState = MutableStateFlow(GameUiState())
    // Public StateFlow for UI state
    val uiState: StateFlow<GameUiState> = _uiState.asStateFlow()

    // User guess state
    var userGuess by mutableIntStateOf(-1)
        private set

    // Timer job
    private var timerJob: Job? = null

    // Select a random colour, optionally excluding one
    private fun selectRandomColour(exemptColour: ColourData? = null): ColourData {
        val filteredList = if (exemptColour != null) {
            Colours.colours.filter { it != exemptColour }
        } else {
            Colours.colours
        }
        val randomIndex = Random.nextInt(filteredList.size)
        return filteredList[randomIndex]
    }
}