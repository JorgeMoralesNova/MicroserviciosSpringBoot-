package com.example.msvcusuarios.clients;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "msvc-cursos", url = "localhost:8002")
public interface CursoClientRest {

    @DeleteMapping("/eliminarUsuarioById/{id}")
    void eliminarCursoUsuarioById(@PathVariable Long id);


}
