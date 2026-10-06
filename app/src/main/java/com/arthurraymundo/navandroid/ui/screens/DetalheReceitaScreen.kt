package com.arthurraymundo.navandroid.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.arthurraymundo.navandroid.model.Receita

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetalheReceitaScreen(
    receita: Receita?,
    aoVoltar: () -> Unit,
    aoAlternarFavorita: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(receita?.nome ?: "Receita") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                ),
                navigationIcon = {
                    IconButton(onClick = aoVoltar) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Voltar para a lista")
                    }
                },
                actions = {
                    if (receita != null) {
                        IconButton(onClick = aoAlternarFavorita) {
                            Icon(
                                imageVector = if (receita.favorita) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                                contentDescription = if (receita.favorita) "Remover das favoritas" else "Adicionar às favoritas",
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                }
            )
        }
    ) { padding ->
        if (receita == null) {
            Box(Modifier.fillMaxSize().padding(padding), contentAlignment = Alignment.Center) {
                Text("Receita não encontrada.")
            }
            return@Scaffold
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp, vertical = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(receita.emoji, fontSize = 64.sp)
            Spacer(Modifier.height(8.dp))
            Text(receita.nome, style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)
            Text(
                "${receita.categoria} · ${receita.tempoMinutos} min · rende ${receita.porcoes}",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(Modifier.height(24.dp))

            SecaoDetalhe(titulo = "Ingredientes") {
                receita.ingredientes.forEach { Text("•  $it", style = MaterialTheme.typography.bodyLarge) }
            }
            Spacer(Modifier.height(16.dp))
            SecaoDetalhe(titulo = "Modo de preparo") {
                receita.passos.forEachIndexed { i, passo ->
                    Text("${i + 1}.  $passo", style = MaterialTheme.typography.bodyLarge)
                }
            }
            Spacer(Modifier.height(24.dp))
        }
    }
}

@Composable
private fun SecaoDetalhe(titulo: String, conteudo: @Composable () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text(
                titulo,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.secondary
            )
            conteudo()
        }
    }
}
