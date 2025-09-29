package fr.eseo.ld.mm.whatcolor.ui.state

import fr.eseo.ld.mm.whatcolor.model.Colours
import fr.eseo.ld.mm.whatcolor.model.ColourData
import fr.eseo.ld.mm.whatcolor.model.PreviousGuess

data class GameUiState(
    val currentCorrectColour : ColourData = Colours.colours[0],
    val currentIncorrectColour : ColourData = Colours.colours[1],
    val currentScore : Int = 0,
    val guessedColourIndex : Int = -1,
    val timeLeft : Long = 60000,
    val lastScore : Int = 0,
    val localHighScore : Int = 0,
    val previousGuesses : List<PreviousGuess> = emptyList()

)
