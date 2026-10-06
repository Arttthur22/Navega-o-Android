package com.arthurraymundo.navandroid.model

import kotlinx.serialization.Serializable

/** Todas as rotas do app. Nenhuma é uma String solta. */
sealed interface Rota {
    @Serializable
    data object Splash : Rota

    @Serializable
    data object Login : Rota

    // As três abas do Painel Principal
    @Serializable
    data object Receitas : Rota

    @Serializable
    data object Favoritas : Rota

    @Serializable
    data object Perfil : Rota

    // Detalhe com parâmetro type-safe
    @Serializable
    data class DetalheReceita(val receitaId: Int) : Rota
}
