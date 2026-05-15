package com._DM.E_commerce.Controller;

import com._DM.E_commerce.Dtos.PedidoCreateDTO;
import com._DM.E_commerce.Dtos.PedidoDTO;
import com._DM.E_commerce.Enum.StatusDoPedido;
import com._DM.E_commerce.Service.PedidoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/pedidos")
public class PedidoController {

    @Autowired
    private PedidoService service;

    @GetMapping
    public ResponseEntity<List<PedidoDTO>> findAll() {
        List<PedidoDTO> list = service.findAll();
        return ResponseEntity.ok().body(list);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<PedidoDTO> findById(@PathVariable UUID id) {
        PedidoDTO dto = service.findById(id);
        return ResponseEntity.ok().body(dto);
    }

    @PostMapping
    public ResponseEntity<PedidoDTO> insert(@RequestBody @Valid PedidoCreateDTO dto) {
        PedidoDTO newDto = service.insert(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(newDto.id()).toUri();
        return ResponseEntity.created(uri).body(newDto);
    }

    @PutMapping(value = "/{id}/status")
    public ResponseEntity<PedidoDTO> updateStatus(@PathVariable UUID id, @RequestParam StatusDoPedido status) {
        PedidoDTO dto = service.updateStatus(id, status);
        return ResponseEntity.ok().body(dto);
    }
}
