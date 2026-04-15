package com._DM.E_commerce.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController("usuarioController")
@RequestMapping("/usuarios")
public class Usuario {

    private final com._DM.E_commerce.Service.Usuario service;

    public Usuario(com._DM.E_commerce.Service.Usuario service) {
        this.service = service;
    }

    @GetMapping
    public List<com._DM.E_commerce.Entity.Usuario> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public com._DM.E_commerce.Entity.Usuario buscarPorId(@PathVariable UUID id) {
        return service.buscarPorId(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public com._DM.E_commerce.Entity.Usuario criar(@RequestBody com._DM.E_commerce.Entity.Usuario usuario) {
        return service.criar(usuario);
    }

    @PutMapping("/{id}")
    public com._DM.E_commerce.Entity.Usuario atualizar(@PathVariable UUID id,
                                                        @RequestBody com._DM.E_commerce.Entity.Usuario usuario) {
        return service.atualizar(id, usuario);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable UUID id) {
        service.remover(id);
    }
}
