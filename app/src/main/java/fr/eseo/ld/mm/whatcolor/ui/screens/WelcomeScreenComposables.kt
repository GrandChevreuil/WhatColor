package fr.eseo.ld.mm.whatcolor.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import fr.eseo.ld.mm.whatcolor.R

@Composable
fun WelcomeTitle() {
    Text(
        text = stringResource(id = R.string.game_description),
        fontWeight = FontWeight.Bold,
        fontSize = 32.sp,
        lineHeight = 32.sp,
        textAlign = TextAlign.Center,
        modifier = Modifier.padding(8.dp)
    )
}

@Composable
fun WelcomeButton(
    text: String,
    onClick: () -> Unit,
    enabled: Boolean = true
) {
    Button(
        onClick = onClick,
        enabled = enabled,
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
    ) {
        Text(
            text = text,
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(8.dp)
        )
    }
}

@Composable
fun WelcomeScores() {
    Text(
        text = stringResource(R.string.last_score, 0),
        fontSize = 24.sp,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(8.dp)
    )
    Text(
        text = stringResource(R.string.local_high_score, 0),
        fontSize = 24.sp,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(8.dp)
    )
}

@Composable
fun WelcomeScreen() {
    Column(
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        WelcomeTitle()
        WelcomeButton(
            text = stringResource(R.string.play_game_button),
            onClick = {}
        )
        WelcomeScores()
        WelcomeButton(
            text = stringResource(R.string.show_global_scores_button),
            onClick = {},
            enabled = false
        )
    }
}

@Preview(showBackground = true)
@Composable
fun WelcomeScreenPreview() {
    WelcomeScreen()
}
