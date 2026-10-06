package com.arthurraymundo.navandroid.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.arthurraymundo.navandroid.viewmodel.ReceitaViewModel

@Composable
fun FavoritasScreen(
    modifier: Modifier = Modifier,
    viewModel: ReceitaViewModel,
    aoAbrirDetalhe: (Int) -> Unit
) {
    val favoritas = viewModel.receitas.filter { it.favorita }

    Column(modifier = modifier.fillMaxSize()) {
        CabecalhoLista(
            titulo = "Favoritas",
            subtitulo = "As receitas que você mais ama"
        )
        if (favoritas.isEmpty()) {
            EstadoVazio(
                icone = Icons.Filled.FavoriteBorder,
                titulo = "Nenhuma favorita ainda",
                mensagem = "Toque no coração de uma receita para guardá-la aqui."
            )
        } else {
            ListaDeCards(
                receitas = favoritas,
                viewModel = viewModel,
                aoAbrirDetalhe = aoAbrirDetalhe
            )
        }
    }
}
