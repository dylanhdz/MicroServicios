package com.espe.msvc.usuarios.services;

import com.espe.msvc.usuarios.models.entity.Usuario;

import java.util.List;
import java.util.Optional;

public interface UsuarioService {
    List<Usuario> listar();
    Optional<Usuario> porId (Long id);
    Optional<Usuario> porNombre(String nombre);
    Usuario guardar(Usuario usuario);
    void eliminar(Long id);
}
