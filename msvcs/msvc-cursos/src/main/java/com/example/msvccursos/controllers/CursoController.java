package com.example.msvccursos.controllers;

import com.example.msvccursos.models.Usuario;
import com.example.msvccursos.models.entity.Curso;
import com.example.msvccursos.service.CursoService;
import feign.FeignException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class CursoController {

    @Autowired
    private CursoService cursoService;

    @GetMapping
    public ResponseEntity<?> listar(){

        List<Curso> cursos;

        try {
            cursos=cursoService.listar();
        }catch (Exception e){
            return ResponseEntity.badRequest().body(Collections.singletonMap("error", e.getMessage()));
        }

        if (cursos!=null){

            return ResponseEntity.ok(cursos);
        }

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> byid(@PathVariable Long id){

        Optional<Curso> curso;

        try {
            curso =cursoService.findbyidwithUsers(id);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(Collections.singletonMap("error", e.getMessage()));
        }
        if (curso.isPresent()){
            return ResponseEntity.ok(curso.get());
        }
        return ResponseEntity.notFound().build();

    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> crear(@Valid @RequestBody Curso curso, BindingResult result){

        if (result.hasErrors()) {

            return getErrors(result);

        }
        return ResponseEntity.status(HttpStatus.CREATED).body(cursoService.save(curso));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@Valid @RequestParam Long id, @RequestBody Curso curso, BindingResult result){

        if (result.hasErrors()) {

            return getErrors(result);

        }


        Optional<Curso> findbyid;

        try {
            findbyid=cursoService.findbyid(id);
        }catch (Exception e){
            return ResponseEntity.badRequest().body(Collections.singletonMap("ewrror", e.getMessage()));
        }

        if (findbyid.isPresent()){
            Curso curso1 = findbyid.get();
            curso1.setNombre(curso.getNombre());

            return new ResponseEntity<>(cursoService.save(curso1), HttpStatus.CREATED);
        }

        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletebyid(@PathVariable Long id){
        Optional<Curso> optionalCurso;

        try {
            optionalCurso=cursoService.findbyid(id);
        }catch (Exception e){
            return ResponseEntity.badRequest().body(Collections.singletonMap("error", e.getMessage()));
        }

        if (optionalCurso.isPresent()){
            cursoService.delete(id);

            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.notFound().build();
    }

    @PutMapping("/asignar-usuario/{cusoid}")
    public ResponseEntity<?> asignarUsuario(@PathVariable Long cusoid, @RequestBody Usuario usuario, BindingResult result){

        if (result.hasErrors()){
            return getErrors(result);
        }

        Optional<Usuario> Ousuario;
        try {
            Ousuario=cursoService.asignarUsuario(usuario, cusoid);
        }catch (FeignException e){

            return ResponseEntity.badRequest().body(Collections.singletonMap("error: ", e.getMessage()));
        }

        if (Ousuario.isPresent()){

            return ResponseEntity.status(HttpStatus.CREATED).body(Ousuario.get());
        }

        return ResponseEntity.notFound().build();
    }

    @PostMapping("/crear-usuario/{idCurso}")
    public ResponseEntity<?> crearUsuario(@RequestBody Usuario usuario, @PathVariable Long idCurso){

        Optional<Usuario> usuarioOptional;

        try {
            usuarioOptional= cursoService.crearUsuario(usuario, idCurso);
        }catch (FeignException e){

            return ResponseEntity.badRequest().body(Collections.singletonMap("error: ", e.getMessage()));
        }

        if (usuarioOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.CREATED).body(usuarioOptional.get());
        }

        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/eliminarUsuario/{idCurso}")
    public ResponseEntity <?> eliminarUsuario(@RequestBody Usuario usuario, BindingResult result, @PathVariable Long idCurso){

        if (result.hasErrors()){
            return getErrors(result);
        }

        Optional<Usuario> usuarioOptional;

        try {
            usuarioOptional= cursoService.desAsignarUsuario(usuario,idCurso);
        }catch (FeignException feignException){
            return ResponseEntity.badRequest().body(Collections.singletonMap("error: ", feignException.getMessage()));
        }

        if (usuarioOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.CREATED).body(usuarioOptional.get());
        }

        return ResponseEntity.notFound().build();
    }


    @DeleteMapping("/eliminarUsuarioById/{id}")
    public ResponseEntity<?> eliminarCursoUsuarioById(@PathVariable Long id){

        try {
            cursoService.eliminarCursoUsuarioById(id);
        }catch (Exception exception){
            return ResponseEntity.internalServerError().body(Collections.singletonMap("error", exception.getMessage()));
        }

       return ResponseEntity.ok(Collections.singletonMap("exito", "operacion exitosa"));

    }








    private static ResponseEntity<Map<String, String>> getErrors(BindingResult result) {
        Map<String, String> errores= new HashMap<>();

        result.getFieldErrors().forEach(error-> {
            errores.put(error.getField(), "el campo ".concat(error.getField().concat("  ".concat(error.getDefaultMessage()))));

        });

        return ResponseEntity.badRequest().body(errores);
    }





}



