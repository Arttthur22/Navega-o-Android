package com.arthurraymundo.navandroid.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.RestartAlt
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.arthurraymundo.navandroid.model.Receita
import com.arthurraymundo.navandroid.viewmodel.ReceitaViewModel

@Composable
fun ListaReceitasScreen(
    modifier: Modifier = Modifier,
    viewModel: ReceitaViewModel,
    aoAbrirDetalhe: (Int) -> Unit
) {
    Column(modifier = modifier.fillMaxSize()) {
        CabecalhoLista(
            titulo = "Receitas",
            subtitulo = "${viewModel.receitas.size} receitas no seu caderno"
        )

        if (viewModel.receitas.isEmpty()) {
            EstadoVazio(
                icone = Icons.Filled.RestartAlt,
                titulo = "Nenhuma receita por aqui",
                mensagem = "Você excluiu todas as receitas. Que tal trazer as originais de volta?",
                textoAcao = "Restaurar receitas",
                aoAcao = viewModel::restaurarReceitas
            )
        } else {
            ListaDeCards(
                receitas = viewModel.receitas,
                viewModel = viewModel,
                aoAbrirDetalhe = aoAbrirDetalhe
            )
        }
    }
}

@Composable
internal fun CabecalhoLista(titulo: String, subtitulo: String) {
    Column(modifier = Modifier.padding(start = 20.dp, end = 20.dp, top = 20.dp, bottom = 12.dp)) {
        Text(
            titulo,
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )
        Text(
            subtitulo,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
internal fun ListaDeCards(
    receitas: List<Receita>,
    viewModel: ReceitaViewModel,
    aoAbrirDetalhe: (Int) -> Unit
) {
    // O LazyListState é salvo via rememberSaveable por padrão: junto com
    // saveState/restoreState da navegação, a rolagem sobrevive à troca de aba.
    LazyColumn(
        contentPadding = PaddingValues(horizontal = 20.dp, vertical = 4.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier.fillMaxSize()
    ) {
        items(receitas, key = { it.id }) { receita ->
            CardReceita(
                receita = receita,
                aoClicar = { aoAbrirDetalhe(receita.id) },
                aoAlternarFavorita = { viewModel.alternarFavorita(receita.id) },
                aoExcluir = { viewModel.remover(receita.id) }
            )
        }
    }
}

@Composable
private fun CardReceita(
    receita: Receita,
    aoClicar: () -> Unit,
    aoAlternarFavorita: () -> Unit,
    aoExcluir: () -> Unit
) {
    Card(
        onClick = aoClicar,
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, top = 12.dp, bottom = 12.dp, end = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primaryContainer),
                contentAlignment = Alignment.Center
            ) {
                Text(receita.emoji, fontSize = 22.sp)
            }
            Spacer(Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(receita.nome, fontWeight = FontWeight.SemiBold)
                Text(
                    "${receita.categoria} · ${receita.tempoMinutos} min",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            IconButton(onClick = aoAlternarFavorita) {
                Icon(
                    imageVector = if (receita.favorita) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                    contentDescription = if (receita.favorita) {
                        "Remover ${receita.nome} das favoritas"
                    } else {
                        "Adicionar ${receita.nome} às favoritas"
                    },
                    tint = if (receita.favorita) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline
                )
            }
            IconButton(onClick = aoExcluir) {
                Icon(
                    imageVector = Icons.Filled.Delete,
                    contentDescription = "Excluir ${receita.nome}",
                    tint = MaterialTheme.colorScheme.outline
                )
            }
        }
    }
}

/** Estado vazio reutilizável: ícone, texto explicativo e (opcional) uma ação clara. */
@Composable
internal fun EstadoVazio(
    icone: ImageVector,
    titulo: String,
    mensagem: String,
    textoAcao: String? = null,
    aoAcao: (() -> Unit)? = null
) {
    Column(
        modifier = Modifier.fillMaxSize().padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Decorativo: o texto logo abaixo já descreve o estado.
        Icon(
            imageVector = icone,
            contentDescription = null,
            modifier = Modifier.size(48.dp),
            tint = MaterialTheme.colorScheme.secondary
        )
        Spacer(Modifier.height(16.dp))
        Text(titulo, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold)
        Spacer(Modifier.height(8.dp))
        Text(
            mensagem,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center
        )
        if (textoAcao != null && aoAcao != null) {
            Spacer(Modifier.height(24.dp))
            Button(onClick = aoAcao) { Text(textoAcao) }
        }
    }
}
