package com.example.livrosfirebase

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.livrosfirebase.ui.theme.LivrosFirebaseTheme
import com.example.livrosfirebase.view.CadastroLivros
import com.example.livrosfirebase.view.DetalhesLivro
import com.example.livrosfirebase.view.ListaLivros


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LivrosFirebaseTheme {
                val navController = rememberNavController()

                NavHost(navController, startDestination = "CadastroLivros"){
                    composable(route="CadastroLivros"){
                        CadastroLivros(navController)
                    }

                    composable(route="ListaLivros"){
                        ListaLivros(navController)
                    }



                    composable("DetalhesLivro/{t}/{a}/{d}") { backStackEntry ->
                        val t = backStackEntry.arguments?.getString("t") ?: "Sem título"
                        val a = backStackEntry.arguments?.getString("a") ?: "Sem autor"
                        val d = backStackEntry.arguments?.getString("d") ?: "Sem gênero"

                        DetalhesLivro(navController, t, a, d)

                    }

                }

                }
            }
        }
    }
