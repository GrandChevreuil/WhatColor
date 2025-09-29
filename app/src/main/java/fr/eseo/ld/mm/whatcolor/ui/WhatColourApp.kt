package fr.eseo.ld.mm.whatcolor.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.composable
import fr.eseo.ld.mm.whatcolor.R
import fr.eseo.ld.mm.whatcolor.model.WhatColourDataStore
import fr.eseo.ld.mm.whatcolor.ui.navigation.WhatColourScreens
import fr.eseo.ld.mm.whatcolor.ui.screens.GameScreen
import fr.eseo.ld.mm.whatcolor.ui.screens.WelcomeScreen
import fr.eseo.ld.mm.whatcolor.ui.viewmodels.GameViewModel
import fr.eseo.ld.mm.whatcolor.ui.viewmodels.GameViewModelFactory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun WhatColourAppBar(
    currentScreen: WhatColourScreens,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = { Text(stringResource(id = R.string.app_name)) },
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        ),
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = stringResource(id = R.string.back_button)
                    )
                }
            }
        },
        modifier = modifier
    )
}

@Composable
fun WhatColourApp(
    navController: NavHostController = rememberNavController()
) {
    val context = LocalContext.current
    val dataStore = WhatColourDataStore(context)
    val factory = GameViewModelFactory(dataStore)
    val viewModel: GameViewModel = androidx.lifecycle.viewmodel.compose.viewModel(factory = factory)
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = WhatColourScreens.valueOf(
        backStackEntry?.destination?.route ?: WhatColourScreens.WELCOME.name
    )
    Surface {
        Scaffold(
            topBar = {
                WhatColourAppBar(
                    currentScreen = currentScreen,
                    canNavigateBack = navController.previousBackStackEntry != null,
                    navigateUp = { navController.navigateUp() }
                )
            }
        ) { innerPadding ->
            NavHost(
                navController = navController,
                startDestination = WhatColourScreens.WELCOME.name,
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(innerPadding)
            ) {
                composable(route = WhatColourScreens.WELCOME.name) {
                    WelcomeScreen(
                        viewModel = viewModel,
                        playGameClick = {
                            viewModel.startGame()
                            navController.navigate(WhatColourScreens.GAME.name)
                        }
                    )
                }
                composable(route = WhatColourScreens.GAME.name) {
                    GameScreen(
                        viewModel = viewModel,
                        navController = navController,
                        modifier = Modifier.padding(8.dp)
                    )
                }
            }
        }
    }
}
