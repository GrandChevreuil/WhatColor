package fr.eseo.ld.mm.whatcolor.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.delay

import kotlin.random.Random

import fr.eseo.ld.mm.whatcolor.model.ColourData
import fr.eseo.ld.mm.whatcolor.model.Colours
import fr.eseo.ld.mm.whatcolor.model.PreviousGuess
import fr.eseo.ld.mm.whatcolor.ui.state.GameUiState

open class GameViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(GameUiState())

    open val uiState: StateFlow<GameUiState> = _uiState.asStateFlow()


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

    // begin the game timer
    private fun startTimer() {
        timerJob?.cancel()
        timerJob = viewModelScope.launch {
            while (_uiState.value.timeLeft > 0) {
                val delayTime: Long = if (_uiState.value.timeLeft > 10000) {
                    1000
                } else {
                    100
                }
                delay(delayTime)
                val timeLeftAfterTick = _uiState.value.timeLeft.minus(delayTime)
                updateGameState(timeLeftAfterTick)
            }
        }
    }

    // stop timer
    fun stopGame() {
        timerJob?.cancel()
    }

    // new game and new timer
    fun startGame() {
        val correctColour = selectRandomColour()
        val incorrectColour = selectRandomColour(correctColour)
        _uiState.update { currentState ->
            currentState.copy(
                currentCorrectColour = correctColour,
                currentIncorrectColour = incorrectColour,
                currentScore = 0,
                timeLeft = 60000,
                guessedColourIndex = -1,
                previousGuesses = emptyList()
            )
        }
        startTimer()
    }

    // verify user guess, update score or time and add to previous guesses
    fun checkUserGuess(guessedColourId: Int) {
        userGuess = guessedColourId
        val previousGuess: PreviousGuess
        if (userGuess == _uiState.value.currentCorrectColour.nameId) {
            previousGuess = PreviousGuess(
                _uiState.value.currentCorrectColour,
                _uiState.value.currentCorrectColour
            )
            val updatedScore = _uiState.value.currentScore + 1
            updateGameState(score = updatedScore)
        } else {
            previousGuess = PreviousGuess(
                _uiState.value.currentCorrectColour,
                _uiState.value.currentIncorrectColour
            )
            val timeLeftAfterPenalty = _uiState.value.timeLeft - 2000
            updateGameState(time = timeLeftAfterPenalty)
        }
        updateGameState(previousGuess)
        userGuess = -1
    }

    // save final score and update high score if needed
    fun recordScore() {
        val lastScore = _uiState.value.currentScore
        val highScore = if (lastScore > _uiState.value.localHighScore) lastScore else _uiState.value.localHighScore
        updateGameState(lastScore, highScore)
    }
}