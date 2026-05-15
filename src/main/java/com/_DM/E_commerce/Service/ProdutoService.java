package com._DM.E_commerce.Service;

import com._DM.E_commerce.Dtos.ProdutoCreateDTO;
import com._DM.E_commerce.Dtos.ProdutoDTO;
import com._DM.E_commerce.Entity.Categoria;
import com._DM.E_commerce.Entity.Produto;
import com._DM.E_commerce.Repositories.CategoriaRepository;
import com._DM.E_commerce.Repositories.ProdutoRepository;
import com._DM.E_commerce.Service.Exceptions.DatabaseException;
import com._DM.E_commerce.Service.Exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository repository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private ImageStorageService imageStorageService;

    @Transactional(readOnly = true)
    public List<ProdutoDTO> findAll() {
        return repository.findAll().stream().map(ProdutoDTO::new).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ProdutoDTO findById(UUID id) {
        Produto entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));
        return new ProdutoDTO(entity);
    }

    @Transactional
    public ProdutoDTO insert(ProdutoCreateDTO dto) {
        Produto entity = new Produto();
        copyDtoToEntity(dto, entity);
        entity = repository.save(entity);
        return new ProdutoDTO(entity);
    }

    @Transactional
    public ProdutoDTO update(UUID id, ProdutoCreateDTO dto) {
        try {
            Produto entity = repository.getReferenceById(id);
            copyDtoToEntity(dto, entity);
            entity = repository.save(entity);
            return new ProdutoDTO(entity);
        } catch (jakarta.persistence.EntityNotFoundException e) {
            throw new ResourceNotFoundException(id);
        }
    }

    @Transactional
    public void delete(UUID id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException(id);
        }
        try {
            repository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Integrity violation");
        }
    }

    @Transactional
    public ProdutoDTO uploadFoto(UUID id, MultipartFile foto) {
        Produto entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));
        String oldImageUrl = entity.getImgUrl();
        String newImageUrl = imageStorageService.saveProductImage(id, foto);

        entity.setImgUrl(newImageUrl);
        entity = repository.save(entity);
        imageStorageService.deleteByPublicUrl(oldImageUrl);

        return new ProdutoDTO(entity);
    }

    private void copyDtoToEntity(ProdutoCreateDTO dto, Produto entity) {
        entity.setNome(dto.nome());
        entity.setDescricao(dto.descricao());
        entity.setPreco(dto.preco());
        entity.setImgUrl(dto.imgUrl());

        entity.getCategorias().clear();
        for (UUID catId : dto.categoriasIds()) {
            Categoria categoria = categoriaRepository.getReferenceById(catId);
            entity.getCategorias().add(categoria);
        }
    }
}
