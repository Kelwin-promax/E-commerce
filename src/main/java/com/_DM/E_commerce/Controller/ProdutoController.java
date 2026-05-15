package com._DM.E_commerce.Controller;

import com._DM.E_commerce.Dtos.ProdutoCreateDTO;
import com._DM.E_commerce.Dtos.ProdutoDTO;
import com._DM.E_commerce.Service.ProdutoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/produtos")
public class ProdutoController {

    @Autowired
    private ProdutoService service;

    @GetMapping
    public ResponseEntity<List<ProdutoDTO>> findAll() {
        List<ProdutoDTO> list = service.findAll();
        return ResponseEntity.ok().body(list);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<ProdutoDTO> findById(@PathVariable UUID id) {
        ProdutoDTO dto = service.findById(id);
        return ResponseEntity.ok().body(dto);
    }

    @PostMapping
    public ResponseEntity<ProdutoDTO> insert(@RequestBody @Valid ProdutoCreateDTO dto) {
        ProdutoDTO newDto = service.insert(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(newDto.id()).toUri();
        return ResponseEntity.created(uri).body(newDto);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<ProdutoDTO> update(@PathVariable UUID id, @RequestBody @Valid ProdutoCreateDTO dto) {
        ProdutoDTO newDto = service.update(id, dto);
        return ResponseEntity.ok().body(newDto);
    }

    @PostMapping(value = "/{id}/foto", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ProdutoDTO> uploadFoto(@PathVariable UUID id, @RequestParam("foto") MultipartFile foto) {
        ProdutoDTO dto = service.uploadFoto(id, foto);
        return ResponseEntity.ok().body(dto);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
