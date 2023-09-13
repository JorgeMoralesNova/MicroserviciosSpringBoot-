package com.example.msvccursos.service;

import com.example.msvccursos.models.Usuario;
import com.example.msvccursos.models.entity.Curso;

import java.util.List;
import java.util.Optional;

public interface CursoService {

    List<Curso> listar();

    Optional<Curso> findbyid(Long id);
    Optional<Curso> findbyidwithUsers(Long id);

    Curso save(Curso curso);

    void delete(Long id);

    void eliminarCursoUsuarioById(Long id);


    Optional<Usuario> asignarUsuario(Usuario usuario, Long cursoId);

    Optional<Usuario> crearUsuario(Usuario usuario, Long cursoId);
    Optional<Usuario> desAsignarUsuario(Usuario usuario, Long cursoId);







}
