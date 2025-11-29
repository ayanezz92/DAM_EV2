package com.example.vidasalud2.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vidasalud2.data.model.Post
import com.example.vidasalud2.repository.PostRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * ViewModel para gestionar la lista de Posts.
 *
 * Esta clase está marcada como 'open' para permitir que las clases de prueba
 * hereden de ella y simulen su comportamiento.
 */
open class PostViewModel(
    // Asumimos que tu ViewModel recibe el Repositorio por inyección
    // Si no es así, debes instanciarlo (ej: private val repository = PostRepository())
    // PERO la Guía 15 sugiere inyectarlo.
    private val repository: PostRepository
) : ViewModel() {

    /**
     * El estado interno y mutable de la lista de posts.
     * NO es 'private' para que la clase de prueba 'PostViewModelTest' pueda acceder a él
     * y simular la respuesta de la API.
     */
    val _postList = MutableStateFlow<List<Post>>(emptyList())

    /**
     * La lista pública e inmutable de posts que la UI observará.
     */
    val postList = _postList.asStateFlow()

    init {
        // Llama a fetchPosts() cuando el ViewModel se inicializa
        fetchPosts()
    }

    /**
     * Obtiene los posts desde el repositorio de forma asíncrona.
     *
     * Esta función está marcada como 'open' para permitir que 'PostViewModelTest'
     * la sobrescriba ('override') y evite hacer una llamada real a la API durante la prueba.
     */
    open fun fetchPosts() {
        viewModelScope.launch {
            try {
                // Llama al repositorio para obtener los datos
                val posts = repository.getPosts()
                _postList.value = posts
            } catch (e: Exception) {
                // En caso de error, deja la lista vacía
                _postList.value = emptyList()
                // Aquí podrías manejar un estado de error
            }
        }
    }
}