package com.espe.msvc.usuarios.services;

import com.espe.msvc.usuarios.models.entity.Usuario;
import com.espe.msvc.usuarios.repositories.UsuarioRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class UsuarioServiceImplTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private UsuarioServiceImpl usuarioService;

    @Test
    public void listarTest() {
        when(usuarioRepository.findAll()).thenReturn(Arrays.asList(new Usuario()));
        assertNotNull(usuarioService.listar());
        verify(usuarioRepository).findAll();
    }

    @Test
    public void listarTestConUsuariosEspecificos() {
        Usuario usuario1 = new Usuario();
        usuario1.setId(1L);
        usuario1.setNombre("Usuario 1");
        Usuario usuario2 = new Usuario();
        usuario2.setId(2L);
        usuario2.setNombre("Usuario 2");

        when(usuarioRepository.findAll()).thenReturn(Arrays.asList(usuario1, usuario2));
        List<Usuario> usuarios = usuarioService.listar();

        assertNotNull(usuarios);
        assertEquals(2, usuarios.size());
        assertEquals("Usuario 1", usuarios.get(0).getNombre());
        assertEquals("Usuario 2", usuarios.get(1).getNombre());
        verify(usuarioRepository).findAll();
    }

    

    @Test
    public void porIdTest() {
        Long id = 1L;
        when(usuarioRepository.findById(id)).thenReturn(Optional.of(new Usuario()));
        assertTrue(usuarioService.porId(id).isPresent());
        verify(usuarioRepository).findById(id);
    }

    @Test
    public void porIdCuandoNoExisteTest() {
        Long idInexistente = 2L;
        when(usuarioRepository.findById(idInexistente)).thenReturn(Optional.empty());
        assertFalse(usuarioService.porId(idInexistente).isPresent());
        verify(usuarioRepository).findById(idInexistente);
    }

    @Test
    public void guardarTest() {
        Usuario usuario = new Usuario();
        usuario.setNombre("Test User");
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuario);
        Usuario savedUsuario = usuarioService.guardar(new Usuario());
        assertEquals("Test User", savedUsuario.getNombre());
        verify(usuarioRepository).save(any(Usuario.class));
    }
    @Test
    public void guardarTestConExcepcion() {
        Usuario usuarioConError = new Usuario();
        usuarioConError.setNombre("Usuario Con Error");

        when(usuarioRepository.save(any(Usuario.class))).thenThrow(new RuntimeException("Error al guardar"));

        Exception excepcion = assertThrows(RuntimeException.class, () -> usuarioService.guardar(usuarioConError));
        assertEquals("Error al guardar", excepcion.getMessage());

        verify(usuarioRepository).save(any(Usuario.class));
    }

    @Test
    public void eliminarTest() {
        Long id = 1L;
        doNothing().when(usuarioRepository).deleteById(id);
        usuarioService.eliminar(id);
        verify(usuarioRepository).deleteById(id);
    }
    @Test
    public void eliminarUsuarioInexistenteTest() {
        Long idInexistente = 999L;
        doNothing().when(usuarioRepository).deleteById(idInexistente);
        usuarioService.eliminar(idInexistente);
        verify(usuarioRepository).deleteById(idInexistente);
    }
}