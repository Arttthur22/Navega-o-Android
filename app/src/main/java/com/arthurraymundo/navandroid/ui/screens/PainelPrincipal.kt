package com.arthurraymundo.navandroid.ui.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavHostController
import com.arthurraymundo.navandroid.model.Rota
import com.arthurraymundo.navandroid.viewmodel.ReceitaViewModel

enum class AbaPrincipal { RECEITAS, FAVORITAS, PERFIL }

private data class ItemAba(
    val aba: AbaPrincipal,
    val rota: Rota,
    val rotulo: String,
    val icone: ImageVector
)

private val itensAba = listOf(
    ItemAba(AbaPrincipal.RECEITAS, Rota.Receitas, "Receitas", Icons.Filled.Restaurant),
    ItemAba(AbaPrincipal.FAVORITAS, Rota.Favoritas, "Favoritas", Icons.Filled.Favorite),
    ItemAba(AbaPrincipal.PERFIL, Rota.Perfil, "Perfil", Icons.Filled.Person),
)

@Composable
fun PainelPrincipal(
    navController: NavHostController,
    viewModel: ReceitaViewModel,
    abaAtual: AbaPrincipal
) {
    Scaffold(
        bottomBar = {
            NavigationBar {
                itensAba.forEach { item ->
                    NavigationBarItem(
                        selected = abaAtual == item.aba,
                        onClick = { navegarParaAba(navController, item.rota) },
                        icon = { Icon(item.icone, contentDescription = "Aba ${item.rotulo}") },
                        label = { Text(item.rotulo) }
                    )
                }
            }
        }
    ) { paddingValues ->
        val modifier = Modifier.padding(paddingValues)
        val abrirDetalhe: (Int) -> Unit = { id -> navController.navigate(Rota.DetalheReceita(id)) }

        when (abaAtual) {
            AbaPrincipal.RECEITAS -> ListaReceitasScreen(modifier, viewModel, abrirDetalhe)
            AbaPrincipal.FAVORITAS -> FavoritasScreen(modifier, viewModel, abrirDetalhe)
            AbaPrincipal.PERFIL -> PerfilScreen(
                modifier = modifier,
                viewModel = viewModel,
                aoSair = {
                    viewModel.fazerLogout()
                    navController.navigate(Rota.Login) {
                        popUpTo(0) { inclusive = true }
                    }
                }
            )
        }
    }
}

/**
 * Receita da navegação persistente: as TRÊS opções juntas.
 *
 * O ponto de ancoragem é a primeira aba (Rota.Receitas), que sempre está na
 * pilha depois da Splash/Login. (findStartDestination() apontaria para a Splash,
 * que já foi removida da pilha, e a troca de aba deixaria de salvar/restaurar.)
 */
private fun navegarParaAba(navController: NavHostController, destino: Rota) {
    navController.navigate(destino) {
        popUpTo<Rota.Receitas> { saveState = true }
        launchSingleTop = true
        restoreState = true
    }
}
