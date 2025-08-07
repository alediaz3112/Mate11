package com.example.mate11

import android.os.Bundle
import android.view.View
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.google.androidgamesdk.GameActivity

// Cambia la herencia de GameActivity a ComponentActivity para evitar conflictos con Compose
class MainActivity : ComponentActivity() {
    companion object {
        init {
            System.loadLibrary("mate11")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Mate11Theme {
                Mate11App()
            }
        }
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus) {
            hideSystemUi()
        }
    }

    private fun hideSystemUi() {
        val decorView = window.decorView
        decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_FULLSCREEN)
    }
}

@Composable
fun Mate11App() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "welcome") {
        composable("welcome") {
            WelcomeScreen(
                onTableSelected = { table ->
                    navController.navigate("table/$table")
                },
                onStoryMode = { navController.navigate("story") },
                onChapterSelect = { navController.navigate("chapter") }
            )
        }
        composable(
            route = "table/{table}",
            arguments = listOf(navArgument("table") { type = NavType.IntType })
        ) { backStackEntry ->
            val table = backStackEntry.arguments?.getInt("table") ?: 2
            TableScreen(table = table, onBack = { navController.popBackStack() })
        }
        composable("story") {
            StoryModeScreen(onBack = { navController.popBackStack() })
        }
        composable("chapter") {
            ChapterSelectScreen(onBack = { navController.popBackStack() })
        }
    }
}

// ... Mate11Theme y otras pantallas se definen en otros archivos ...
