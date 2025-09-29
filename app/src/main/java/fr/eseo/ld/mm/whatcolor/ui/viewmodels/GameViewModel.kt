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
import fr.eseo.ld.mm.whatcolor.model.PreviousGuess
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

    // update game state when time changes
    private fun updateGameState(time: Long) {
        _uiState.update { currentState ->
            currentState.copy(
                timeLeft = time
            )
        }
    }

    // update game state white last score and high local score
    private fun updateGameState(lastScore: Int, highScore: Int) {
        _uiState.update { currentState ->
            currentState.copy(
                localHighScore = highScore,
                lastScore = lastScore
            )
        }
    }

    // updtate game state with a new previous guess
    private fun updateGameState(previousGuess: PreviousGuess) {
        _uiState.update { currentState ->
            currentState.copy(
                previousGuesses = _uiState.value.previousGuesses + previousGuess
            )
        }
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