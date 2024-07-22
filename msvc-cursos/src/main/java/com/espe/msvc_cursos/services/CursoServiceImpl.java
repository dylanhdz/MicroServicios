package com.espe.msvc_cursos.services;

import com.espe.msvc_cursos.clients.UsuarioClientRest;
import com.espe.msvc_cursos.models.Usuario;
import com.espe.msvc_cursos.models.entity.Curso;
import com.espe.msvc_cursos.models.entity.CursoUsuario;
import com.espe.msvc_cursos.repositories.CursoRepository;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CursoServiceImpl implements CursoService {
    @Autowired
    private CursoRepository repository;

    @Autowired
    UsuarioClientRest clientRest;
    @Autowired
    private UsuarioClientRest usuarioClientRest;

    @Override
    @Transactional(readOnly = true)
    public List<Curso> listar() {
        return (List<Curso>) repository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Curso> porId(Long id) {
        return repository.findById(id);
    }

    @Override
    @Transactional
    public Curso guardar(Curso curso) {
        return repository.save(curso);
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        repository.deleteById(id);
    }

    @Override
    public Optional<Usuario> agregarUsuario(Usuario usuario, Long idCurso) {
        // Verificar que existe el curso
        Optional<Curso> o = repository.findById(idCurso);
        if (o.isPresent()) {
            // Verificar que existe el usuario
            Usuario usuarioMicro = usuarioClientRest.detalle(usuario.getId());
            // Agregamos el usuario al curso
            Curso curso = o.get();
            CursoUsuario cursoUsuario = new CursoUsuario();
            cursoUsuario.setUsuarioId(usuarioMicro.getId());

            curso.addCursoUsuario(cursoUsuario);
            repository.save(curso);

            // Return the usuario that was added
            return Optional.of(usuarioMicro);
        }

        // If the course does not exist, return Optional.empty()
        return Optional.empty();
    }

    @Override
    public Optional<Usuario> crearUsuario(Usuario usuario, Long idCurso) {
        // Verificar que existe el curso
        Optional<Curso> o = repository.findById(idCurso);
        if(o.isPresent()) {
            // Verificar que existe el usuario
            Usuario usuarioMicro = usuarioClientRest.crear(usuario);
            // Agregamos el usuario al curso
            Curso curso = o.get();
            CursoUsuario cursoUsuario = new CursoUsuario();
            cursoUsuario.setUsuarioId(usuarioMicro.getId());

            curso.addCursoUsuario(cursoUsuario);
            repository.save(curso);
        }
        return Optional.empty();
    }

    @Override
    public Optional<Usuario> eliminarUsuario(Usuario usuario, Long idCurso) {
        // Verificar que existe el curso
        Optional<Curso> o = repository.findById(idCurso);
        if(o.isPresent()) {
            // Verificar que existe el usuario
            Usuario usuarioMicro = usuarioClientRest.detalle(usuario.getId());
            // Eliminamos el usuario del curso
            Curso curso = o.get();
            CursoUsuario cursoUsuario = new CursoUsuario();
            cursoUsuario.setUsuarioId(usuarioMicro.getId());

            curso.removeCursoUsuario(cursoUsuario);
            repository.save(curso);
        }
        return Optional.empty();
    }
}
