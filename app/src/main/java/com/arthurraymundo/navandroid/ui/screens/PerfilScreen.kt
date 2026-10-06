package com.arthurraymundo.navandroid.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.arthurraymundo.navandroid.viewmodel.ReceitaViewModel

@Composable
fun PerfilScreen(
    modifier: Modifier = Modifier,
    viewModel: ReceitaViewModel,
    aoSair: () -> Unit
) {
    val total = viewModel.receitas.size
    val favoritas = viewModel.receitas.count { it.favorita }

    Column(
        modifier = modifier.fillMaxSize().padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("👩‍🍳", fontSize = 56.sp)
        Spacer(Modifier.height(16.dp))
        Text(
            "Seu caderno de receitas",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(Modifier.height(8.dp))
        Text("$total receitas · $favoritas favoritas", style = MaterialTheme.typography.bodyLarge)
        Spacer(Modifier.height(32.dp))
        OutlinedButton(onClick = aoSair) { Text("Sair da conta") }
    }
}
