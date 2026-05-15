package com._DM.E_commerce.Controller;

import com._DM.E_commerce.Dtos.PagamentoCreateDTO;
import com._DM.E_commerce.Dtos.PagamentoDTO;
import com._DM.E_commerce.Service.PagamentoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping(value = "/pagamentos")
public class PagamentoController {

    @Autowired
    private PagamentoService service;

    @PostMapping
    public ResponseEntity<PagamentoDTO> insert(@RequestBody @Valid PagamentoCreateDTO dto) {
        PagamentoDTO newDto = service.insert(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(newDto.id()).toUri();
        return ResponseEntity.created(uri).body(newDto);
    }
}
