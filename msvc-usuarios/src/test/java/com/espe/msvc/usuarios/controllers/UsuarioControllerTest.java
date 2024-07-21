package com.espe.msvc.usuarios.controllers;

import com.espe.msvc.usuarios.models.entity.Usuario;
import com.espe.msvc.usuarios.services.UsuarioService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UsuarioController.class)
@ExtendWith(SpringExtension.class)
public class UsuarioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UsuarioService usuarioService;

    @Test
    public void listarTest() throws Exception {
        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setNombre("Juan Perez");
        usuario.setEmail("juan@example.com");
        usuario.setPassword("1234");

        given(usuarioService.listar()).willReturn(Arrays.asList(usuario));

        mockMvc.perform(get("/api/usuarios/listar")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nombre").value("Juan Perez"));
    }
    @Test
    public void listarTestCuandoListaVacia() throws Exception {
        given(usuarioService.listar()).willReturn(Arrays.asList());

        mockMvc.perform(get("/api/usuarios/listar")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("[]"));
    }

    @Test
    public void detalleTest() throws Exception {
        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setNombre("Juan Perez");

        given(usuarioService.porId(1L)).willReturn(Optional.of(usuario));

        mockMvc.perform(get("/api/usuarios/detalles/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Juan Perez"));
    }

    @Test
    public void detalleUsuarioInexistenteTest() throws Exception {
        Long usuarioIdInexistente = 999L;
        given(usuarioService.porId(usuarioIdInexistente)).willReturn(Optional.empty());

        mockMvc.perform(get("/api/usuarios/detalles/{id}", usuarioIdInexistente)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void crearTest() throws Exception {
        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setNombre("Juan Perez");

        given(usuarioService.guardar(any(Usuario.class))).willReturn(usuario);

        mockMvc.perform(post("/api/usuarios/crear")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nombre\":\"Juan Perez\", \"email\":\"juan@example.com\", \"password\":\"1234\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nombre").value("Juan Perez"));
    }

   

    @Test
    public void editarTest() throws Exception {
        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setNombre("Juan Perez Actualizado");

        given(usuarioService.porId(1L)).willReturn(Optional.of(usuario));
        given(usuarioService.guardar(any(Usuario.class))).willReturn(usuario);

        mockMvc.perform(put("/api/usuarios/actualizar/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nombre\":\"Juan Perez Actualizado\", \"email\":\"juan@example.com\", \"password\":\"1234\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nombre").value("Juan Perez Actualizado"));
    }

    @Test
    public void eliminarTest() throws Exception {
        Usuario usuario = new Usuario();
        usuario.setId(1L);

        given(usuarioService.porId(1L)).willReturn(Optional.of(usuario));
        mockMvc.perform(delete("/api/usuarios/eliminar/{id}", 1L))
                .andExpect(status().isNoContent());
    }
}