package fr.eseo.ld.mm.whatcolor.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import fr.eseo.ld.mm.whatcolor.R
import fr.eseo.ld.mm.whatcolor.model.ColourData
import fr.eseo.ld.mm.whatcolor.model.PreviousGuess
import fr.eseo.ld.mm.whatcolor.model.Colours
import kotlin.math.max

@Composable
fun PreviousGuessCard(
    previousGuess: PreviousGuess,
    modifier: Modifier = Modifier
) {
    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 5.dp),
        modifier = modifier
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Image(
                painter = painterResource(id = if (previousGuess.result) R.drawable.yes else R.drawable.no),
                contentDescription = null,
                contentScale = ContentScale.FillHeight,
                modifier = Modifier
                    .height(IntrinsicSize.Min)
                    .padding(16.dp)
            )
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 16.dp)
            ) {
                Text(
                    text = stringResource(
                        id = R.string.actual_message,
                        stringResource(previousGuess.correctColourData.nameId)
                    )
                )
                Text(
                    text = stringResource(
                        id = R.string.guessed_message,
                        stringResource(previousGuess.guessedColourData.nameId)
                    )
                )
            }
        }
    }
}

@Composable
fun PreviousGuessList(
    previousGuesses: List<PreviousGuess>,
    modifier: Modifier = Modifier
) {
    val listState = rememberLazyListState()
    LaunchedEffect(previousGuesses.size) {
        listState.animateScrollToItem(index = max(previousGuesses.size - 1, 0))
    }
    LazyColumn(
        state = listState,
        modifier = modifier.fillMaxSize()
    ) {
        items(previousGuesses) { previousGuess ->
            PreviousGuessCard(
                previousGuess = previousGuess,
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}

@Composable
fun BoxWithText(
    textId: Int,
    colourId: Int,
    modifier: Modifier = Modifier
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .background(colorResource(id = R.color.colour_grey))
            .padding(vertical = 16.dp)
            .fillMaxWidth(0.75f)
    ) {
        Text(
            text = stringResource(id = textId),
            color = colorResource(id = colourId),
            fontSize = 58.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun GameCard(
    currentCorrectColour: ColourData,
    currentIncorrectColour: ColourData,
    option1: ColourData,
    option2: ColourData,
    onClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier
    ) {
        BoxWithText(
            textId = currentIncorrectColour.nameId,
            colourId = currentCorrectColour.colourId
        )
        Button(
            onClick = { onClick(option1.nameId) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = stringResource(id = option1.nameId),
                fontSize = 36.sp
            )
        }
        Button(
            onClick = { onClick(option2.nameId) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = stringResource(id = option2.nameId),
                fontSize = 36.sp
            )
        }
    }
}

@Composable
fun GameScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .statusBarsPadding()
            .safeDrawingPadding()
            .padding(8.dp)
    ) {
        Text(
            text = stringResource(id = R.string.time_left_label, 60, 0)
        )
        GameCard(
            currentCorrectColour = Colours.colours[0],
            currentIncorrectColour = Colours.colours[1],
            option1 = Colours.colours[0],
            option2 = Colours.colours[1],
            onClick = {},
            modifier = modifier.padding(8.dp)
        )
        Box(
            modifier = modifier
                .fillMaxSize()
                .weight(1f)
        ) {
            PreviousGuessList(
                previousGuesses = listOf(
                    PreviousGuess(Colours.colours[0], Colours.colours[1]),
                    PreviousGuess(Colours.colours[3], Colours.colours[3])
                )
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GameScreenPreview() {
    GameScreen()
}
