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
import kotlinx.coroutines.flow.update
import kotlin.random.Random

class GameViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(GameUiState())

    val uiState: StateFlow<GameUiState> = _uiState.asStateFlow()


    var userGuess by mutableIntStateOf(-1)
        private set

    private var timerJob: Job? = null

    // slect a random colour, optionally excluding one
    private fun selectRandomColour(exemptColour: ColourData? = null): ColourData {
        val filteredList = if (exemptColour != null) {
            Colours.colours.filter { it != exemptColour }
        } else {
            Colours.colours
        }
        val randomIndex = Random.nextInt(filteredList.size)
        return filteredList[randomIndex]
    }

    private fun updateGameState(score: Int) {
        val correctColour = selectRandomColour()
        val incorrectColour = selectRandomColour(correctColour)
        _uiState.update { currentState ->
            currentState.copy(
                currentCorrectColour = correctColour,
                currentIncorrectColour = incorrectColour,
                currentScore = score
            )
        }
    }
}