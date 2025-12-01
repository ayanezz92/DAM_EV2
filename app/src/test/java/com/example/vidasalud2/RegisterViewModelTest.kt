package com.example.vidasalud2.features.register

import com.example.vidasalud2.data.local.ActivityDao
import com.example.vidasalud2.data.local.ActivityEntity
import io.mockk.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class RegisterViewModelTest {

    private lateinit var viewModel: RegisterViewModel
    private lateinit var dao: ActivityDao
    private val testDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        dao = mockk(relaxed = true) // Creamos un DAO falso
        viewModel = RegisterViewModel(dao)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `saveActivity NO debe guardar si el nombre esta vacio`() = runTest {
        // 1. Damos datos incompletos (Nombre vacío)
        viewModel.onActivityNameChange("")
        viewModel.onActivityDurationChange("30")

        // 2. Ejecutamos guardar
        viewModel.saveActivity()

        // 3. Verificamos que el DAO NUNCA fue llamado
        coVerify(exactly = 0) { dao.insertar(any()) }

        // 4. Verificamos el mensaje de error
        assertEquals("Error: Debes ingresar nombre y duración", viewModel.saveMessage.value)
    }

    @Test
    fun `saveActivity DEBE guardar si los datos son correctos`() = runTest {
        // 1. Llenamos los datos correctamente
        viewModel.onActivityNameChange("Correr")
        viewModel.onActivityDurationChange("45")

        // 2. Ejecutamos guardar
        viewModel.saveActivity()

        // 3. Verificamos que el DAO SÍ fue llamado una vez
        coVerify(exactly = 1) { dao.insertar(any()) }

        // 4. Verificamos mensaje de éxito
        assertEquals("¡Actividad guardada correctamente!", viewModel.saveMessage.value)
    }
}