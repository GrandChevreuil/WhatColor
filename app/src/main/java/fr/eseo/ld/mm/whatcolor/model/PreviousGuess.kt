package fr.eseo.ld.mm.whatcolor.model

data class PreviousGuess(
    val correctColourData: ColourData,
    val guessedColourData: ColourData
){
    val result : Boolean
    init {
        result = correctColourData == guessedColourData
    }
}
