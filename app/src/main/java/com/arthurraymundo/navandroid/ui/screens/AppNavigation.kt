package com.arthurraymundo.navandroid.ui.screens

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.arthurraymundo.navandroid.model.Rota
import com.arthurraymundo.navandroid.viewmodel.ReceitaViewModel

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    // ÚNICA chamada de viewModel() do app. Todas as telas recebem esta instância por parâmetro.
    val viewModel: ReceitaViewModel = viewModel()

    NavHost(navController = navController, startDestination = Rota.Splash) {

        composable<Rota.Splash> {
            SplashScreen(
                aoTerminar = {
                    // Navegação CONDICIONAL: a decisão é de quem chama a Splash.
                    val destino: Rota = if (viewModel.estaLogado) Rota.Receitas else Rota.Login
                    navController.navigate(destino) {
                        popUpTo(Rota.Splash) { inclusive = true }
                    }
                }
            )
        }

        composable<Rota.Login> {
            LoginScreen(
                aoLogar = {
                    viewModel.fazerLogin()
                    navController.navigate(Rota.Receitas) {
                        popUpTo(Rota.Login) { inclusive = true }
                    }
                }
            )
        }

        composable<Rota.Receitas> {
            PainelPrincipal(navController, viewModel, AbaPrincipal.RECEITAS)
        }

        composable<Rota.Favoritas> {
            PainelPrincipal(navController, viewModel, AbaPrincipal.FAVORITAS)
        }

        composable<Rota.Perfil> {
            PainelPrincipal(navController, viewModel, AbaPrincipal.PERFIL)
        }

        composable<Rota.DetalheReceita> { backStackEntry ->
            val rota: Rota.DetalheReceita = backStackEntry.toRoute()
            DetalheReceitaScreen(
                receita = viewModel.buscarReceita(rota.receitaId),
                aoVoltar = { navController.popBackStack() },
                aoAlternarFavorita = { viewModel.alternarFavorita(rota.receitaId) }
            )
        }
    }
}
