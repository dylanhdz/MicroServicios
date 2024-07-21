package com.espe.msvc_cursos.controllers;

import com.espe.msvc_cursos.models.Usuario;
import com.espe.msvc_cursos.models.entity.Curso;
import com.espe.msvc_cursos.services.CursoService;
import feign.FeignException;
import feign.Request;
import org.junit.jupiter.api.BeforeEach;
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
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CursoController.class)
@ExtendWith(SpringExtension.class)
public class CursoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CursoService cursoService;

    @Test
    public void listarTest() throws Exception {
        Curso curso = new Curso();
        curso.setId(1L);
        curso.setNombre("Curso de Spring Boot");

        given(cursoService.listar()).willReturn(Arrays.asList(curso));

        mockMvc.perform(get("/api/cursos/listar")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nombre").value("Curso de Spring Boot"));
    }

    @Test
    public void detalleTest() throws Exception {
        Curso curso = new Curso();
        curso.setId(1L);
        curso.setNombre("Curso de Spring Boot");

        given(cursoService.porId(1L)).willReturn(Optional.of(curso));

        mockMvc.perform(get("/api/cursos/detalles/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Curso de Spring Boot"));
    }

    @Test
    public void detalleCursoNoExistenteTest() throws Exception {
        Long cursoIdInexistente = 2L;
        given(cursoService.porId(cursoIdInexistente)).willReturn(Optional.empty());

        mockMvc.perform(get("/api/cursos/detalles/{id}", cursoIdInexistente)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void detalleConIdInvalidoTest() throws Exception {
        String idInvalido = "abc";
        mockMvc.perform(get("/api/cursos/detalles/{id}", idInvalido)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void crearTest() throws Exception {
        Curso curso = new Curso();
        curso.setId(1L);
        curso.setNombre("Curso de Spring Boot");

        given(cursoService.guardar(any(Curso.class))).willAnswer(invocation -> invocation.getArgument(0));

        mockMvc.perform(post("/api/cursos/crear")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nombre\":\"Curso de Spring Boot\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nombre").value("Curso de Spring Boot"));
    }
    @Test
    public void crearCursoConDatosIncompletosTest() throws Exception {
        mockMvc.perform(post("/api/cursos/crear")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}")) // Datos incompletos
                .andExpect(status().isBadRequest()); // Esperamos un estado HTTP 400 Bad Request
    }

    @Test
    public void crearCursoConDatosInvalidosTest() throws Exception {
        mockMvc.perform(post("/api/cursos/crear")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nombre\":\"\"}")) // Nombre inválido (vacío)
                .andExpect(status().isBadRequest()); // Esperamos un estado HTTP 400 Bad Request
    }
    @Test
    public void actualizarTest() throws Exception {
        Curso curso = new Curso();
        curso.setId(1L);
        curso.setNombre("Curso de Spring Boot Actualizado");

        given(cursoService.porId(1L)).willReturn(Optional.of(curso));
        given(cursoService.guardar(any(Curso.class))).willAnswer(invocation -> invocation.getArgument(0));

        mockMvc.perform(put("/api/cursos/actualizar/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nombre\":\"Curso de Spring Boot Actualizado\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nombre").value("Curso de Spring Boot Actualizado"));
    }
    @Test
    public void actualizarCursoNoExistenteTest() throws Exception {
        Long cursoIdInexistente = 100L; // Un ID que se asume no existe en la base de datos
        given(cursoService.porId(cursoIdInexistente)).willReturn(Optional.empty());

        mockMvc.perform(put("/api/cursos/actualizar/{id}", cursoIdInexistente)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nombre\":\"Curso Inexistente\"}"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void eliminarTest() throws Exception {
        Curso curso = new Curso();
        curso.setId(1L);

        given(cursoService.porId(1L)).willReturn(Optional.of(curso));
        doNothing().when(cursoService).eliminar(1L);

        mockMvc.perform(delete("/api/cursos/eliminar/{id}", 1L))
                .andExpect(status().isNoContent());
    }

    @Test
    public void asignarUsuarioTest() throws Exception {
        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setNombre("Usuario Test");

        given(cursoService.agregarUsuario(any(Usuario.class), eq(1L))).willReturn(Optional.of(usuario));

        mockMvc.perform(put("/api/cursos/asignar-usuario/{idcurso}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":1,\"nombre\":\"Usuario Test\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nombre").value("Usuario Test"));
    }
    @Test
    public void asignarUsuarioACursoNoExistenteTest() throws Exception {
        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setNombre("Usuario Test");

        Long cursoIdInexistente = 999L; // ID de curso que se asume no existe
        given(cursoService.agregarUsuario(any(Usuario.class), eq(cursoIdInexistente))).willReturn(Optional.empty());

        mockMvc.perform(put("/api/cursos/asignar-usuario/{idcurso}", cursoIdInexistente)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":1,\"nombre\":\"Usuario Test\"}"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void asignarUsuarioConDatosInvalidosTest() throws Exception {
        mockMvc.perform(put("/api/cursos/asignar-usuario/{idcurso}", "abc") // ID de curso inválido
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":\"xyz\",\"nombre\":\"Usuario Test\"}")) // ID de usuario inválido
                .andExpect(status().isBadRequest());
    }

    
}