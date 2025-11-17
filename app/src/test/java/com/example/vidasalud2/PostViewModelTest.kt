package com.example.vidasalud2.viewmodel

// Tus imports...
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vidasalud2.data.model.Post
import com.example.vidasalud2.repository.PostRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

// --- CAMBIO 1 ---
// La clase debe ser 'open' para que el test pueda heredar de ella.
open class PostViewModel(
    private val repository: PostRepository // Asumo que recibes el repositorio aquí
) : ViewModel() {

    // --- CAMBIO 2 ---
    // Quita 'private' de _postList para que el test pueda acceder a él.
    // Si era: private val _postList = ...
    // Cámbialo a:
    val _postList = MutableStateFlow<List<Post>>(emptyList())
    val postList = _postList.asStateFlow()

    init {
        fetchPosts()
    }

    // --- CAMBIO 3 ---
    // La función debe ser 'open' para que el test pueda sobrescribirla.
    open fun fetchPosts() {
        viewModelScope.launch {
            try {
                val posts = repository.getPosts() // Asumo que llamas a tu repositorio
                _postList.value = posts
            } catch (e: Exception) {
                // Manejar error
                _postList.value = emptyList()
            }
        }
    }
}