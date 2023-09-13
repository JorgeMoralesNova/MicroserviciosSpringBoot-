package com.example.msvccursos.models.entity;

import com.example.msvccursos.models.Usuario;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "cursos")
public class Curso {

    public Curso(){

        cursoUsuarios= new ArrayList<>();
        usuarios= new ArrayList<>();
    }


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    private String nombre;

    @Transient
    private List<Usuario> usuarios;


    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)//un curso puede tener muchos usuarios,
    //si se borra un curso, se remueven cursos id sin asignarse
    @JoinColumn(name = "usuario_id")
    private List<CursoUsuario> cursoUsuarios;


    public void addCursoUsuario(CursoUsuario usuario){

        cursoUsuarios.add(usuario);
    }

    public void removeCursoUsuario(CursoUsuario usuario){

        cursoUsuarios.remove(usuario);
    }



    //getter and setter
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<CursoUsuario> getCursoUsuarios() {
        return cursoUsuarios;
    }

    public List<Usuario> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(List<Usuario> usuarios) {
        this.usuarios = usuarios;
    }

    public void setCursoUsuarios(List<CursoUsuario> cursoUsuarios) {
        this.cursoUsuarios = cursoUsuarios;
    }
}
