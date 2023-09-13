package com.example.msvcusuarios.models.repository;

import com.example.msvcusuarios.models.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<Usuario, Long> {


}
