package com.arthurraymundo.navandroid.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val CoresReceitario = lightColorScheme(
    primary = Terracota,
    onPrimary = Branco,
    primaryContainer = TerracotaClara,
    onPrimaryContainer = TerracotaEscura,
    secondary = Oliva,
    onSecondary = Branco,
    secondaryContainer = OlivaClara,
    onSecondaryContainer = OlivaEscura,
    tertiary = Mel,
    onTertiary = Branco,
    tertiaryContainer = MelClaro,
    background = Creme,
    onBackground = Cacau,
    surface = CremeSuperficie,
    onSurface = Cacau,
    surfaceVariant = CremeVariante,
    onSurfaceVariant = CacauSuave,
    outline = ContornoCacau,
)

@Composable
fun ReceitarioTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = CoresReceitario,
        typography = Typography,
        content = content
    )
}
