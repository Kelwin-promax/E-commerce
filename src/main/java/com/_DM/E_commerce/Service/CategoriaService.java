package com._DM.E_commerce.Service;

import com._DM.E_commerce.Dtos.CategoriaCreateDTO;
import com._DM.E_commerce.Dtos.CategoriaDTO;
import com._DM.E_commerce.Entity.Categoria;
import com._DM.E_commerce.Repositories.CategoriaRepository;
import com._DM.E_commerce.Service.Exceptions.DatabaseException;
import com._DM.E_commerce.Service.Exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository repository;

    @Transactional(readOnly = true)
    public List<CategoriaDTO> findAll() {
        return repository.findAll().stream().map(CategoriaDTO::new).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public CategoriaDTO findById(UUID id) {
        Categoria entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));
        return new CategoriaDTO(entity);
    }

    @Transactional
    public CategoriaDTO insert(CategoriaCreateDTO dto) {
        Categoria entity = new Categoria();
        entity.setNome(dto.nome());
        entity = repository.save(entity);
        return new CategoriaDTO(entity);
    }

    @Transactional
    public CategoriaDTO update(UUID id, CategoriaCreateDTO dto) {
        try {
            Categoria entity = repository.getReferenceById(id);
            entity.setNome(dto.nome());
            entity = repository.save(entity);
            return new CategoriaDTO(entity);
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
}
