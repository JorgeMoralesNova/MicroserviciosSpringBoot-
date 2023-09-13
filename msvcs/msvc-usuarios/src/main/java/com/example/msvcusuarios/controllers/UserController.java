package com.example.msvcusuarios.controllers;

import com.example.msvcusuarios.models.entity.Usuario;
import com.example.msvcusuarios.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class UserController {

    @Autowired
    private UserService userService;


    @GetMapping
    public ResponseEntity<?> listar() {

        List<Usuario> usuarios;

        try {
            usuarios = userService.listar();
        } catch (Exception exception) {
            return ResponseEntity.internalServerError().body(Collections.singletonMap("error", exception.getMessage()));
        }

        if (usuarios == null || usuarios.isEmpty()) {

            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(usuarios);
    }


    @GetMapping("/{id}")
    public ResponseEntity<?> findbyid(@PathVariable Long id) {

        Optional<Usuario> byId;

        try {
            byId = userService.findById(id);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(Collections.singletonMap("error", e.getMessage()));
        }

        if (byId.isPresent()) {

            return ResponseEntity.ok(byId.get());
        }

        return ResponseEntity.notFound().build();
    }


    @PostMapping
    public ResponseEntity<?> guardar(@Valid @RequestBody Usuario usuario, BindingResult result) {

        if (result.hasErrors()) {

            return getErrors(result);

        }

        return ResponseEntity.status(HttpStatus.CREATED).body(userService.save(usuario));

    }


    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @Valid @RequestBody Usuario usuario, BindingResult result) {

        Optional<Usuario> byId;

        try {
            byId = userService.findById(id);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(Collections.singletonMap("error", e.getMessage()));
        }

        if (result.hasErrors()) {
            return getErrors(result);
        }

        if (byId.isPresent()) {

            byId.get().setName(usuario.getName());
            byId.get().setEmail(usuario.getEmail());
            byId.get().setPassword(usuario.getPassword());

            return ResponseEntity.ok(userService.save(byId.get()));
        }
        return ResponseEntity.notFound().build();
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {

        Optional<Usuario> byId;
        try {
            byId = userService.findById(id);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(Collections.singletonMap("error", e.getMessage()));
        }

        if (byId.isPresent()) {

            userService.delete(id);
            return ResponseEntity.noContent().build();
        }


        return ResponseEntity.notFound().build();

    }


    @GetMapping("/usuarios-curso")
    public ResponseEntity<?> obtenerUsuariosById(@RequestParam List<Long> ids) {

        List<Usuario> listarbyid;

        try {
            listarbyid = userService.listarbyid(ids);
            return ResponseEntity.ok().body(listarbyid);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Collections.singletonMap("error ", e.getMessage()));
        }
    }


    private static ResponseEntity<Map<String, String>> getErrors(BindingResult result) {
        Map<String, String> errores = new HashMap<>();

        result.getFieldErrors().forEach(error -> {
            errores.put(error.getField(), "el campo ".concat(error.getField().concat("    ".concat(error.getDefaultMessage()))));

        });

        return ResponseEntity.badRequest().body(errores);
    }
}
