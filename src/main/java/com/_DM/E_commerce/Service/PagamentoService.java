package com._DM.E_commerce.Service;

import com._DM.E_commerce.Dtos.PagamentoCreateDTO;
import com._DM.E_commerce.Dtos.PagamentoDTO;
import com._DM.E_commerce.Entity.Pagamento;
import com._DM.E_commerce.Entity.Pedido;
import com._DM.E_commerce.Enum.StatusDoPedido;
import com._DM.E_commerce.Repositories.PagamentoRepository;
import com._DM.E_commerce.Repositories.PedidoRepository;
import com._DM.E_commerce.Service.Exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PagamentoService {

    @Autowired
    private PagamentoRepository repository;

    @Autowired
    private PedidoRepository pedidoRepository;

    @Transactional
    public PagamentoDTO insert(PagamentoCreateDTO dto) {
        Pedido pedido = pedidoRepository.findById(dto.pedidoId())
                .orElseThrow(() -> new ResourceNotFoundException("Pedido não encontrado: " + dto.pedidoId()));
        
        Pagamento pagamento = new Pagamento(null, dto.momento(), pedido);
        pedido.setPagamento(pagamento);
        pedido.setStatus(StatusDoPedido.PAGO);
        
        pagamento = repository.save(pagamento);
        pedidoRepository.save(pedido);
        
        return new PagamentoDTO(pagamento);
    }
}
