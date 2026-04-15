package com._DM.E_commerce.Service;

import com._DM.E_commerce.Entity.Endereco;
import com._DM.E_commerce.Entity.Usuario;
import com._DM.E_commerce.Repository.EnderecoRepository;
import com._DM.E_commerce.Repository.UsuarioRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@Service
public class EnderecoService {

    private final EnderecoRepository enderecoRepository;
    private final UsuarioRepository usuarioRepository;

    public EnderecoService(EnderecoRepository enderecoRepository, UsuarioRepository usuarioRepository) {
        this.enderecoRepository = enderecoRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public List<Endereco> listar() {
        return enderecoRepository.findAll();
    }

    public List<Endereco> listarPorCliente(UUID clienteId) {
        validarCliente(clienteId);
        return enderecoRepository.findByClienteId(clienteId);
    }

    public Endereco buscarPorId(UUID id) {
        return enderecoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Endereco nao encontrado"));
    }

    public Endereco criar(Endereco endereco) {
        Usuario cliente = validarCliente(endereco.getCliente().getId());
        endereco.setId(null);
        endereco.setCliente(cliente);
        return enderecoRepository.save(endereco);
    }

    public Endereco atualizar(UUID id, Endereco enderecoAtualizado) {
        Endereco endereco = buscarPorId(id);
        Usuario cliente = validarCliente(enderecoAtualizado.getCliente().getId());

        endereco.setLogradouro(enderecoAtualizado.getLogradouro());
        endereco.setNumero(enderecoAtualizado.getNumero());
        endereco.setComplemento(enderecoAtualizado.getComplemento());
        endereco.setBairro(enderecoAtualizado.getBairro());
        endereco.setCidade(enderecoAtualizado.getCidade());
        endereco.setEstado(enderecoAtualizado.getEstado());
        endereco.setCep(enderecoAtualizado.getCep());
        endereco.setReferencia(enderecoAtualizado.getReferencia());
        endereco.setCliente(cliente);

        return enderecoRepository.save(endereco);
    }

    public void remover(UUID id) {
        enderecoRepository.delete(buscarPorId(id));
    }

    private Usuario validarCliente(UUID clienteId) {
        if (clienteId == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cliente obrigatorio");
        }

        return usuarioRepository.findById(clienteId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario nao encontrado"));
    }
}
