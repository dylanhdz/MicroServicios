package com.espe.msvc_cursos.services;

import com.espe.msvc_cursos.clients.UsuarioClientRest;
import com.espe.msvc_cursos.models.Usuario;
import com.espe.msvc_cursos.models.entity.Curso;
import com.espe.msvc_cursos.repositories.CursoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class CursoServiceTest {

    @MockBean
    private CursoRepository cursoRepository;

    @MockBean
    private UsuarioClientRest usuarioClientRest;

    @Autowired
    @InjectMocks
    private CursoServiceImpl cursoService;

    @Test
    public void porIdTest() {
        // Configura el mock para devolver un valor específico cuando se llame al método findById
        Curso curso = new Curso();
        curso.setId(1L);
        when(cursoRepository.findById(1L)).thenReturn(Optional.of(curso));

        // Llama al método porId del servicio
        Optional<Curso> resultado = cursoService.porId(1L);

        // Verifica que el resultado es el esperado
        assertTrue(resultado.isPresent(), "El curso no fue encontrado");
        assertEquals(1L, resultado.get().getId(), "El ID del curso no coincide");

        // Verifica que se llamó al método findById del repositorio
        verify(cursoRepository).findById(1L);
    }
    @Test
    public void listarTest() {
        // Configurar el mock para devolver una lista de cursos
        List<Curso> cursos = new ArrayList<>();
        cursos.add(new Curso());
        when(cursoRepository.findAll()).thenReturn(cursos);

        // Llamar al método listar
        List<Curso> resultado = cursoService.listar();

        // Verificar el resultado
        assertFalse(resultado.isEmpty(), "La lista de cursos está vacía");
        verify(cursoRepository).findAll();
    }

    @Test
    public void guardarTest() {
        // Configurar el mock para guardar y devolver el curso
        Curso curso = new Curso();
        curso.setNombre("Curso de prueba");
        when(cursoRepository.save(any(Curso.class))).thenReturn(curso);

        // Llamar al método guardar
        Curso resultado = cursoService.guardar(new Curso());

        // Verificar el resultado
        assertNotNull(resultado, "El curso guardado es nulo");
        assertEquals("Curso de prueba", resultado.getNombre(), "El nombre del curso no coincide");
    }

    @Test
    public void eliminarTest() {
        // Configurar el mock para simular la eliminación
        doNothing().when(cursoRepository).deleteById(1L);

        // Llamar al método eliminar
        cursoService.eliminar(1L);

        // Verificar que se llamó al método deleteById del repositorio
        verify(cursoRepository).deleteById(1L);
    }
    
    
}