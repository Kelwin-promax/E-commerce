package com._DM.E_commerce.Controller;

import com._DM.E_commerce.Entity.Endereco;
import com._DM.E_commerce.Service.EnderecoService;
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

@RestController
@RequestMapping("/enderecos")
public class EnderecoController {

    private final EnderecoService enderecoService;

    public EnderecoController(EnderecoService enderecoService) {
        this.enderecoService = enderecoService;
    }

    @GetMapping
    public List<Endereco> listar() {
        return enderecoService.listar();
    }

    @GetMapping("/{id}")
    public Endereco buscarPorId(@PathVariable UUID id) {
        return enderecoService.buscarPorId(id);
    }

    @GetMapping("/cliente/{clienteId}")
    public List<Endereco> listarPorCliente(@PathVariable UUID clienteId) {
        return enderecoService.listarPorCliente(clienteId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Endereco criar(@RequestBody Endereco endereco) {
        return enderecoService.criar(endereco);
    }

    @PutMapping("/{id}")
    public Endereco atualizar(@PathVariable UUID id, @RequestBody Endereco endereco) {
        return enderecoService.atualizar(id, endereco);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable UUID id) {
        enderecoService.remover(id);
    }
}
