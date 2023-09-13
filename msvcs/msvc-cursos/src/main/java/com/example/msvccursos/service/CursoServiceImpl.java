package com.example.msvccursos.service;

import com.example.msvccursos.clients.UsuarioClientRest;
import com.example.msvccursos.models.Usuario;
import com.example.msvccursos.models.entity.Curso;
import com.example.msvccursos.models.entity.CursoUsuario;
import com.example.msvccursos.models.repository.CursoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CursoServiceImpl implements CursoService {

    @Autowired
    private CursoRepository cursoRepository;

    @Autowired
    private UsuarioClientRest clientRest;


    @Override
    @Transactional(readOnly = true)
    public List<Curso> listar() {
        return cursoRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Curso> findbyid(Long id) {
        return cursoRepository.findById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Curso> findbyidwithUsers(Long id) {

        Optional<Curso> cursoOptional= cursoRepository.findById(id);

        if (cursoOptional.isPresent()){

            Curso curso=cursoOptional.get();

            if (!curso.getCursoUsuarios().isEmpty()){
                List<Long> ids=curso.getCursoUsuarios().stream().map(CursoUsuario::getUsuarioId).toList();

                List<Usuario> usuarios= clientRest.obtenerAlumnosPorCurso(ids);

                curso.setUsuarios(usuarios);

            }
            return Optional.of(curso);
        }

        return Optional.empty();


    }

    @Override
    @Transactional
    public Curso save(Curso curso) {

        return (Curso) cursoRepository.save(curso);
    }

    @Override
    @Transactional
    public void delete(Long id) {

        cursoRepository.deleteById(id);

    }

    @Transactional
    @Override
    public void eliminarCursoUsuarioById(Long id) {

        cursoRepository.deleteCursoUsuarioByid(id);
    }

    @Transactional
    @Override
    public Optional<Usuario> asignarUsuario(Usuario usuario, Long cursoId) {
        Optional<Curso> c = cursoRepository.findById(cursoId);

        if (c.isPresent()){
            Usuario usuario1= clientRest.findbyid(usuario.getId());

            Curso curso= c.get();

            CursoUsuario cursoUsuario= new CursoUsuario();

            cursoUsuario.setUsuarioId(usuario1.getId());

            curso.addCursoUsuario(cursoUsuario);

            cursoRepository.save(curso);

            return Optional.of(usuario1);

        }



        return Optional.empty();
    }

    @Transactional
    @Override
    public Optional<Usuario> crearUsuario(Usuario usuario, Long cursoId) {
        Optional<Curso> c = cursoRepository.findById(cursoId);

        if (c.isPresent()){
            Usuario usuario1= clientRest.crear(usuario);

            Curso curso= c.get();

            CursoUsuario cursoUsuario= new CursoUsuario();

            cursoUsuario.setUsuarioId(usuario1.getId());

            curso.addCursoUsuario(cursoUsuario);

            cursoRepository.save(curso);

            return Optional.of(usuario1);

        }



        return Optional.empty();
    }

    @Transactional
    @Override
    public Optional<Usuario> desAsignarUsuario(Usuario usuario, Long cursoId) {
        Optional<Curso> c = cursoRepository.findById(cursoId);

        if (c.isPresent()){
            Usuario usuario1= clientRest.findbyid(usuario.getId());

            Curso curso= c.get();

            CursoUsuario cursoUsuario= new CursoUsuario();

            cursoUsuario.setUsuarioId(usuario1.getId());

            curso.removeCursoUsuario(cursoUsuario);

            cursoRepository.save(curso);

            return Optional.of(usuario1);

        }



        return Optional.empty();
    }

}
