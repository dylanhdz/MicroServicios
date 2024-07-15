package com.espe.msvc_cursos.clients;

import com.espe.msvc_cursos.models.Usuario;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "msvc-usuarios", url = "localhost:8001")
public interface UsuarioClientRest {

    @GetMapping("/api/usuarios/detalles/{id}")
    Usuario detalle(@PathVariable Long id);

    @PostMapping("/api/usuarios/crear")
    Usuario crear(@RequestBody Usuario usuario);
}
