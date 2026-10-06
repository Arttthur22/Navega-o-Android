package com.arthurraymundo.navandroid.model

data class Receita(
    val id: Int,
    val nome: String,
    val emoji: String,
    val categoria: String,
    val tempoMinutos: Int,
    val porcoes: Int,
    val ingredientes: List<String>,
    val passos: List<String>,
    val favorita: Boolean = false
)
