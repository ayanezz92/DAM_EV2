package com.example.vidasalud2.viewmodel

import androidx.lifecycle.ViewModel
import com.example.vidasalud2.model.Post
import com.example.vidasalud2.repository.PostRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class PostViewModel : ViewModel() {

    // Instanciamos el repositorio directamente (para simplificar por ahora)
    private val repository = PostRepository()

    // Estado de la UI: Una lista de Posts
    private val _posts = MutableStateFlow<List<Post>>(emptyList())
    val posts: StateFlow<List<Post>> = _posts.asStateFlow()

    init {
        loadPosts()
    }

    private fun loadPosts() {
        // Pedimos los posts al repositorio
        val loadedPosts = repository.getPosts()
        _posts.value = loadedPosts
    }
}