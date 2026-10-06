package com.arthurraymundo.navandroid.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.arthurraymundo.navandroid.model.Receita
import com.arthurraymundo.navandroid.model.receitasIniciais

/** Instância única: criada em AppNavigation e repassada como parâmetro a cada tela. */
class ReceitaViewModel : ViewModel() {

    var estaLogado by mutableStateOf(false)
        private set

    val receitas = mutableStateListOf<Receita>().apply { addAll(receitasIniciais) }

    fun fazerLogin() {
        estaLogado = true
    }

    fun fazerLogout() {
        estaLogado = false
    }

    fun alternarFavorita(receitaId: Int) {
        val index = receitas.indexOfFirst { it.id == receitaId }
        if (index != -1) {
            receitas[index] = receitas[index].copy(favorita = !receitas[index].favorita)
        }
    }

    fun remover(receitaId: Int) {
        receitas.removeAll { it.id == receitaId }
    }

    fun restaurarReceitas() {
        receitas.clear()
        receitas.addAll(receitasIniciais)
    }

    fun buscarReceita(receitaId: Int): Receita? =
        receitas.find { it.id == receitaId }
}
