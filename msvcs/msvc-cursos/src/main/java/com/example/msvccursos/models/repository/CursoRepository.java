package com.example.msvccursos.models.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface CursoRepository extends JpaRepository {


    @Modifying  //para que efectivamente se modifique con el query
    @Query("delete from CursoUsuario cu where cu.usuarioId=?1")
    void deleteCursoUsuarioByid(Long id);
}
