package com.example.msvcusuarios.service;

import com.example.msvcusuarios.clients.CursoClientRest;
import com.example.msvcusuarios.models.entity.Usuario;
import com.example.msvcusuarios.models.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CursoClientRest clientRest;

    @Transactional(readOnly = true)
    @Override
    public List<Usuario> listar() {
        return userRepository.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Usuario> findById(Long id) {
        return userRepository.findById(id);
    }

    @Transactional
    @Override
    public Usuario save(Usuario usuario) {
        return userRepository.save(usuario);
    }

    @Transactional
    @Override
    public void delete(Long id) {

        Optional<Usuario> user = userRepository.findById(id);

        userRepository.deleteById(id);
        clientRest.eliminarCursoUsuarioById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Usuario> listarbyid(Iterable<Long> ids) {
        return (List<Usuario>)
                userRepository.findAllById(ids);
    }


}
