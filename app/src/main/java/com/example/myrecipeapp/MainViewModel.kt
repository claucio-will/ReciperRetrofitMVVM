package com.example.myrecipeapp

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    private val _categorieState = mutableStateOf(RecipeState())
    val categoriesState: State<RecipeState> = _categorieState

    init {
        fetchCategories()
    }

    private fun fetchCategories() {
        //  viewModelScope.launch é usado quando temos função suspend
        viewModelScope.launch {
            try {
                //chama o metodos de getCategories() da interface ApiService
              val response = recipeService.getCategories()

                //Copia os valores novos para dentro da data class
                //Usando o copy, todas as propiedades que passamos será alteradas
                //as que não passamos vai continuar do jeito que era
                _categorieState.value = _categorieState.value.copy(
                    list = response.categories,
                    loading = false,
                    error = null
                )
            } catch (e: Exception) {
                //Como não passamos a lista detro desse copy, ela vai continuar do jeito que era
                // ou seja ainda vai está vazia
                _categorieState.value = _categorieState.value.copy(
                    loading = false,
                    error = "Erro fetching Categories ${e.message}"
                )
            }
        }
    }


    data class RecipeState(
        val loading: Boolean = true,
        val list: List<Category> = emptyList(),
        val error: String? = null
    )
}