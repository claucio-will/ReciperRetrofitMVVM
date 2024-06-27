package com.example.myrecipeapp

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun RecipeApp(navController: NavHostController) {
    val recipeViewModel: MainViewModel = viewModel()
    val viewState by recipeViewModel.categoriesState

    NavHost(navController = navController, startDestination = Screen.RecipeScreen.route) {
        composable(route = Screen.RecipeScreen.route) {
            RecipeScreen(navigationToDetail = {
                //Todos os dados da categorias está dentro de it, e a chave para recuperar é categories
                // currentBackStackEntry serve para armazenar todos os dados que queremos
                //como o composable RecipeScreen recebe essa navegação passando o category, ele vai
                //mandar todos esses dados para a tela que queremos
                navController.currentBackStackEntry?.savedStateHandle?.set("categories", it)
                navController.navigate(Screen.DetailScreen.route)
            }, viewState = viewState)
        }
        //Recuperando dados da RecipeScreen
        composable(Screen.DetailScreen.route) {
            //previousBackStackEntry pega os dados passado da outra tela
            val category =
                navController.previousBackStackEntry?.savedStateHandle?.get<Category>("categories")
                    ?: Category(
                        "",
                        "",
                        "", ""
                    )
            CategoryDetailScreen(category = category)
        }
    }
}