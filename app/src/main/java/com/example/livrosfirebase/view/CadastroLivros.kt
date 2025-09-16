package com.example.livrosfirebase.view


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)


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
                                    if (drawerState.isClosed) drawerState.open() else drawerState.close()
                                }
                            }
                        ) {
                            Icon(Icons.Default.Menu, contentDescription = "Ícone do menu", tint = Color.White)
                        }
                    }
                )
            },
            bottomBar = {
                BottomAppBar(
                    containerColor =  Purple400,
                ) {


                FloatingActionButton(onClick = { navController.navigate(route = "ListaLivros") }) {
                    Icon(Icons.Default.Menu, contentDescription = "Adicionar")
                }
            }}
        ) { innerPadding ->
            Column(
                modifier = Modifier.fillMaxSize().padding(innerPadding),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {

                Text(
                    text = "Bem-vindo a nossa livraria!",
                    fontWeight = FontWeight.Bold,
                    fontSize = 28.sp,
                    color = Color(0xFFA52A2A) // marrom
                )

                Spacer(modifier = Modifier.size(40.dp))

                Text(
                    text = "Para realizar o cadastro dos livros:",
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = Color(0xFFA52A2A) // marrom
                )


                Spacer(modifier = Modifier.size(10.dp))


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

                Spacer(modifier = Modifier.size(20.dp))
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
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor =  Purple400, // marrom
                    )
                ) {
                    Text(
                        text = "Cadastrar Livros",
                        color = Color.White // texto branco para contraste
                    )
                }


                Spacer(modifier = Modifier.size(20.dp))
                Text(text = mensagem)
            }
        }
    }
}