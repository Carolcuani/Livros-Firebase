package com.example.livrosfirebase.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.livrosfirebase.datasource.DataSource
import com.example.livrosfirebase.ui.theme.Purple400
import com.example.livrosfirebase.ui.theme.WHITE
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListaLivros(navController: NavController){

    val dataSource = DataSource()
    var listaLivros by remember { mutableStateOf(listOf<Map<String, Any>>()) }
    var mensagem by remember { mutableStateOf("") }
    val scope = rememberCoroutineScope()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

    //Carregar lista ao abrir a tela
    LaunchedEffect(Unit) {
        dataSource.listarLivros(
            onResult = { livros -> listaLivros = livros },
            onFailure = { e -> mensagem = "Erro: ${e.message}" }
        )
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Text(text = "Menu do app livros", modifier = Modifier.padding(16.dp))
                Divider()
                NavigationDrawerItem(
                    label = { Text("Cadastro dos Livros") },
                    selected = false,
                    onClick = { navController.navigate("CadastroLivros") }
                )
                NavigationDrawerItem(
                    label = { Text("Lista de livros") },
                    selected = false,
                    onClick = { navController.navigate("ListaLivros") }
                )
            }
        }
    ) {

        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                TopAppBar(
                    title = { Text("Lista de Livros") },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Purple400,
                        titleContentColor = WHITE
                    ),
                    navigationIcon = {
                        IconButton(
                            onClick = {
                                scope.launch {
                                    if (drawerState.isClosed) drawerState.open() else drawerState.close()
                                }
                            }
                        ) {
                            Icon(Icons.Default.Menu, contentDescription = "Ícone do menu", tint = Color.White)
                        }
                    }
                )
            },
            bottomBar = { BottomAppBar { } },
            floatingActionButton = {
                FloatingActionButton(onClick = { navController.navigate("CadastroLivros") }) {
                    Icon(Icons.Default.Add, contentDescription = "Adicionar")
                }
            }
        ) { innerPadding ->

            Column(modifier = Modifier.fillMaxSize().padding(innerPadding)) {

                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    items(listaLivros) { livro ->
                        val t = livro["Titulo"] as? String ?: "Sem título"
                        val a = livro["Autor"] as? String ?: "Sem autor"
                        val d = livro["Genero"] as? String ?: "Sem gênero"

                        // Linha principal com título e ícones à direita
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 10.dp, vertical = 10.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "📌 $t",
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(end = 8.dp)
                            )

                            Spacer(modifier = Modifier.weight(1f)) // empurra os ícones para a direita

                            IconButton(
                                onClick = { navController.navigate("DetalhesLivro/$t/$a/$d") }
                            ) {
                                Icon(Icons.Default.Info, contentDescription = "Mostrar detalhes", modifier = Modifier.size(24.dp))
                            }

                            IconButton(
                                onClick = {
                                    dataSource.deletarLivros(t)
                                    navController.navigate("ListaLivros")
                                }
                            ) {
                                Icon(Icons.Default.Delete, contentDescription = "Apagar livro", modifier = Modifier.size(24.dp))
                            }
                        }

                        // Detalhes Autor e Gênero
                        Column(modifier = Modifier.padding(start = 35.dp, bottom = 10.dp)) {
                            Text("Autor: $a")
                            Text("Gênero: $d")
                        }

                        Divider()
                    }
                }

                Text(text = mensagem, modifier = Modifier.padding(8.dp))
            }
        }
    }
}
