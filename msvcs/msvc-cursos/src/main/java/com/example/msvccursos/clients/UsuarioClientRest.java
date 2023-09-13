package com.example.msvccursos.clients;

import com.example.msvccursos.models.Usuario;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "msvc-usuarios", url = "localhost:8001")
public interface UsuarioClientRest {

    @GetMapping("/{id}")
    Usuario findbyid(@PathVariable Long id);

    @PostMapping
    Usuario crear(@RequestBody Usuario usuario);

    @GetMapping("/usuariosByCurso")
    List<Usuario> obtenerAlumnosPorCurso(@RequestParam Iterable<Long> ids);//funciona mejor con iterable en este caso





}
