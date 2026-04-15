package com._DM.E_commerce.Service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@Service("usuarioService")
public class Usuario {

    private final com._DM.E_commerce.Repository.UsuarioRepository repository;

    public Usuario(com._DM.E_commerce.Repository.UsuarioRepository repository) {
        this.repository = repository;
    }

    public List<com._DM.E_commerce.Entity.Usuario> listar() {
        return repository.findAll();
    }

    public com._DM.E_commerce.Entity.Usuario buscarPorId(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario nao encontrado"));
    }

    public com._DM.E_commerce.Entity.Usuario criar(com._DM.E_commerce.Entity.Usuario usuario) {
        usuario.setId(null);
        return repository.save(usuario);
    }

    public com._DM.E_commerce.Entity.Usuario atualizar(UUID id, com._DM.E_commerce.Entity.Usuario usuarioAtualizado) {
        com._DM.E_commerce.Entity.Usuario usuario = buscarPorId(id);
        usuario.setNome(usuarioAtualizado.getNome());
        usuario.setEmail(usuarioAtualizado.getEmail());
        usuario.setTelefone(usuarioAtualizado.getTelefone());
        usuario.setSenha(usuarioAtualizado.getSenha());
        usuario.setRole(usuarioAtualizado.getRole());
        return repository.save(usuario);
    }

    public void remover(UUID id) {
        repository.delete(buscarPorId(id));
    }
}
