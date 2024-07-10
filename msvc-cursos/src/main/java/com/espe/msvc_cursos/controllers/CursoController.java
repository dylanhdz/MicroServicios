package com.espe.msvc_cursos.controllers;

import com.espe.msvc_cursos.models.entity.Curso;
import com.espe.msvc_cursos.services.CursoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/cursos")
public class CursoController {
    @Autowired
    private CursoService service;

    @GetMapping("/listar")
    public List<Curso> listar(){
        return service.listar();
    }

    @GetMapping("/detalles/{id}")
    public ResponseEntity<?> detalle(@PathVariable Long id){
        Optional<Curso> cursosOptional = service.porId(id);
        if(cursosOptional.isPresent()) {
            return ResponseEntity.ok().body(cursosOptional.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/crear")
    public ResponseEntity<?> crear(@RequestBody Curso curso){
        return ResponseEntity.ok().body(service.guardar(curso));
    }

    @PutMapping("/actualizar/{id}")
    public ResponseEntity<?> editar(@RequestBody Curso curso, @PathVariable Long id) {
        Optional<Curso> cursoOptional = service.porId(id);
        if (cursoOptional.isPresent()) {
            Curso cursoDB = cursoOptional.get();
            cursoDB.setNombre(curso.getNombre());
            return ResponseEntity.ok().body(service.guardar(cursoDB));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id){
        Optional<Curso> optionalCursos= service.porId(id);
        if(optionalCursos.isPresent()) {
            service.eliminar(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
