package com._DM.E_commerce.Service;

import com._DM.E_commerce.Dtos.ItemPedidoCreateDTO;
import com._DM.E_commerce.Dtos.PedidoCreateDTO;
import com._DM.E_commerce.Dtos.PedidoDTO;
import com._DM.E_commerce.Entity.ItemDoPedido;
import com._DM.E_commerce.Entity.Pedido;
import com._DM.E_commerce.Entity.Produto;
import com._DM.E_commerce.Entity.Usuario;
import com._DM.E_commerce.Enum.StatusDoPedido;
import com._DM.E_commerce.Repositories.ItemDoPedidoRepository;
import com._DM.E_commerce.Repositories.PedidoRepository;
import com._DM.E_commerce.Repositories.ProdutoRepository;
import com._DM.E_commerce.Repositories.UsuarioRepository;
import com._DM.E_commerce.Service.Exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository repository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private ItemDoPedidoRepository itemDoPedidoRepository;

    @Transactional(readOnly = true)
    public List<PedidoDTO> findAll() {
        return repository.findAll().stream().map(PedidoDTO::new).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public PedidoDTO findById(UUID id) {
        Pedido entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));
        return new PedidoDTO(entity);
    }

    @Transactional
    public PedidoDTO insert(PedidoCreateDTO dto) {
        Pedido pedido = new Pedido();
        pedido.setMomento(LocalDate.now());
        pedido.setStatus(StatusDoPedido.AGUARDANDO_PAGAMENTO);

        Usuario cliente = usuarioRepository.findById(dto.clienteId())
                .orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado: " + dto.clienteId()));
        pedido.setCliente(cliente);

        pedido = repository.save(pedido);

        for (ItemPedidoCreateDTO itemDto : dto.itens()) {
            Produto produto = produtoRepository.findById(itemDto.produtoId())
                    .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado: " + itemDto.produtoId()));

            ItemDoPedido item = new ItemDoPedido(pedido, produto, itemDto.quantidade(), produto.getPreco());
            itemDoPedidoRepository.save(item);
            pedido.getItens().add(item);
        }

        return new PedidoDTO(pedido);
    }
    
    @Transactional
    public PedidoDTO updateStatus(UUID id, StatusDoPedido status) {
        Pedido pedido = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));
        pedido.setStatus(status);
        return new PedidoDTO(repository.save(pedido));
    }
}
