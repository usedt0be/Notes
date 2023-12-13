package com.example.notes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.notes.screens.HomeScreen
import com.example.notes.screens.NoteScreen
import com.example.notes.ui.theme.NotesTheme
import com.example.notes.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

enum class Screens {
    Home,Note
}

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val homeViewModel: HomeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            NotesTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    NavHost(navController = navController, startDestination = Screens.Home.name, builder = {
                        composable(Screens.Home.name) {
                            HomeScreen(
                                homeViewModel = homeViewModel,
                                onClickNote = {
                                    navController.navigate(Screens.Note.name)
                                },
                                onClickAddNote = {
                                    navController.navigate(Screens.Note.name)
                                }
                            )
                        }
                        composable(Screens.Note.name) {
                            NoteScreen(homeViewModel = homeViewModel,
                            onClickClose = {navController.popBackStack()}
                            )
                        }
                    })
                }
            }
        }
    }
}


