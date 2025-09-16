package com.example.livrosfirebase.view


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.simulateHotReload
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.livrosfirebase.datasource.DataSource
import com.example.livrosfirebase.ui.theme.Purple400
import com.example.livrosfirebase.ui.theme.WHITE
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CadastroLivros(navController: NavController){


    var titulo by remember { mutableStateOf("") }
    var genero by remember { mutableStateOf("") }
    var mensagem by remember { mutableStateOf("") }
    var autor by remember { mutableStateOf("") }

    var scope = rememberCoroutineScope()
    //instanciando o banco de dados
    val dataSource = DataSource()


    var DrawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
//Menu de navigacao
    ModalNavigationDrawer(
        drawerState = DrawerState,
        drawerContent = {
            ModalDrawerSheet {
                Text(text = "Menu do app livros", modifier = Modifier.padding(16.dp))
                HorizontalDivider()
                NavigationDrawerItem(
                    label = {Text(text = "Lista dos Livros")},
                    selected = false,
                    onClick = {navController.navigate("ListaLivros")}
                )
                NavigationDrawerItem(
                    label = {Text(text = "Cadastro de Livros")},
                    selected = false,
                    onClick = {navController.navigate("CadastroLivros")}
                )
            }
        }
    ) {


        Scaffold(modifier = Modifier.fillMaxSize(),
            topBar = {
                TopAppBar(
                    title = { Text("Cadastrar Livros") },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Purple400,
                        titleContentColor = WHITE
                    ),
                    navigationIcon = {
                        IconButton(
                            onClick = {
                                scope.launch {
                                    DrawerState.apply {
                                        if (isClosed) open() else close()
                                    }
                                }
                            }
                        ) {
                            Icon(
                                Icons.Default.Menu, contentDescription = "Icone do menu",
                                tint = Color.White,
                                modifier = Modifier.size(30.dp)
                            )
                        }
                    }
                )
            },
            bottomBar = {
                BottomAppBar {
                    //Text("rodapé")
                }
            },
            floatingActionButton = {
                FloatingActionButton(onClick = { navController.navigate(route = "ListaLivros") }) {
                    Icon(Icons.Default.Menu, contentDescription = "Adicionar")
                }
            }
        ) { innerPadding ->
            Column(
                modifier = Modifier.fillMaxSize().padding(innerPadding),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                OutlinedTextField(
                    value = titulo,
                    onValueChange = { novotitulo ->
                        titulo = novotitulo
                    },
                    label = { Text(text = "Titulo do livro") }
                )
                OutlinedTextField(
                    value = autor,
                    onValueChange = { novoautor ->
                        autor = novoautor
                    },
                    label = { Text(text = "Autor do livro") }
                )
                OutlinedTextField(
                    value = genero,
                    onValueChange = { novogenero ->
                        genero = novogenero
                    },
                    label = { Text(text = "Descrição do livro") }
                )

                Button(
                    onClick = {
                        if (titulo.isNotBlank() && genero.isNotBlank() && autor.isNotBlank()) {
                            dataSource.salvarLivros(
                                titulo,
                                autor,
                                genero,
                                onSuccess = { mensagem = "✔ Livro Cadastrado " },
                                onFailure = { erro -> mensagem = "❌ Erro no cadastro " })

                            titulo = ""
                            genero = ""
                            autor = ""
                        }
                    }
                ) {
                    Text(text = "Cadastrar Livros")
                }

                Spacer(modifier = Modifier.size(100.dp))
                Text(text = mensagem)
            }
        }
    }
}