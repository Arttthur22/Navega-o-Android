# 🍲 Receitário — Catálogo de Receitas

App Android (Kotlin + Jetpack Compose) feito para a atividade **"Missão: Seu Próprio App, com Navegação de Verdade"**.
Tema escolhido: **Catálogo de Receitas**.

## Telas implementadas

| Tela | Rota | Descrição |
|------|------|-----------|
| Splash | `Rota.Splash` | Tela de abertura; termina sozinha (navegação **linear**) e avisa quem a chamou. |
| Login | `Rota.Login` | Login simulado (qualquer clique em "Entrar" funciona). |
| Receitas (aba 1) | `Rota.Receitas` | **Lista** de receitas, com favoritar, excluir e estado vazio. |
| Favoritas (aba 2) | `Rota.Favoritas` | Receitas favoritadas, com estado vazio próprio. |
| Perfil (aba 3) | `Rota.Perfil` | Resumo do caderno e botão "Sair da conta". |
| Detalhe | `Rota.DetalheReceita(receitaId: Int)` | Ingredientes e modo de preparo da receita escolhida. |

Fluxo: `Splash → (condicional) → Login ou Painel Principal (3 abas) → Lista → Detalhe(id)`.

## Onde está cada requisito

- **Rotas tipadas:** `model/Rota.kt` — `sealed interface Rota` com `@Serializable`; nenhuma String solta. O detalhe é lido com `backStackEntry.toRoute()` em `AppNavigation.kt`.
- **Navegação linear + condicional:** `SplashScreen(aoTerminar)` não conhece `estaLogado`; quem decide o destino é o `AppNavigation` (`if (viewModel.estaLogado) Receitas else Login`).
- **Parâmetro type-safe:** a lista chama `navController.navigate(Rota.DetalheReceita(id))`.
- **Bottom Navigation persistente:** `PainelPrincipal.kt` → `navegarParaAba()` usa `saveState`, `restoreState` e `launchSingleTop` juntos.
- **ViewModel único:** `viewModel()` é chamado **uma só vez**, em `AppNavigation`, e passado por parâmetro a todas as telas.
- **Paleta própria:** `ui/theme/Color.kt` + `Theme.kt` (`lightColorScheme`, sem cor dinâmica). Nenhuma tela usa `Color(0xFF...)`.
- **Estado vazio:** a lista mostra mensagem + botão "Restaurar receitas" quando tudo é excluído; Favoritas também trata o caso vazio.
- **Acessibilidade:** todo `Icon` clicável tem `contentDescription` (os decorativos ao lado de texto usam `null`).

## Como rodar no Android Studio

1. Abra o Android Studio (Ladybug ou mais recente) → **Open** → selecione a pasta do projeto.
2. Aguarde o **Gradle Sync** terminar (JDK 17+ embutido no Android Studio).
3. Escolha um emulador (API 24+) ou um celular com depuração USB.
4. Clique em **Run ▶**.

## Como testar (checklist)

1. Splash → Login → "Entrar".
2. Role a lista de receitas, vá para "Favoritas", volte: a rolagem continua onde estava.
3. Toque em receitas diferentes: cada uma abre o seu próprio detalhe.
4. Exclua todas as receitas (ícone de lixeira): aparece a mensagem de lista vazia.
5. Perfil → "Sair da conta": volta ao Login e o botão Voltar não retorna às abas.
