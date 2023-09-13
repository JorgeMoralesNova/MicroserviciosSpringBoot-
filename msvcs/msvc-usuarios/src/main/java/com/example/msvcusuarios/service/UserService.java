package com.example.msvcusuarios.service;

import com.example.msvcusuarios.models.entity.Usuario;

import java.util.List;
import java.util.Optional;


public interface UserService {

    List<Usuario> listar();

    Optional<Usuario> findById(Long id);

    Usuario save(Usuario usuario);

    void delete(Long id);

    List<Usuario> listarbyid(Iterable<Long> ids);


}
