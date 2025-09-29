package fr.eseo.ld.mm.whatcolor.model

import androidx.annotation.ColorRes
import androidx.annotation.StringRes

data class ColourData(
    @StringRes val nameId : Int,
    @ColorRes val colourId : Int

)
